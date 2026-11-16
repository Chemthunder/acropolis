package org.chemthunder.acropolis.mixin.compat.enchancement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.enchancement.common.component.entity.AirJumpComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.chemthunder.acropolis.core.cca.entity.TargetComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = AirJumpComponent.class)
public abstract class AirJumpComponentMixin {
    @Shadow @Final private PlayerEntity obj;

    @ModifyReturnValue(method = "canUse", at = @At(value = "RETURN"))
    private boolean acropolis$stunlockDash(boolean original) {
        PlayerEntity obj = this.obj;

        TargetComponent target = obj.getComponent(TargetComponent.KEY);

        if (target.isFrozen()) {
            return false;
        }
        return original;
    }
}
