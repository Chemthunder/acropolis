package org.chemthunder.acropolis.core.client.event;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.cca.skyfall.obj.SightInstance;
import org.chemthunder.acropolis.core.client.api.Nitro;
import org.chemthunder.acropolis.core.client.model.PlaneModel;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;
import org.chemthunder.acropolis.core.utilities.Skyfall;

import java.util.Random;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class SightEvents {
    public static float width = 0.0F;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        WorldRenderEvents.END_MAIN.register(new Render());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            World world = MinecraftClient.getInstance().world;
            if (world == null) return;

            SkyfallWorldComponent skyfall = Skyfall.ofWorld(world);
            SightInstance ins = skyfall.sight;

            width = Math.clamp(width, 0, 1);

            if (ins.active) {
                if (width < 1) {
                    width += 0.005F;
                }
            } else {
                if (width > 0) {
                    width -= 0.005F;
                }
            }

            if (!ins.active && ins.pos == null) {
                width = 0;
            }
        }
    }

    public static class Render implements WorldRenderEvents.EndMain {
        public void endMain(WorldRenderContext context) {
            World world = MinecraftClient.getInstance().world;
            if (world == null) return;

            SkyfallWorldComponent skyfall = Skyfall.ofWorld(world);
            SightInstance ins = skyfall.sight;

            Vec3d camPos = context.gameRenderer().getCamera().getCameraPos();

            MatrixStack matrix = context.matrices();
            VertexConsumerProvider provider = context.consumers();
            OrderedRenderCommandQueue queue = context.commandQueue();
            LoadedEntityModels models = MinecraftClient.getInstance().getLoadedEntityModels();

            if (ins.pos != null) {
                Vec3d renderPos = ins.pos.subtract(camPos);

                float easedW = EasingType.OUT_EXPO.apply(width) * 1.6F;
                float delta = MiscUtils.getTickDelta();

                if (width > 0) {
                    for (int i = 0; i < 2; i++) {
                        Random rand = new Random();
                        final double w = easedW * 1.9F;

                        world.addParticleClient(
                                ParticleTypes.END_ROD,
                                ins.pos.x + rand.nextDouble(-w, w),
                                ins.pos.y,
                                ins.pos.z + rand.nextDouble(-w, w),
                                0,
                                rand.nextFloat(0.3F, 1.2F),
                                0
                        );
                    }

                    matrix.push();

                    matrix.translate(renderPos);
                    matrix.scale((easedW * 6), (easedW * 6), (easedW * 6));

                    matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.age + delta)));

                    queue.submitModel(
                            new PlaneModel(models.getModelPart(AEntityModelLayers.PLANE)),
                            Unit.INSTANCE,
                            matrix,
                            RenderLayers.entityTranslucent(Acropolis.id("textures/entity/sight_new.png")),
                            LightmapTextureManager.MAX_LIGHT_COORDINATE,
                            OverlayTexture.DEFAULT_UV,
                            0x00,
                            null
                    );

                    matrix.pop();

                    matrix.push();

                    matrix.translate(renderPos);
                    matrix.translate(0, -0.35F, 0);

                    matrix.scale((easedW * 7), (easedW * 7), (easedW * 7));

                    matrix.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((AcropolisClient.age + delta)));

                    queue.submitModel(
                            new PlaneModel(models.getModelPart(AEntityModelLayers.PLANE)),
                            Unit.INSTANCE,
                            matrix,
                            RenderLayers.entityTranslucentEmissive(Acropolis.id("textures/entity/sight_bg.png")),
                            LightmapTextureManager.MAX_LIGHT_COORDINATE,
                            OverlayTexture.DEFAULT_UV,
                            ColorHelper.withAlpha(0.3F, 0xFFffffff),
                            null,
                            0x00,
                            null
                    );

                    matrix.pop();

                    matrix.push();

                    matrix.push();

                    matrix.translate(renderPos);
                    matrix.translate(0, 240, 0);

                    matrix.scale(-1, -1, -1);

                    Nitro.skyBeamAtPos(
                            matrix,
                            provider.getBuffer(RenderLayers.entityShadow(Acropolis.id("textures/render/innovator_outer.png"))),
                            0, 0, 0,
                            easedW,
                            360,
                            0
                    );

                    matrix.pop();

                    matrix.push();

                    matrix.translate(renderPos);
                    matrix.translate(0, -40, 0);

                    Nitro.skyBeamAtPos(
                            matrix,
                            provider.getBuffer(RenderLayers.entityTranslucent(Acropolis.id("textures/render/innovator_inner.png"))),
                            0, 0, 0,
                            easedW - 0.2F,
                            360,
                            0
                    );

                    matrix.pop();

                    matrix.pop();
                }
            }
        }
    }
}

