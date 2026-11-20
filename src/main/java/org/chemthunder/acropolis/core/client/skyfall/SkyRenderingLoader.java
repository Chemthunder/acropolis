package org.chemthunder.acropolis.core.client.skyfall;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.AcropolisConfig;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.chemthunder.acropolis.core.AcropolisClient.mercyActive;
import static org.chemthunder.acropolis.core.AcropolisClient.skyfallActive;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class SkyRenderingLoader {
    public static Matrix4f applySolarRotations(Matrix4fStack instance, float x, float y, float z, Operation<Matrix4f> original) {
        if (mercyActive()) {
            instance.rotate(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.age + MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks()) / 4));
            return original.call(instance, x + 60, y, z + 60);
        }

        if (skyfallActive()) {
            if (MinecraftClient.getInstance().world != null) {
                SkyfallWorldComponent fall = SkyfallWorldComponent.KEY.get(MinecraftClient.getInstance().world);

                instance.rotate(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.age + MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks()) / 4));
                instance.scale((EasingType.OUT_EXPO.apply(fall.getSunscale() / SkyfallWorldComponent.MAX_SUNSCALE) * SkyfallWorldComponent.MAX_SUNSCALE));

                return original.call(instance, x, y, z);
            }
        }
        return original.call(instance, x, y, z);
    }

    public static void tweakRenderState(ClientWorld world, float tickProgress, Camera camera, SkyRenderState state, CallbackInfo ci) {
        if (mercyActive()) {
            state.sunAngle = 6000;
            state.skyColor = AcropolisConfig.epilepsyMode ? 0xFF693a7f : 0xFF8c2197;
        }

        if (skyfallActive()) {
            state.sunAngle = 5925;
            state.skyColor = 0xFF000000;
            state.starBrightness = 0.0F;
        }

    }

    public static BufferBuilder buildSkyBuffer(BufferAllocator allocator, VertexFormat format) {
        final float dist = 16.0F;

        BufferBuilder bufferBuilder = new BufferBuilder(allocator, VertexFormat.DrawMode.QUADS, format);

        // top
        bufferBuilder.vertex(-dist, dist, -dist);
        bufferBuilder.vertex(dist, dist, -dist);
        bufferBuilder.vertex(dist, dist, dist);
        bufferBuilder.vertex(-dist, dist, dist);

        // bottom
        bufferBuilder.vertex(-dist, -dist, dist);
        bufferBuilder.vertex(dist, -dist, dist);
        bufferBuilder.vertex(dist, -dist, -dist);
        bufferBuilder.vertex(-dist, -dist, -dist);

        // north
        bufferBuilder.vertex(-dist, -dist, -dist);
        bufferBuilder.vertex(dist, -dist, -dist);
        bufferBuilder.vertex(dist, dist, -dist);
        bufferBuilder.vertex(-dist, dist, -dist);

        // south
        bufferBuilder.vertex(-dist, dist, dist);
        bufferBuilder.vertex(dist, dist, dist);
        bufferBuilder.vertex(dist, -dist, dist);
        bufferBuilder.vertex(-dist, -dist, dist);

        // west
        bufferBuilder.vertex(-dist, -dist, dist);
        bufferBuilder.vertex(-dist, -dist, -dist);
        bufferBuilder.vertex(-dist, dist, -dist);
        bufferBuilder.vertex(-dist, dist, dist);

        // east
        bufferBuilder.vertex(dist, -dist, -dist);
        bufferBuilder.vertex(dist, -dist, dist);
        bufferBuilder.vertex(dist, dist, dist);
        bufferBuilder.vertex(dist, dist, -dist);

        return bufferBuilder;
    }
}
