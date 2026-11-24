package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.waypoint.ServerWaypoint;
import org.chemthunder.acropolis.core.cca.entity.BorderComponent;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.entity.PestilenceEntity;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ServerWaypoint {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onKilledBy", at = @At(value = "HEAD"))
    private void acropolis$becomeTainted(LivingEntity adversary, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self instanceof EvokerEntity) {
            if (adversary instanceof PlayerEntity player) {
                if (player.getOffHandStack().isOf(AItems.CELESTIAL_GRIMOIRE)) {
                    player.getOffHandStack().decrement(1);
                    player.equipStack(EquipmentSlot.OFFHAND, new ItemStack(AItems.TAMPERED_GRIMOIRE));
                }
            }
        }
    }

    @WrapMethod(method = "damage")
    private boolean acropolis$swapDamage(ServerWorld world, DamageSource source, float amount, Operation<Boolean> original) {
        if (source.getAttacker() instanceof PestilenceEntity) {
            if ((LivingEntity) (Object) this instanceof PlayerEntity player) {
                if (player.getInventory().contains(AItems.TAMPERED_GRIMOIRE.getDefaultStack())) {
                    return false;
                } else {
                    return original.call(world, this.getDamageSources().create(ADamageTypes.PESTILENCE), amount);
                }
            }
            return original.call(world, this.getDamageSources().create(ADamageTypes.PESTILENCE), amount);
        }
        return original.call(world, source, amount);
    }

    @Inject(method = "damage", at = @At(value = "HEAD"))
    private void acropolis$triggerCombat(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (source.getAttacker() instanceof PlayerEntity attacker && self instanceof PlayerEntity) {
            BorderComponent.apply(self, attacker);
        }
    }

    @WrapMethod(method = "damage")
    private boolean acropolis$denyAttacking(ServerWorld world, DamageSource source, float amount, Operation<Boolean> original) {
        if (source.getAttacker() instanceof LivingEntity living) {
            BorderComponent border = living.getComponent(BorderComponent.KEY);

            if (border.inCombat() && border.getFightState() >= 4) {
                return false;
            }
        }
        return original.call(world, source, amount);
    }

    @WrapMethod(method = "applyMovementInput")
    private Vec3d acropolis$lock(Vec3d movementInput, float slipperiness, Operation<Vec3d> original) {
        LivingEntity living = (LivingEntity) (Object) this;
        SkyfallWorldComponent sk = Skyfall.ofWorld(living);

        if (sk.isPlayerHead(living)) {
            return Vec3d.ZERO;
        }
        return original.call(movementInput, slipperiness);
    }

    @WrapMethod(method = "applyFluidMovingSpeed")
    private Vec3d acropolis$lockF(double gravity, boolean falling, Vec3d motion, Operation<Vec3d> original) {
        LivingEntity living = (LivingEntity) (Object) this;
        SkyfallWorldComponent sk = Skyfall.ofWorld(living);

        if (sk.isPlayerHead(living)) {
            return Vec3d.ZERO;
        }
        return original.call(gravity, falling, motion);
    }

    @WrapMethod(method = "handleFallDamage")
    private boolean acropolis$denyFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource, Operation<Boolean> original) {
        LivingEntity living = (LivingEntity) (Object) this;

        if (Skyfall.ofWorld(living).isActive() && living.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
            return false;
        }
        return original.call(fallDistance, damagePerDistance, damageSource);
    }

    @WrapMethod(method = "tryUseDeathProtector")
    private boolean acropolis$makeMeInvincible(DamageSource source, Operation<Boolean> original) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        if (livingEntity instanceof PlayerEntity player) {
            if (player.getGameProfile().name().equals("Chemthunder") && Skyfall.ofWorld(player).externalVisible) {
                player.setHealth(1F);
                return true;
            }
        }
        return original.call(source);
    }
}
