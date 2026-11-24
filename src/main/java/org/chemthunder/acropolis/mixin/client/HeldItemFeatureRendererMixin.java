package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin {
    @WrapMethod(method = "renderItem")
    private <S extends ArmedEntityRenderState> void acropolis$makeRaptureGlow(S entityState, ItemRenderState itemState, ItemStack stack, Arm arm, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, Operation<Void> original) {
        if (Boolean.TRUE.equals(entityState.getData(AcropolisClient.MATRIARCH))) {
            light = LightmapTextureManager.MAX_LIGHT_COORDINATE;
        }
        original.call(entityState, itemState, stack, arm, matrices, queue, light);
    }
}
