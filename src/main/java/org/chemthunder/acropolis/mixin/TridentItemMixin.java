package org.chemthunder.acropolis.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(value = TridentItem.class)
public abstract class TridentItemMixin {
    @Inject(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;addVelocity(DDD)V"
            ),
            cancellable = true
    )
    private void acropolis$noRiptide(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
        if (MercyComponent.KEY.get(world).isActive()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;useRiptide(IFLnet/minecraft/item/ItemStack;)V"
            ),
            cancellable = true
    )
    private void acropolis$noRiptideAnimation(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfoReturnable<Boolean> cir) {
        if (MercyComponent.KEY.get(world).isActive()) {
            cir.setReturnValue(false);
        }
    }
}
