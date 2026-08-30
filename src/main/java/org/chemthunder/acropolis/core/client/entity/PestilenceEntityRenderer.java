package org.chemthunder.acropolis.core.client.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.entity.PestilenceEntity;
import org.chemthunder.acropolis.core.index.AcropolisEntityModelLayers;

/**
 * @author Chemthunder
 */
public class PestilenceEntityRenderer extends MobEntityRenderer<PestilenceEntity, PestilenceEntityRenderState, PestilenceEntityModel> {
    public PestilenceEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PestilenceEntityModel(context.getPart(AcropolisEntityModelLayers.PESTILENCE)), 0.0F);
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
}
