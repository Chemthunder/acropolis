package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {
    @WrapMethod(method = "renderArmor")
    private <S extends BipedEntityRenderState> void acropolis$hideArmorIfMatriarch(MatrixStack matrices, OrderedRenderCommandQueue queue, ItemStack stack, EquipmentSlot slot, int light, S state, Operation<Void> original) {
        if (Boolean.FALSE.equals(state.getData(AcropolisClient.MATRIARCH))) {
            original.call(matrices, queue, stack, slot, light, state);
        }
    }
}
