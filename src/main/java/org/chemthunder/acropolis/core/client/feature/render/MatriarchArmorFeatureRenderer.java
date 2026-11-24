package org.chemthunder.acropolis.core.client.feature.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
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
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.client.event.RynMaterializeEvent;
import org.chemthunder.acropolis.core.client.feature.model.MatriarchArmorModel;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class MatriarchArmorFeatureRenderer<S extends BipedEntityRenderState, M extends BipedEntityModel<S>, A extends BipedEntityModel<S>> extends FeatureRenderer<S, M> {
    private static final Identifier PATH = Acropolis.id("textures/entity/matriarch.png");
    private final MatriarchArmorModel armor;

    public MatriarchArmorFeatureRenderer(FeatureRendererContext<S, M> context, LoadedEntityModels models) {
        super(context);
        this.armor = new MatriarchArmorModel(models.getModelPart(AEntityModelLayers.MATRIARCH));
    }

    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state, float limbAngle, float limbDistance) {
        matrices.push();

        M contextModel = this.getContextModel();

        if (Boolean.TRUE.equals(state.getData(AcropolisClient.MATRIARCH))) {
            int tintColor = ColorHelper.lerpLinear(RynMaterializeEvent.lerp, 0xFFffffff, ColorHelper.withAlpha(100000, 0xFFc803ff));

            // Arms
            matrices.push();

            contextModel.setArmAngle(state, Arm.RIGHT, matrices);
            matrices.translate(0.3, 0.63, 0);

            queue.submitModelPart(
                    this.armor.RightArm,
                    matrices,
                    RenderLayers.entityCutout(PATH),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    null,
                    tintColor,
                    null
            );

            matrices.pop();

            matrices.push();

            contextModel.setArmAngle(state, Arm.LEFT, matrices);
            matrices.translate(-0.3, 0.63, 0);

            queue.submitModelPart(
                    this.armor.LeftArm,
                    matrices,
                    RenderLayers.entityCutout(PATH),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    null,
                    tintColor,
                    null
            );

            matrices.pop();

            // Head
            matrices.push();

            contextModel.getRootPart().applyTransform(matrices);
            contextModel.head.applyTransform(matrices);

            matrices.translate(0, 0.75, 0);

            queue.submitModelPart(
                    this.armor.Head,
                    matrices,
                    RenderLayers.entityCutout(PATH),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    null,
                    tintColor,
                    null
            );

            matrices.pop();

            // Legs
            matrices.push();

            contextModel.getRootPart().applyTransform(matrices);
            contextModel.leftLeg.applyTransform(matrices);

            matrices.translate(-0.12, -0.75, 0);

            queue.submitModelPart(
                    this.armor.LeftLeg,
                    matrices,
                    RenderLayers.entityCutout(PATH),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    null,
                    tintColor,
                    null
            );

            matrices.pop();

            matrices.push();

            contextModel.getRootPart().applyTransform(matrices);
            contextModel.rightLeg.applyTransform(matrices);

            matrices.translate(0.12, -0.75, 0);

            queue.submitModelPart(
                    this.armor.RightLeg,
                    matrices,
                    RenderLayers.entityCutout(PATH),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    null,
                    tintColor,
                    null
            );

            matrices.pop();

            // Chest
            matrices.push();

            contextModel.getRootPart().applyTransform(matrices);
            contextModel.body.applyTransform(matrices);

            queue.submitModelPart(
                    this.armor.Body,
                    matrices,
                    RenderLayers.entityCutout(PATH),
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV,
                    null,
                    tintColor,
                    null
            );

            matrices.pop();
        }
        matrices.pop();
    }

    private void uploadPart(ModelPart reference, ModelPart rendered, OrderedRenderCommandQueue queue, MatrixStack matrices) {

    }
}
