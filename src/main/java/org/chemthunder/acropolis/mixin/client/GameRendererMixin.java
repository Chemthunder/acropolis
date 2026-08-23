package org.chemthunder.acropolis.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "onCameraEntitySet", at = @At("HEAD"), cancellable = true)
    private void disablePrespectiveKeyMessingWithIt(Entity entity, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            if (MercyComponent.KEY.get(client.world).isActive()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "togglePostProcessorEnabled", at = @At("HEAD"), cancellable = true)
    private void disableF4MessingWithIt(CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            if (MercyComponent.KEY.get(client.world).isActive()) {
                ci.cancel();
            }
        }
    }
}
