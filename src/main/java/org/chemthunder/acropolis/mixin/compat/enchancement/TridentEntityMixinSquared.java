package org.chemthunder.acropolis.mixin.compat.enchancement;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = TridentEntity.class, priority = 2500)
public abstract class TridentEntityMixinSquared {

    @TargetHandler(
            mixin = "moriyashiine.enchancement.mixin.enchantmenteffectcomponenttype.teleportonhit.TridentEntityMixin",
            name = "enchancement$teleportOnHit"
    )
    @WrapMethod(method = "@MixinSquared:Handler")
    private void acropolis$removeWarp(World world, LivingEntity owner, ItemStack stack, CallbackInfo ci, Operation<Void> original) {
        if (!MercyComponent.KEY.get(world).isActive()) original.call(world, owner, stack, ci);
    }
}
