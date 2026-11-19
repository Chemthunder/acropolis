package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.entity.TargetComponent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

    @WrapOperation(
            method = "renderCrosshair",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 0
            )
    )
    private void acropolis$replaceCrosshair(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original) {
        PlayerEntity player = this.getCameraPlayer();
        if (player == null) return;

        TargetComponent cmp = player.getComponent(TargetComponent.KEY);

        if (cmp.dashBuffer > 0) {
            sprite = Acropolis.id("hud/buffer_crosshair");
        }

        original.call(instance, pipeline, sprite, x, y, width, height);
    }
}
