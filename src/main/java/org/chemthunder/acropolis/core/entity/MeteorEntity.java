package org.chemthunder.acropolis.core.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class MeteorEntity extends ThrownEntity {
    public MeteorEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    public void tick() {
        super.tick();

        World world = this.getEntityWorld();

        for (int i = 0; i < 6; i++) {
            Random rand = new Random();

            world.addParticleClient(
                    ParticleTypes.SMOKE,
                    this.getX() + rand.nextFloat(-1, 1),
                    this.getY() + rand.nextFloat(-1, 1),
                    this.getZ() + rand.nextFloat(-1, 1),
                    0,
                    0,
                    0
            );
        }
    }

    protected void onBlockCollision(BlockState state) {
        if (!state.isAir()) {
            this.discard();
        }
    }
}
