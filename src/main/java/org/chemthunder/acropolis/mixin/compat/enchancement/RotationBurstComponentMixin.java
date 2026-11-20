package org.chemthunder.acropolis.mixin.compat.enchancement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.enchancement.common.component.entity.RotationBurstComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallEntityComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = RotationBurstComponent.class)
public abstract class RotationBurstComponentMixin {
    @Shadow @Final private PlayerEntity obj;

    @ModifyReturnValue(method = "canUse", at = @At(value = "RETURN"))
    private boolean acropolis$stunlockGale(boolean original) {
        PlayerEntity obj = this.obj;

        SkyfallEntityComponent target = obj.getComponent(SkyfallEntityComponent.KEY);

        if (target.isFrozen()) {
            return false;
        }
        return original;
    }
}
