package org.chemthunder.acropolis.mixin.client;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallEntityComponent;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
@Mixin(value = LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "TAIL"))
    private void acropolis$outlineColor(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        if (Skyfall.shouldHaveOutline(livingEntity)) {
            livingEntityRenderState.outlineColor = 0xFFe534eb;
        }

        if (livingEntity.getComponent(SkyfallEntityComponent.KEY).fallTicks > 0) {
            livingEntityRenderState.setData(AcropolisClient.HAS_CHAINS, true);
        }
    }
}
