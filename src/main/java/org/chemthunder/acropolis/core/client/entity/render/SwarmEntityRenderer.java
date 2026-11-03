package org.chemthunder.acropolis.core.client.entity.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.entity.model.SwarmEntityModel;
import org.chemthunder.acropolis.core.entity.SwarmEntity;
import org.chemthunder.acropolis.core.index.client.AcropolisEntityModelLayers;

/**
 * @author Chemthunder
 */
public class SwarmEntityRenderer extends EmptyEntityRenderer<SwarmEntity> {
    private final SwarmEntityModel firstPlane;
    private final SwarmEntityModel secondPlane;

    public SwarmEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.firstPlane = new SwarmEntityModel(context.getPart(AcropolisEntityModelLayers.SWARM));
        this.secondPlane = new SwarmEntityModel(context.getPart(AcropolisEntityModelLayers.SWARM));
    }

    public void render(EntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        super.render(renderState, matrices, queue, cameraState);

        float delta = MinecraftClient.getInstance().getRenderTickCounter().getFixedDeltaTicks();

        matrices.push();

        matrices.scale(3F, 3F, 3F);

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((delta + renderState.age) * 4));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        queue.submitModel(
                this.firstPlane,
                renderState,
                matrices,
                RenderLayers.entityCutout(Acropolis.id("textures/entity/swarm.png")),
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                OverlayTexture.DEFAULT_UV,
                0xFFff0000,
                null
        );

        matrices.pop();

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((delta + renderState.age) * -4));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        queue.submitModel(
                this.firstPlane,
                renderState,
                matrices,
                RenderLayers.entityCutout(Acropolis.id("textures/entity/swarm.png")),
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                OverlayTexture.DEFAULT_UV,
                0xFFff0000,
                null
        );

        matrices.pop();

        matrices.pop();
    }
}
