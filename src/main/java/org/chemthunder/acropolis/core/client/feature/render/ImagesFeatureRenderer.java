package org.chemthunder.acropolis.core.client.feature.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.cca.entity.ImagesComponent;

import java.util.List;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class ImagesFeatureRenderer<S extends BipedEntityRenderState, M extends BipedEntityModel<S>, A extends BipedEntityModel<S>> extends FeatureRenderer<S, M> {
    private final FeatureRendererContext<S, M> ctx;
    private final LoadedEntityModels loader;

    public ImagesFeatureRenderer(FeatureRendererContext<S, M> context, LoadedEntityModels loader) {
        super(context);
        this.ctx = context;
        this.loader = loader;
    }

    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state, float limbAngle, float limbDistance) {
        List<ImagesComponent.Image> afterImages = state.getData(AcropolisClient.AFTER_IMAGES);

        if (state instanceof PlayerEntityRenderState) {
            if (afterImages != null) {
                for (ImagesComponent.Image image : afterImages) {
                    PlayerEntityModel playerModel = new PlayerEntityModel(this.loader.getModelPart(EntityModelLayers.PLAYER), true);

                    PlayerEntityRenderState uploadedState = new PlayerEntityRenderState();

                    uploadedState.x = image.position().x;
                    uploadedState.y = image.position().y;
                    uploadedState.z = image.position().z;

                    uploadedState.bodyYaw = image.bodyYaw();
                    uploadedState.relativeHeadYaw = image.headYaw();
                    uploadedState.pitch = image.pitch();

                    Vec3d renderTarget = image.position().subtract(new Vec3d(state.x, state.y, state.z));

                    matrices.push();

                    matrices.translate(renderTarget);

                    queue.submitModel(
                            playerModel,
                            uploadedState,
                            matrices,
                            RenderLayers.entityTranslucent(Acropolis.id("n")),
                            light,
                            OverlayTexture.DEFAULT_UV,
                            0x00,
                            null
                    );

                    matrices.pop();
                }
            }
        }
    }
}
