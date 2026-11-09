package org.chemthunder.acropolis.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract void setHolder(@Nullable Entity holder);

    @Inject(method = "inventoryTick", at = @At(value = "TAIL"))
    private void acropolis$fix(World world, Entity entity, EquipmentSlot slot, CallbackInfo ci) {
        this.setHolder(entity);
    }
}
