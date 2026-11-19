package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.item.skyfall.RaptureItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

/**
 * @author Chemthunder
 */
@Mixin(value = DrawContext.class)
public abstract class DrawContextMixin {

    @WrapMethod(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;III)V")
    private void acropolis$shake(LivingEntity entity, World world, ItemStack stack, int x, int y, int seed, Operation<Void> original) {
        if (stack.getItem() instanceof RaptureItem) {
            if (RaptureItem.awakened(entity)) {
                Random rand = new Random();

                x += rand.nextInt(-RaptureItem.SHAKE_AMNT, RaptureItem.SHAKE_AMNT);
                y += rand.nextInt(-RaptureItem.SHAKE_AMNT, RaptureItem.SHAKE_AMNT);
            }
        }

        original.call(entity, world, stack, x, y, seed);
    }
}
