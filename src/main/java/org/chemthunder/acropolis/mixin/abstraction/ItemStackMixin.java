package org.chemthunder.acropolis.mixin.abstraction;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = ItemStack.class)
public abstract class ItemStackMixin {
    @WrapMethod(method = "use")
    private ActionResult abs$use(World world, PlayerEntity user, Hand hand, Operation<ActionResult> original) {
        if (Skyfall.ofEntity(user).abstractedTicks > 0) {
            return ActionResult.FAIL;
        }
        return original.call(world, user, hand);
    }

    @WrapMethod(method = "useOnEntity")
    private ActionResult abs$useOnEntity(PlayerEntity user, LivingEntity entity, Hand hand, Operation<ActionResult> original) {
        if (Skyfall.ofEntity(user).abstractedTicks > 0) {
            return ActionResult.FAIL;
        }
        return original.call(user, entity, hand);
    }

    @WrapMethod(method = "useOnBlock")
    private ActionResult abs$useOnBlock(ItemUsageContext context, Operation<ActionResult> original) {
        if (context.getPlayer() != null && Skyfall.ofEntity(context.getPlayer()).abstractedTicks > 0) {
            return ActionResult.FAIL;
        }
        return original.call(context);
    }
}
