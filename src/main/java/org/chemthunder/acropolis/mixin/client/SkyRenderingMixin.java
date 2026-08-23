package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderPass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.texture.AtlasManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.MoonPhase;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SkyRendering.class)
public abstract class SkyRenderingMixin {
    @Unique private static final Identifier CUSTOM_SUN_TEXTURE = Acropolis.id("eclipsed_sun");
    @Unique private GpuBuffer customSunVertexBuffer;

    @Shadow @Final private SpriteAtlasTexture celestialAtlasTexture;

    @Shadow @Final private GpuBuffer sunRiseVertexBuffer;

    @Shadow
    private static GpuBuffer createQuadVertexBuffer(String description, Sprite sprite) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void acropolis$createBuffer(TextureManager textureManager, AtlasManager atlasManager, CallbackInfo ci) {
        customSunVertexBuffer = createQuadVertexBuffer("Custom sun quad", this.celestialAtlasTexture.getSprite(CUSTOM_SUN_TEXTURE));
    }

    @ModifyExpressionValue(
            method = "renderSun",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/SkyRendering;sunVertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private GpuBuffer acropolis$applyBuffer(GpuBuffer original) {
        return mercyActive() ? customSunVertexBuffer : original;
    }

    @WrapOperation(
            method = "renderSun",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Matrix4fStack;scale(FFF)Lorg/joml/Matrix4f;"
            )
    )
    private Matrix4f acropolis$applyMatrixRotations(Matrix4fStack instance, float x, float y, float z, Operation<Matrix4f> original) {
        if (mercyActive()) {
            instance.rotate(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.AGE + MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks()) / 4));
            return original.call(instance, x + 60, y, z + 60);
        }
        return original.call(instance, x, y, z);
    }

    @WrapMethod(method = "renderMoon")
    private void acropolis$haltMoonIfActive(MoonPhase moonPhase, float alpha, MatrixStack matrices, Operation<Void> original) {
        if (!mercyActive()) {
            original.call(moonPhase, alpha, matrices);
        }
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void fuckassMojang(CallbackInfo ci) {
        customSunVertexBuffer.close();
    }

    @Inject(method = "updateRenderState", at = @At(value = "TAIL"))
    private void acropolis$applyTweaks(ClientWorld world, float tickProgress, Camera camera, SkyRenderState state, CallbackInfo ci) {
        if (mercyActive()) {
            state.sunAngle = 6000;
            state.skyColor = 0xFF8c2197;
        }
    }

    @WrapOperation(method = "renderGlowingSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBuffer;)V"))
    private void acropolis$removesunrise(RenderPass instance, int i, GpuBuffer gpuBuffer, Operation<Void> original) {
        if (!mercyActive()) {
            original.call(instance, i, gpuBuffer);
        }
    }

    @Unique
    private static boolean mercyActive() {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            return MercyComponent.KEY.get(world).isActive();
        }
        return false;
    }
}