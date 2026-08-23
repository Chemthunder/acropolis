package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WeatherRendering;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.Acropolis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static org.chemthunder.acropolis.core.AcropolisClient.mercyActive;

/**
 * @author Chemthunder
 */
@Mixin(value = WeatherRendering.class)
public abstract class WeatherRenderingMixin {
    @WrapOperation(
            method = "renderPrecipitation",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/RenderLayers;weather(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;",
                    ordinal = 0
            )
    )
    private RenderLayer acropolis$swapRainTexture(Identifier texture, boolean depth, Operation<RenderLayer> original) {
        return original.call(mercyActive() ? Acropolis.id("textures/environment/rain.png") : texture, depth);
    }
}
