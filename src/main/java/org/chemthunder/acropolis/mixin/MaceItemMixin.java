package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MaceItem;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = MaceItem.class)
public abstract class MaceItemMixin {

    @WrapMethod(method = "shouldDealAdditionalDamage")
    private static boolean acropolis$FUCKBLUDOTSCRENSPECIFICALLYIFUCKINGHATEYOUIAHFOUFBRE(LivingEntity attacker, Operation<Boolean> original) {
        World world = attacker.getEntityWorld();

        if (world.getComponent(SkyfallComponent.KEY).isActive()) {
            return false;
        }
        return original.call(attacker);
    }
}
