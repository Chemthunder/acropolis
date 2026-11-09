package org.chemthunder.acropolis.core.event.client.entities;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.AcropolisConfig;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.client.api.Nitro;
import org.chemthunder.acropolis.core.client.model.PlaneModel;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;
import org.joml.Vector3f;

/**
 * @author Chemthunder
 */
public class GazeRenderEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext worldRenderContext) {
        MatrixStack matrix = worldRenderContext.matrices();
        VertexConsumerProvider consumers = worldRenderContext.consumers();
        float delta = MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks();

        Camera camera = worldRenderContext.gameRenderer().getCamera();
        World world = MinecraftClient.getInstance().world;

        if (world != null) {
            GazeComponent gaze = GazeComponent.KEY.get(world);

            PlaneModel model = new PlaneModel(MinecraftClient.getInstance().getLoadedEntityModels().getModelPart(AEntityModelLayers.PLANE));

            if (gaze.isPresent() && gaze.getPos() != null) {
                Vector3f pos = new Vector3f(
                        (float) (gaze.getPos().x - camera.getCameraPos().x),
                        (float) (gaze.getPos().y - camera.getCameraPos().y),
                        (float) (gaze.getPos().z - camera.getCameraPos().z)
                );

                //
                matrix.push();

                matrix.translate(pos.x, pos.y + 1.5F, pos.z);

                matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.AGE + delta)));

                ItemRenderState grimoireState = new ItemRenderState();
                MinecraftClient.getInstance().getItemModelManager().clearAndUpdate(
                        grimoireState,
                        AItems.CELESTIAL_GRIMOIRE.getDefaultStack(),
                        ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                        world,
                        null,
                        0
                );

                grimoireState.render(
                        matrix,
                        worldRenderContext.commandQueue(),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        0x00
                );

                matrix.pop();

                //
                matrix.push();

                matrix.translate(pos.x, pos.y + 50, pos.z + 0.5F);

                matrix.multiply(camera.getRotation());
                matrix.scale(25, 25, 25);

                matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

                matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.AGE + delta) / 4));

                model.render(
                        matrix,
                        consumers.getBuffer(RenderLayers.entityTranslucentEmissive(Acropolis.id("textures/entity/gaze.png"))),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        ColorHelper.withAlpha(0.2F, gaze.isAngered() ? 0xff004f : 0xffffff)
                );

                matrix.pop();

                //
                matrix.push();

                matrix.translate(pos.x, pos.y + 50, pos.z);

                matrix.multiply(camera.getRotation());
                matrix.scale(30, 30, 30);
                matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

                model.render(
                        matrix,
                        consumers.getBuffer(RenderLayers.entityTranslucent(Acropolis.id("textures/entity/gaze.png"))),
                        LightmapTextureManager.MAX_LIGHT_COORDINATE,
                        OverlayTexture.DEFAULT_UV,
                        gaze.isAngered() ? 0xFFff004f : 0xFFffffff
                );

                matrix.pop();
            }
        }
    }
}
