package org.chemthunder.acropolis.core.client.entity.render;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.entity.model.MeteorEntityModel;
import org.chemthunder.acropolis.core.client.entity.state.MeteorEntityRenderState;
import org.chemthunder.acropolis.core.entity.MeteorEntity;
import org.chemthunder.acropolis.core.index.client.AcropolisEntityModelLayers;

/**
 * @author Chemthunder
 */
public class MeteorEntityRenderer extends EntityRenderer<MeteorEntity, MeteorEntityRenderState> {
    private final MeteorEntityModel model;

    public MeteorEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new MeteorEntityModel(context.getEntityModels().getModelPart(AcropolisEntityModelLayers.METEOR));
    }

    public MeteorEntityRenderState createRenderState() {
        return new MeteorEntityRenderState();
    }

    public void render(MeteorEntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        super.render(renderState, matrices, queue, cameraState);

        float delta = MiscUtils.getTickDelta();

        matrices.push();

//        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((renderState.age + delta) * 2),
//                0,
//                0,
//                0);
//        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((renderState.age + delta) * 2),
//                0,
//                0,
//                0);

        queue.submitModel(
                this.model,
                renderState,
                matrices,
                RenderLayers.entityTranslucent(Acropolis.id("textures/entity/meteor.png")),
                LightmapTextureManager.MAX_LIGHT_COORDINATE,
                OverlayTexture.DEFAULT_UV,
                0x00,
                null
        );

        matrices.pop();
    }
}
