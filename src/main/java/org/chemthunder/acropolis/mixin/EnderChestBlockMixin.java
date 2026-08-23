package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = EnderChestBlock.class)
public abstract class EnderChestBlockMixin {
    @WrapMethod(method = "onUse")
    private ActionResult acropolis$noEchests(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, Operation<ActionResult> original) {
        if (MercyComponent.KEY.get(world).isActive()) {
            return ActionResult.FAIL;
        }
        return original.call(state, world, pos, player, hit);
    }
}
