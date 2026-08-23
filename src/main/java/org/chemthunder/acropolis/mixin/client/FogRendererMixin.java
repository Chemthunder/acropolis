package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
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
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world != null) {
            if (MercyComponent.KEY.get(world).isActive()) {
                return ColorHelper.toRgbaVector(0xFF000000);
            }
        }
        return original;
    }
}
