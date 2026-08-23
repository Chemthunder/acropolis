package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.util.math.ColorHelper;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = FogRenderer.class)
public abstract class FogRendererMixin {
    @ModifyReturnValue(method = "getFogColor", at = @At(value = "RETURN"))
    private Vector4f acropolis$fogColor(Vector4f original) {
        if (AcropolisClient.mercyActive()) {
            return ColorHelper.toRgbaVector(0xFF000000);
        }
        return original;
    }
}
