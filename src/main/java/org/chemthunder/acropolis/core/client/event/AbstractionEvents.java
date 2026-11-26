package org.chemthunder.acropolis.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.cca.skyfall.obj.AbstractionInstance;
import org.chemthunder.acropolis.core.client.model.CuboidModel;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;
import org.chemthunder.acropolis.core.utilities.Skyfall;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class AbstractionEvents {
    public static float boxOpacity = 0.0F;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        WorldRenderEvents.END_MAIN.register(new Render());
    }

    public static void trigger() {
        boxOpacity = 1.0F;
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            boxOpacity = Math.clamp(boxOpacity, 0, 1);

            if (!client.isPaused()) {
                if (boxOpacity > 0.0F) {
                    boxOpacity -= 0.025F;
                }
            }
        }
    }

    public static class Render implements WorldRenderEvents.EndMain {
        public void endMain(WorldRenderContext context) {
            MatrixStack matrix = context.matrices();
            OrderedRenderCommandQueue queue = context.commandQueue();

            Camera camera = context.gameRenderer().getCamera();

            World world = MinecraftClient.getInstance().world;
            if (world == null) return;

            SkyfallWorldComponent sk = Skyfall.ofWorld(world);
            AbstractionInstance abs = sk.abs;

            LoadedEntityModels models = MinecraftClient.getInstance().getLoadedEntityModels();

            if (abs.pos != null && boxOpacity > 0.0F) {
                Vec3d renderPos = abs.pos.subtract(camera.getCameraPos());
                float scale = (float) AbstractionInstance.RANGE;

                CuboidModel cuboid = new CuboidModel(models.getModelPart(AEntityModelLayers.CUBOID));

                matrix.push();

                matrix.translate(renderPos);
                matrix.scale(scale, scale, scale);
                matrix.translate(0, -0.8F, 0);

                queue.submitModel(
                        cuboid,
                        Unit.INSTANCE,
                        matrix,
                        RenderLayers.entityTranslucent(Acropolis.id("textures/render/abstraction.png")),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        ColorHelper.withAlpha(EasingType.OUT_EXPO.apply(boxOpacity), 0xFFffffff),
                        null,
                        0x00,
                        null
                );

                matrix.pop();
            }
        }
    }
}
