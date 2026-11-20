package org.chemthunder.acropolis.core.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.entity.model.PestilenceEntityModel;
import org.chemthunder.acropolis.core.client.entity.state.PestilenceEntityRenderState;
import org.chemthunder.acropolis.core.entity.PestilenceEntity;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PestilenceEntityRenderer extends MobEntityRenderer<PestilenceEntity, PestilenceEntityRenderState, PestilenceEntityModel> {
    public PestilenceEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PestilenceEntityModel(context.getPart(AEntityModelLayers.PESTILENCE)), 0.0F);
    }

    public Identifier getTexture(PestilenceEntityRenderState state) {
        return Acropolis.id("textures/entity/pestilence.png");
    }

    public PestilenceEntityRenderState createRenderState() {
        return new PestilenceEntityRenderState();
    }

    public void updateRenderState(PestilenceEntity livingEntity, PestilenceEntityRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.tickProgress = f;
        livingEntityRenderState.entity = livingEntity;
    }

    public void render(PestilenceEntityRenderState livingEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        super.render(livingEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);

        matrixStack.push();

        matrixStack.scale(4, 4, 4);

        matrixStack.pop();
    }
}
