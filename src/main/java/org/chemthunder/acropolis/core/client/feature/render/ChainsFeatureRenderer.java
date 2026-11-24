package org.chemthunder.acropolis.core.client.feature.render;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Unit;
import net.minecraft.util.math.RotationAxis;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.client.model.ChainsModel;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class ChainsFeatureRenderer<S extends BipedEntityRenderState, M extends BipedEntityModel<S>, A extends BipedEntityModel<S>> extends FeatureRenderer<S, M> {
    private final ChainsModel chains;

    public ChainsFeatureRenderer(FeatureRendererContext<S, M> context, LoadedEntityModels loader) {
        super(context);
        this.chains = new ChainsModel(loader.getModelPart(AEntityModelLayers.CHAINS));
    }

    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state, float limbAngle, float limbDistance) {
        if (Boolean.TRUE.equals(state.getData(AcropolisClient.HAS_CHAINS))) {
            float delta = MiscUtils.getTickDelta();

            matrices.push();

            matrices.translate(0, -1.6, 0);
            matrices.scale(1.7F, 1.7F, 1.7F);

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(25), 0, 0, 0);

            matrices.translate(0.5F, -0.35F, 0);

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((state.age + delta)));

            queue.submitModel(
                    this.chains,
                    Unit.INSTANCE,
                    matrices,
                    RenderLayers.entityAlpha(Acropolis.id("textures/render/chains.png")),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    0x00,
                    null
            );

            matrices.pop();

            matrices.push();

            matrices.translate(0, -1.6, 0);
            matrices.scale(1.7F, 1.7F, 1.7F);

            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(25), 0, 0, 0);

            matrices.translate(-0.5F, -0.35F, 0);

            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((state.age + delta)));

            queue.submitModel(
                    this.chains,
                    Unit.INSTANCE,
                    matrices,
                    RenderLayers.entityAlpha(Acropolis.id("textures/render/chains.png")),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    0x00,
                    null
            );

            matrices.pop();
        }
    }
}

