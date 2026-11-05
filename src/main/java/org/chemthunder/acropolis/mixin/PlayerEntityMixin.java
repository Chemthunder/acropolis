package org.chemthunder.acropolis.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(value = PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Inject(method = "damage", at = @At(value = "HEAD"))
    private void acropolis$damageSound(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(ADamageTypes.GAZE)) {
            this.playSound(SoundEvents.ENTITY_ALLAY_DEATH, 0.4F, 2);
        }
    }
}
