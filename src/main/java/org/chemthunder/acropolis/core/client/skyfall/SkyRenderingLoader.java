package org.chemthunder.acropolis.core.client.skyfall;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.AcropolisConfig;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.chemthunder.acropolis.core.AcropolisClient.fallActive;
import static org.chemthunder.acropolis.core.AcropolisClient.mercyActive;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class SkyRenderingLoader {
    public static Matrix4f applySolarRotations(Matrix4fStack instance, float x, float y, float z, Operation<Matrix4f> original) {
        if (mercyActive()) {
            instance.rotate(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.AGE + MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks()) / 4));
            return original.call(instance, x + 60, y, z + 60);
        }

        if (fallActive()) {
            SkyfallComponent fall = SkyfallComponent.KEY.get(MinecraftClient.getInstance().world);

            instance.rotate(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.AGE + MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks()) / 4));
            instance.scale((EasingType.OUT_EXPO.apply(fall.getSunscale() / SkyfallComponent.MAX_SUNSCALE) * SkyfallComponent.MAX_SUNSCALE));

            return original.call(instance, x, y, z);
        }
        return original.call(instance, x, y, z);
    }

    public static void tweakRenderState(ClientWorld world, float tickProgress, Camera camera, SkyRenderState state, CallbackInfo ci) {
        if (mercyActive()) {
            state.sunAngle = 6000;
            state.skyColor = AcropolisConfig.epilepsyMode ? 0xFF693a7f : 0xFF8c2197;
        }

        if (fallActive()) {
            state.sunAngle = 5925;
            state.skyColor = 0xFF000000;
            state.starBrightness = 0.0F;
        }
    }
}
