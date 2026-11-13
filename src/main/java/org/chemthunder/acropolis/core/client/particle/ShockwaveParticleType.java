package org.chemthunder.acropolis.core.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;

/**
 * @author Chemthunder
 */
public class ShockwaveParticleType extends AnimatedParticle {
    private final SpriteProvider provider;

    public ShockwaveParticleType(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, SpriteProvider provider) {
        super(world, x, y, z, provider, 0.0F);
        this.provider = provider;
        this.setVelocity(vx, vy, vz);

        this.maxAge = 72000;
        this.updateSprite(provider);
    }

    protected void render(BillboardParticleSubmittable submittable, Camera camera, Quaternionf rotation, float tickProgress) {
        super.render(submittable, camera, RotationAxis.POSITIVE_X.rotationDegrees(90), tickProgress);
        super.render(submittable, camera, RotationAxis.NEGATIVE_X.rotationDegrees(90), tickProgress);

        super.render(submittable, camera, RotationAxis.POSITIVE_Y.rotationDegrees(90), tickProgress);
        super.render(submittable, camera, RotationAxis.NEGATIVE_Y.rotationDegrees(90), tickProgress);

        super.render(submittable, camera, RotationAxis.POSITIVE_Z.rotationDegrees(90), tickProgress);
        super.render(submittable, camera, RotationAxis.NEGATIVE_Z.rotationDegrees(90), tickProgress);
    }

    public void tick() {
        super.tick();

        if (this.scale < 35) {
            this.scale += 0.5F;
        }

        if (this.alpha > 0) {
            this.alpha -= 0.035F;
        }

        if (this.alpha <= 0) {
            this.markDead();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, Random random) {
            return new ShockwaveParticleType(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}