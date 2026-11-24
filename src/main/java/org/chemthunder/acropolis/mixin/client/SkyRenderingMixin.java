package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.MoonPhase;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.mixin.SkyRenderingLoader;
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

import java.util.OptionalInt;

import static org.chemthunder.acropolis.core.AcropolisClient.*;

@Mixin(value = SkyRendering.class)
public abstract class SkyRenderingMixin {
    @Unique private static final Identifier CUSTOM_SUN_TEXTURE = Acropolis.id("eclipsed_sun");

    @Unique private GpuBuffer customSunVertexBuffer;
    @Unique private GpuBuffer customStarVertexBuffer;
    @Unique private GpuBuffer customTopSkyBuffer;

    @Shadow @Final private SpriteAtlasTexture celestialAtlasTexture;
    @Shadow private int starIndexCount;

    @Shadow
    private static GpuBuffer createQuadVertexBuffer(String description, Sprite sprite) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Shadow protected abstract void renderStars(float brightness, MatrixStack matrices);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void acropolis$createBuffer(TextureManager textureManager, AtlasManager atlasManager, CallbackInfo ci) {
        customSunVertexBuffer = createQuadVertexBuffer("Custom sun quad", this.celestialAtlasTexture.getSprite(CUSTOM_SUN_TEXTURE));

        customStarVertexBuffer = createCustomStars();

        customTopSkyBuffer = createCustomSkyBuffer();
    }

    @Unique
    private GpuBuffer createCustomSkyBuffer() {
        VertexFormat vertexFormat = VertexFormats.POSITION;

        try (BufferAllocator bufferAllocator = BufferAllocator.fixedSized(36 * vertexFormat.getVertexSize())) {
            BufferBuilder bufferBuilder = SkyRenderingLoader.buildSkyBuffer(bufferAllocator, vertexFormat);

            try (BuiltBuffer builtBuffer = bufferBuilder.end()) {
                return RenderSystem.getDevice().createBuffer(() -> "Custom Top Sky quad", GpuBuffer.USAGE_VERTEX, builtBuffer.getBuffer());
            }
        }
    }

    @Unique
    private GpuBuffer createCustomStars() {
        Random random = Random.create(10842L);
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
        return (mercyActive() || skyfallActive()) ? customSunVertexBuffer : original;
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
        if (mercyActive() || skyfallActive()) {
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
                (mercyActive() || skyfallActive()) ? 1.0F : alpha,
                (mercyActive() || skyfallActive()) ? 0.4F : starBrightness
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
        if (!(mercyActive() || skyfallActive())) {
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
        return (mercyActive() || skyfallActive()) ? customStarVertexBuffer : original;
    }

    @WrapOperation(
            method = "renderSun",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Matrix4fStack;scale(FFF)Lorg/joml/Matrix4f;"
            )
    )
    private Matrix4f acropolis$applyMatrixRotations(Matrix4fStack instance, float x, float y, float z, Operation<Matrix4f> original) {
        return SkyRenderingLoader.applySolarRotations(instance, x, y, z, original);
    }

    @WrapMethod(method = "renderMoon")
    private void acropolis$haltMoonIfActive(MoonPhase moonPhase, float alpha, MatrixStack matrices, Operation<Void> original) {
        if (!(mercyActive() || fallIns().isActive())) {
            original.call(moonPhase, alpha, matrices);
        }
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void fuckassMojang(CallbackInfo ci) {
        customSunVertexBuffer.close();
        customStarVertexBuffer.close();
        customTopSkyBuffer.close();
    }

    @Inject(method = "updateRenderState", at = @At(value = "TAIL"))
    private void acropolis$applyTweaks(ClientWorld world, float tickProgress, Camera camera, SkyRenderState state, CallbackInfo ci) {
        SkyRenderingLoader.tweakRenderState(world, tickProgress, camera, state, ci);
    }

    @WrapOperation(
            method = "renderGlowingSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBuffer;)V"
            )
    )
    private void acropolis$removeSunrise(RenderPass instance, int i, GpuBuffer gpuBuffer, Operation<Void> original) {
        if (!(mercyActive() || skyfallActive())) {
            original.call(instance, i, gpuBuffer);
        }
    }

    @WrapMethod(method = "renderTopSky")
    private void redirect(int i, Operation<Void> original) {
        if (fallIns().externalVisible) {
            createFathomedSky(i);
        }
    }

    @Unique
    private void createFathomedSky(int i) {
        GpuBufferSlice gpuBufferSlice = RenderSystem.getDynamicUniforms()
                .write(RenderSystem.getModelViewMatrix(), ColorHelper.toRgbaVector(i), new Vector3f(), new Matrix4f());
        GpuTextureView gpuTextureView = MinecraftClient.getInstance().getFramebuffer().getColorAttachmentView();

        TextureManager manager = MinecraftClient.getInstance().getTextureManager();

        AbstractTexture sampler0 = manager.getTexture(Identifier.ofVanilla("textures/environment/end_sky.png"));
        AbstractTexture sampler1 = manager.getTexture(Identifier.ofVanilla("textures/entity/end_portal.png"));

        RenderSystem.ShapeIndexBuffer quadIndices = RenderSystem.getSequentialBuffer(VertexFormat.DrawMode.QUADS);
        GpuBuffer indexBuffer = quadIndices.getIndexBuffer(36);

        try (RenderPass renderPass = RenderSystem.getDevice()
                .createCommandEncoder()
                .createRenderPass(() -> "Sky disc", gpuTextureView, OptionalInt.empty())) {

            renderPass.setPipeline(RenderPipelines.END_PORTAL);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", gpuBufferSlice);

            renderPass.bindTexture(
                    "Sampler0",
                    sampler0.getGlTextureView(),
                    sampler0.getSampler()
            );

            renderPass.bindTexture(
                    "Sampler1",
                    sampler1.getGlTextureView(),
                    sampler1.getSampler()
            );

            renderPass.setVertexBuffer(0, this.customTopSkyBuffer);

            renderPass.setIndexBuffer(indexBuffer, quadIndices.getIndexType());
            renderPass.drawIndexed(0, 0, 36, 1);
        }
    }
}