package org.chemthunder.acropolis.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.AcropolisConfig;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Camera.class)
public abstract class CameraMixin {
    @Inject(method = "update", at = @At("RETURN"))
    private void eclipseWorldShake(World area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
        if (!AcropolisConfig.motionSicknessMode) {
            MercyComponent mercy = MercyComponent.KEY.get(focusedEntity.getEntityWorld());
            float tickDelta = MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks();

            if (mercy.isActive()) {
                Camera camera = (Camera) (Object) this;

                ClientPlayerEntity player = MinecraftClient.getInstance().player;

                if (player != null) {
                    int age = player.age;

                    float amplitude = .0025f;
                    float strength = 0.5f;

                    float yawOffset = 0;
                    float pitchOffset = 0;

                    camera.setRotation(camera.getYaw() + yawOffset, camera.getPitch() + pitchOffset);
                    camera.setPos(camera.getCameraPos().add(0, Math.sin((age + tickDelta) * strength) / 2f * amplitude, Math.cos((age + tickDelta) * strength) * amplitude));
                }
            }
        }
    }
}