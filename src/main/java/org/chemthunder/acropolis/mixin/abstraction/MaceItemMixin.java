package org.chemthunder.acropolis.mixin.abstraction;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MaceItem;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = MaceItem.class)
public abstract class MaceItemMixin {
    @WrapMethod(method = "shouldDealAdditionalDamage")
    private static boolean abs$maces(LivingEntity attacker, Operation<Boolean> original) {
        return original.call(attacker) && Skyfall.ofEntity(attacker).abstractedTicks <= 0;
    }
}
