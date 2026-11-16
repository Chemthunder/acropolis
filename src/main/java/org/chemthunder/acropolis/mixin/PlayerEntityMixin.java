package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.chemthunder.acropolis.core.utilities.ShockwaveHandler;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void acropolis$tickTailBullshit(CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity) (Object) this;

        if (self.getAttachedOrCreate(AAttachmentTypes.EXPECTING_SHOCKWAVE, () -> false)) {
            ShockwaveHandler.additionalTick(self);
        }
    }

    @WrapMethod(method = "damage")
    private boolean acropolis$nofightingkids(ServerWorld world, DamageSource source, float amount, Operation<Boolean> original) {
        if (Skyfall.getInstance(world).isInnovatorActive()) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                player.sendMessage(Text.literal("Do not fight in the presence of your creator."), true);
                return false;
            }
        }
        return original.call(world, source, amount);
    }
}
