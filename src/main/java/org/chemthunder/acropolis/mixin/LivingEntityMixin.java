package org.chemthunder.acropolis.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.chemthunder.acropolis.core.index.AcropolisComponentTypes;
import org.chemthunder.acropolis.core.item.ExtolledSimulacrumItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tryUseDeathProtector", at = @At(value = "HEAD"))
    private void acropolis$chargeSimulacrum(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = source.getAttacker();

        if (entity instanceof PlayerEntity player) {
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack slot = player.getInventory().getStack(i);

                if (slot.getItem() instanceof ExtolledSimulacrumItem) {
                    if (slot.getOrDefault(AcropolisComponentTypes.CHARGES, 0) != 3) {
                        slot.set(AcropolisComponentTypes.CHARGES, slot.getOrDefault(AcropolisComponentTypes.CHARGES, 0) + 1);
                    }
                }
            }
        }
    }
}
