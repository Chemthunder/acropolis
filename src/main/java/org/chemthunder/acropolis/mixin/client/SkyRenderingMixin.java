package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.texture.AtlasManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.MoonPhase;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.AcropolisConfig;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.chemthunder.acropolis.core.AcropolisClient.mercyActive;

@Mixin(value = SkyRendering.class)
public abstract class SkyRenderingMixin {
    @Unique private static final Identifier CUSTOM_SUN_TEXTURE = Acropolis.id("eclipsed_sun");

    @Unique private GpuBuffer customSunVertexBuffer;
    @Unique private GpuBuffer customStarVertexBuffer;

    @Shadow @Final private SpriteAtlasTexture celestialAtlasTexture;
    @Shadow private int starIndexCount;

    @Shadow
    private static GpuBuffer createQuadVertexBuffer(String description, Sprite sprite) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow
    protected abstract void renderStars(float brightness, MatrixStack matrices);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void acropolis$createBuffer(TextureManager textureManager, AtlasManager atlasManager, CallbackInfo ci) {
        customSunVertexBuffer = createQuadVertexBuffer("Custom sun quad", this.celestialAtlasTexture.getSprite(CUSTOM_SUN_TEXTURE));
        customStarVertexBuffer = createCustomStars();
    }

    @Unique
    private GpuBuffer createCustomStars() {
        Random random = Random.create(10842L);
        float f = 100.0F;

        Sprite sprite = this.celestialAtlasTexture.getSprite(Acropolis.id("stars"));

        GpuBuffer var19;
        try (BufferAllocator bufferAllocator = BufferAllocator.fixedSized(VertexFormats.POSITION.getVertexSize() * 1500 * 4)) {
            BufferBuilder bufferBuilder = new BufferBuilder(bufferAllocator, VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

            for(int i = 0; i < 1500; ++i) {
                float g = random.nextFloat() * 2.0F - 1.0F;
                float h = random.nextFloat() * 2.0F - 1.0F;
                float j = random.nextFloat() * 2.0F - 1.0F;
                float k = 0.15F + random.nextFloat() * 0.1F;
                float l = MathHelper.magnitude(g, h, j);
                if (!(l <= 0.010000001F) && !(l >= 1.0F)) {
                    Vector3f vector3f = (new Vector3f(g, h, j)).normalize(100.0F);
                    float m = (float)(random.nextDouble() * (double)(float)Math.PI * (double)2.0F);
                    Matrix3f matrix3f = (new Matrix3f()).rotateTowards((new Vector3f(vector3f)).negate(), new Vector3f(0.0F, 1.0F, 0.0F)).rotateZ(-m);

                    bufferBuilder.vertex((new Vector3f(k, -k, 0.0F)).mul(matrix3f).add(vector3f)).texture(sprite.getMaxU(), sprite.getMaxV());
                    bufferBuilder.vertex((new Vector3f(k, k, 0.0F)).mul(matrix3f).add(vector3f)).texture(sprite.getMinU(), sprite.getMaxV());
                    bufferBuilder.vertex((new Vector3f(-k, k, 0.0F)).mul(matrix3f).add(vector3f)).texture(sprite.getMinU(), sprite.getMinV());
                    bufferBuilder.vertex((new Vector3f(-k, -k, 0.0F)).mul(matrix3f).add(vector3f)).texture(sprite.getMaxU(), sprite.getMinV());
                }
            }

            try (BuiltBuffer builtBuffer = bufferBuilder.end()) {
                this.starIndexCount = builtBuffer.getDrawParameters().indexCount();
                var19 = RenderSystem.getDevice().createBuffer(() -> "Custom stars vertex buffer", 40, builtBuffer.getBuffer());
            }
        }

        return var19;
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

    @Inject(
            method = "renderCelestialBodies",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V",
                    ordinal = 1
            )
    )
    private void acropolis$alwaysStars(MatrixStack matrices, float sunAngle, float moonAngle, float starAngle, MoonPhase moonPhase, float alpha, float starBrightness, CallbackInfo ci) {
        if (mercyActive()) {
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_X.rotation(18000));
            this.renderStars(starBrightness, matrices);
            matrices.pop();
        }
    }

    @WrapMethod(method = "renderCelestialBodies")
    private void acropolis$removeRainGradient(MatrixStack matrices, float sunAngle, float moonAngle, float starAngle, MoonPhase moonPhase, float alpha, float starBrightness, Operation<Void> original) {
        original.call(
                matrices,
                sunAngle,
                moonAngle,
                starAngle,
                moonPhase,
                mercyActive() ? 1.0F : alpha,
                mercyActive() ? 0.4F : starBrightness
        );
    }

    @WrapOperation(
            method = "renderCelestialBodies",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/SkyRendering;renderStars(FLnet/minecraft/client/util/math/MatrixStack;)V"
            )
    )
    private void acropolis$removeVanillaStars(SkyRendering instance, float brightness, MatrixStack matrices, Operation<Void> original) {
        if (!mercyActive()) {
            original.call(instance, brightness, matrices);
        }
    }

    @ModifyExpressionValue(
            method = "renderStars",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/SkyRendering;starVertexBuffer:Lcom/mojang/blaze3d/buffers/GpuBuffer;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private GpuBuffer acropolis$applyStars(GpuBuffer original) {
        return mercyActive() ? customStarVertexBuffer : original;
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
        customStarVertexBuffer.close();
    }

    @Inject(method = "updateRenderState", at = @At(value = "TAIL"))
    private void acropolis$applyTweaks(ClientWorld world, float tickProgress, Camera camera, SkyRenderState state, CallbackInfo ci) {
        if (mercyActive()) {
            state.sunAngle = 6000;
            state.skyColor = AcropolisConfig.epilepsyMode ? 0xFF693a7f : 0xFF8c2197;
        }
    }

    @WrapOperation(method = "renderGlowingSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBuffer;)V"))
    private void acropolis$removeSunrise(RenderPass instance, int i, GpuBuffer gpuBuffer, Operation<Void> original) {
        if (!mercyActive()) {
            original.call(instance, i, gpuBuffer);
        }
    }
}