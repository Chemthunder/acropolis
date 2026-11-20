package org.chemthunder.acropolis.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import org.chemthunder.acropolis.core.client.feature.ChainsFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin<AvatarlikeEntity extends PlayerLikeEntity & ClientPlayerLikeEntity>
        extends LivingEntityRenderer<AvatarlikeEntity, PlayerEntityRenderState, PlayerEntityModel> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void acropolis$pushFeatures(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new ChainsFeatureRenderer<>(this, MinecraftClient.getInstance().getLoadedEntityModels()));
    }
}
