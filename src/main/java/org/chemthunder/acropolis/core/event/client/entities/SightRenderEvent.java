package org.chemthunder.acropolis.core.event.client.entities;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Easing;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.AcropolisConfig;
import org.chemthunder.acropolis.core.cca.world.SightComponent;
import org.chemthunder.acropolis.core.client.api.Nitro;
import org.chemthunder.acropolis.core.client.model.PlaneModel;
import org.chemthunder.acropolis.core.index.client.AcropolisEntityModelLayers;

/**
 * @author Chemthunder
 */
public class SightRenderEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext worldRenderContext) {
        MatrixStack stack = worldRenderContext.matrices();
        VertexConsumerProvider consumers = worldRenderContext.consumers();
        OrderedRenderCommandQueue queue = worldRenderContext.commandQueue();
        float delta = MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks();

        Camera camera = worldRenderContext.gameRenderer().getCamera();
        World world = MinecraftClient.getInstance().world;

        if (world != null) {
            SightComponent sight = SightComponent.KEY.get(world);

            if (sight.getPos() != null) {
                float x = (float) (sight.getPos().x - camera.getCameraPos().x);
                float y = (float) (sight.getPos().y - camera.getCameraPos().y);
                float z = (float) (sight.getPos().z - camera.getCameraPos().z);

                float circleScale = Easing.inOutCubic(sight.getScale()) * 6;

                if (sight.getScale() > 0.0F) {
                    stack.push();

                    stack.translate(x, y + 0.2F, z);
                    stack.scale(circleScale, circleScale, circleScale);
                    stack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((AcropolisClient.AGE + delta) * 2));

                    PlaneModel model = new PlaneModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(AcropolisEntityModelLayers.PLANE));

                    model.render(
                            stack,
                            consumers.getBuffer(RenderLayers.entityTranslucentEmissive(Acropolis.id("textures/entity/sight.png"), true)),
                            LightmapTextureManager.MAX_LIGHT_COORDINATE,
                            OverlayTexture.DEFAULT_UV
                    );

                    stack.pop();
                }

                if (sight.getBeamWidth() > 0.0F) {
                    stack.push();

                    stack.translate(x, y - 50, z);

                    Nitro.skyBeamAtPos(
                            stack,
                            consumers.getBuffer(RenderLayers.eyes(Acropolis.id("textures/entity/sight_overlay.png"))),
                            0, 0, 0,
                            Easing.outExpo(sight.getBeamWidth()),
                            360,
                            0
                    );

                    stack.pop();

                    stack.push();

                    stack.translate(x, y - 50, z);

                    Nitro.skyBeamAtPos(
                            stack,
                            consumers.getBuffer(RenderLayers.entityTranslucent(Acropolis.id("textures/entity/sight_black.png"))),
                            0, 0, 0,
                            Easing.outCirc(sight.getBeamWidth() - 0.7F),
                            360,
                            AcropolisConfig.epilepsyMode ? (AcropolisClient.AGE + delta) / 6 : (AcropolisClient.AGE + delta)
                    );

                    stack.pop();
                }
            }
        }
    }
}
