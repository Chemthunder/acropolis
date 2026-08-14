package org.chemthunder.acropolis.core.event.client;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.client.Nitro;
import org.chemthunder.acropolis.core.client.model.PlaneModel;
import org.chemthunder.acropolis.core.index.AcropolisEntityModelLayers;

/**
 * @author Chemthunder
 */
public class GazeRenderEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext worldRenderContext) {
        MatrixStack stack = worldRenderContext.matrices();
        VertexConsumerProvider consumers = worldRenderContext.consumers();
        OrderedRenderCommandQueue queue = worldRenderContext.commandQueue();
        float delta = MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks();

        Camera camera = worldRenderContext.gameRenderer().getCamera();
        World world = MinecraftClient.getInstance().world;

        if (world != null) {
            GazeComponent gaze = GazeComponent.KEY.get(world);

            PlaneModel model = new PlaneModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(AcropolisEntityModelLayers.PLANE));

            if (gaze.isPresent() && gaze.getPos() != null) {
                float x = (float) (gaze.getPos().x - camera.getCameraPos().x);
                float y = (float) (gaze.getPos().y - camera.getCameraPos().y);
                float z = (float) (gaze.getPos().z - camera.getCameraPos().z);

                if (gaze.isBorder()) {
                    {
                        stack.push();

                        stack.translate(x, y - 120, z);

                        Nitro.skyBeamAtPos(
                                stack,
                                consumers.getBuffer(RenderLayers.entityTranslucent(Acropolis.id("textures/entity/border.png"))),
                                0,
                                0,
                                0,
                                150,
                                900,
                                (AcropolisClient.AGE + delta) / 4
                        );

                        stack.pop();
                    }
                }

                {
                    stack.push();

                    stack.translate(x, y + 1.5F, z);

                    stack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90));

                    stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((AcropolisClient.AGE + delta)));

                    model.render(
                            stack,
                            consumers.getBuffer(RenderLayers.itemEntityTranslucentCull(Acropolis.id("textures/item/celestial_grimoire.png"))),
                            LightmapTextureManager.MAX_LIGHT_COORDINATE,
                            OverlayTexture.DEFAULT_UV
                    );

                    stack.pop();
                }

                {
                    stack.push();

                    stack.translate(x, y + 50, z + 0.5F);

                    stack.multiply(camera.getRotation());
                    stack.scale(25, 25, 25);

                    stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

                    stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.AGE + delta) / 4));

                    model.render(
                            stack,
                            consumers.getBuffer(RenderLayers.entityTranslucentEmissive(Acropolis.id("textures/entity/gaze.png"))),
                            LightmapTextureManager.MAX_LIGHT_COORDINATE,
                            OverlayTexture.DEFAULT_UV,
                            ColorHelper.withAlpha(0.2F, gaze.isAngered() ? 0xff004f : 0xffffff)
                    );

                    stack.pop();

                    stack.push();

                    stack.translate(x, y + 50, z);

                    stack.multiply(camera.getRotation());
                    stack.scale(30, 30, 30);
                    stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

                    model.render(
                            stack,
                            consumers.getBuffer(RenderLayers.entityTranslucent(Acropolis.id("textures/entity/gaze.png"))),
                            LightmapTextureManager.MAX_LIGHT_COORDINATE,
                            OverlayTexture.DEFAULT_UV,
                            gaze.isAngered() ? 0xFFff004f : 0xFFffffff
                    );

                    stack.pop();
                }
            }
        }
    }
}
