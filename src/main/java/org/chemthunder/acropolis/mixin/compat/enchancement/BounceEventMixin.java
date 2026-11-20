package org.chemthunder.acropolis.mixin.compat.enchancement;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import moriyashiine.enchancement.common.component.entity.BounceComponent;
import moriyashiine.enchancement.common.event.enchantmenteffectcomponenttype.BounceEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = BounceEvent.Bounce.class)
public abstract class BounceEventMixin {
    @WrapMethod(method = "shouldBounce")
    private static boolean acropolis$denyBouncy(LivingEntity entity, BounceComponent bounceComponent, Operation<Boolean> original) {
        if (Skyfall.ofEntity(entity).lockMovementTicks > 0) {
            return false;
        }
        return original.call(entity, bounceComponent);
    }

    @WrapMethod(method = "preventsFallDamage")
    private TriState acropolis$denyBouncy(World world, LivingEntity entity, double fallDistance, float damagePerDistance, DamageSource damageSource, Operation<TriState> original) {
        if (Skyfall.ofEntity(entity).lockMovementTicks > 0) {
            return TriState.FALSE;
        }
        return original.call(world, entity, fallDistance, damagePerDistance, damageSource);
    }
}
