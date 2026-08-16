package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.chemthunder.acropolis.core.cca.entity.PlayerDataComponent;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;
import org.chemthunder.acropolis.core.index.AcropolisItems;
import org.chemthunder.acropolis.core.networking.s2c.VignetteTriggerPayload;
import org.chemthunder.acropolis.core.utilities.PlayerData;
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
        if (source.isOf(AcropolisDamageTypes.GAZE)) {
            this.playSound(SoundEvents.ENTITY_ALLAY_DEATH, 0.4F, 2);
        }
    }

    @Inject(method = "attack", at = @At(value = "HEAD"))
    private void acropolis$trust(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getMainHandStack().isEmpty() && player.getOffHandStack().isOf(AcropolisItems.CELESTIAL_GRIMOIRE)) {
            if (target instanceof PlayerEntity targetEntity) {
                PlayerDataComponent.KEY.get(targetEntity).setTrusted(!PlayerDataComponent.KEY.get(targetEntity).isTrusted());
                if (player instanceof ServerPlayerEntity entity) {
                    ServerPlayNetworking.send(entity, new VignetteTriggerPayload(1.0F));
                }
                if (targetEntity instanceof ServerPlayerEntity entity) {
                    ServerPlayNetworking.send(entity, new VignetteTriggerPayload(1.0F));
                }
            }
        }
    }

    @WrapMethod(method = "getBlockInteractionRange")
    private double acropolis$pvpBarrier(Operation<Double> original) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (GazeComponent.borderContainsEntity(player.getEntityWorld(), player) && !PlayerData.trusted(player)) {
            return 0;
        }

        return original.call();
    }

    @WrapMethod(method = "getDisplayName")
    private Text acropolis$sanctumName(Operation<Text> original) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (PlayerData.sanctum(player)) {
            return Text.literal("Sanctumkeeper.").formatted(Formatting.OBFUSCATED).withColor(0xFFac61d0);
        }
        return original.call();
    }

    @WrapMethod(method = "getName")
    private Text acropolis$sanctumName2(Operation<Text> original) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (PlayerData.sanctum(player)) {
            return Text.literal("Sanctumkeeper.").formatted(Formatting.OBFUSCATED).withColor(0xFFac61d0);
        }
        return original.call();
    }
}
