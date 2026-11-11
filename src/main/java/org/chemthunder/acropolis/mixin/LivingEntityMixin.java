package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.waypoint.ServerWaypoint;
import org.chemthunder.acropolis.core.cca.entity.BorderComponent;
import org.chemthunder.acropolis.core.entity.PestilenceEntity;
import org.chemthunder.acropolis.core.index.AComponentTypes;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.chemthunder.acropolis.core.item.ExtolledSimulacrumItem;
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

    @Inject(method = "tryUseDeathProtector", at = @At(value = "HEAD"))
    private void acropolis$chargeSimulacrum(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = source.getAttacker();

        if (entity instanceof PlayerEntity player) {
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack slot = player.getInventory().getStack(i);

                if (slot.getItem() instanceof ExtolledSimulacrumItem) {
                    if (slot.getOrDefault(AComponentTypes.CHARGES, 0) != 3) {
                        slot.set(AComponentTypes.CHARGES, slot.getOrDefault(AComponentTypes.CHARGES, 0) + 1);
                    }
                }
            }
        }
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
}
