package org.chemthunder.acropolis.core.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class SwarmEntity extends ThrownEntity {
    public SwarmEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public void tick() {
        super.tick();

        World world = this.getEntityWorld();

        this.setPos(this.getX(), this.getY() + 0.1F, this.getZ());

        if (this.age == (10 * 20)) {
            for (int i = 0; i < 100; i++) {
                PestilenceEntity p = new PestilenceEntity(world);

                Random random1 = new Random();
                float bound = 0.5F;

                Vec3d spawnPos = new Vec3d(
                        this.getX() + random1.nextFloat(-bound, bound),
                        this.getY() + random1.nextFloat(-bound, bound),
                        this.getZ() + random1.nextFloat(-bound, bound)
                );

                Vec3d velocity = this.getEntityPos().subtract(spawnPos).normalize().negate().multiply(0.5F);

                p.setPosition(this.getEntityPos());
                p.setVelocity(velocity);

                world.spawnEntity(p);

                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            ParticleTypes.SMOKE,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            50,
                            0,
                            0,
                            0,
                            1.4F
                    );
                }
                this.discard();
            }
        }

        if (this.age >= (11 * 20)) {
            this.discard();
        }
    }

    public boolean hasNoGravity() {
        return true;
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    protected void readCustomData(ReadView view) {}

    protected void writeCustomData(WriteView view) {}
}
