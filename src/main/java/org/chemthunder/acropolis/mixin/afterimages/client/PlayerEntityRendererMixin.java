package org.chemthunder.acropolis.mixin.afterimages.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.cca.entity.ImagesComponent;
import org.chemthunder.acropolis.core.client.feature.render.ImagesFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin<AvatarlikeEntity extends PlayerLikeEntity & ClientPlayerLikeEntity> extends LivingEntityRenderer<AvatarlikeEntity, PlayerEntityRenderState, PlayerEntityModel> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At(value = "TAIL"))
    private void acropolis$saveAndUploadImages(AvatarlikeEntity playerLikeEntity, PlayerEntityRenderState playerEntityRenderState, float f, CallbackInfo ci) {
        if (playerLikeEntity instanceof ClientPlayerEntity clientPlayer) {
            ImagesComponent images = clientPlayer.getComponent(ImagesComponent.KEY);

            playerEntityRenderState.setData(AcropolisClient.AFTER_IMAGES, images.getAfterImages());
        }
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void acropolis$addFeature(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new ImagesFeatureRenderer<>(this, ctx.getEntityModels()));
    }
}
