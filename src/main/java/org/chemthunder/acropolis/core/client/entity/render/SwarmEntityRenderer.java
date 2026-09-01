package org.chemthunder.acropolis.core.client.entity.render;

import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.model.PlaneModel;
import org.chemthunder.acropolis.core.entity.SwarmEntity;
import org.chemthunder.acropolis.core.index.AcropolisEntityModelLayers;

/**
 * @author Chemthunder
 */
public class SwarmEntityRenderer extends EmptyEntityRenderer<SwarmEntity> {
    private final PlaneModel firstPlane;
    private final PlaneModel secondPlane;

    public SwarmEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.firstPlane = new PlaneModel(context.getPart(AcropolisEntityModelLayers.PLANE));
        this.secondPlane = new PlaneModel(context.getPart(AcropolisEntityModelLayers.PLANE));
    }

    public void render(EntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        super.render(renderState, matrices, queue, cameraState);

        queue.submitCustom(matrices, RenderLayers.entityCutout(Acropolis.id("textures/entity/swarm.png")), (matricesEntry, vertexConsumer) -> {
            this.extract(matricesEntry, vertexConsumer, matrices, renderState);
        });
    }

    private void extract(MatrixStack.Entry entry, VertexConsumer consumer, MatrixStack matrices, EntityRenderState state) {
//        this.firstPlane.render(
//                matrices,
//                consumer,
//                LightmapTextureManager.MAX_LIGHT_COORDINATE,
//                OverlayTexture.DEFAULT_UV
//        );
//
//        this.secondPlane.render(
//                matrices,
//                consumer,
//                LightmapTextureManager.MAX_LIGHT_COORDINATE,
//                OverlayTexture.DEFAULT_UV
//        );
    }
}
