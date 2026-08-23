package org.chemthunder.acropolis.mixin.client;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.chemthunder.acropolis.core.AcropolisClient.mercyActive;

@Mixin(value = GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "onCameraEntitySet", at = @At("HEAD"), cancellable = true)
    private void disablePrespectiveKeyMessingWithIt(Entity entity, CallbackInfo ci){
        if (mercyActive()) {
            ci.cancel();
        }
    }

    @Inject(method = "togglePostProcessorEnabled", at = @At("HEAD"), cancellable = true)
    private void disableF4MessingWithIt(CallbackInfo ci){
        if (mercyActive()) {
            ci.cancel();
        }
    }
}
