package org.chemthunder.acropolis.core.entity;

import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;

import java.util.List;
import java.util.Random;

/**
 * @author Chemthunder
 */
public class CruciDaggerEntity extends ThrownEntity {
    public CruciDaggerEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    public void tick() {
        this.noClip = true;

        super.tick();

        this.noClip = true;

        if (this.age > (4 * 20)) {
            this.discard();
        }

        Vec3d pos = this.getEntityPos();
        World world = this.getEntityWorld();

        world.addParticleClient(
                new SweepParticleEffect(0xFF561060, 0xFFa31eb5),
                pos.x,
                pos.y,
                pos.z,
                0,
                0,
                0
        );

        for (int i = 0; i < 6; i++) {
            Random rand = new Random();

            world.addParticleClient(
                    ParticleTypes.SMOKE,
                    pos.x + rand.nextFloat(-0.5F, 0.5F),
                    pos.y + rand.nextFloat(-0.5F, 0.5F),
                    pos.z + rand.nextFloat(-0.5F, 0.5F),
                    0,
                    0,
                    0
            );

            world.addParticleClient(
                    ParticleTypes.PORTAL,
                    pos.x + rand.nextFloat(-0.5F, 0.5F),
                    pos.y + rand.nextFloat(-0.5F, 0.5F),
                    pos.z + rand.nextFloat(-0.5F, 0.5F),
                    0,
                    0,
                    0
            );
        }

        Box hitbox = new Box(this.getBlockPos()).expand(6);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, hitbox, EntityPredicates.EXCEPT_SPECTATOR);

        Entity entity = this.getOwner();

        if (!entities.isEmpty()) {
            for (LivingEntity living : entities) {
                if (!living.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
                    if (entity instanceof LivingEntity ownerEntity && !ownerEntity.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
                        if (ownerEntity != living) {
                            calcAndApply(living, world);
                        }
                    } else {
                        calcAndApply(living, world);
                    }
                }
            }
        }
    }

    private void calcAndApply(LivingEntity target, World world) {
        Box detect = new Box(this.getBlockPos()).expand(50);
        List<CruciDaggerEntity> daggers = world.getEntitiesByClass(CruciDaggerEntity.class, detect, EntityPredicates.EXCEPT_SPECTATOR);

        float output = 2.0F;

        for (CruciDaggerEntity dagger : daggers) {
            output += 2.0F;

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD,
                        dagger.getX(),
                        dagger.getY(),
                        dagger.getZ(),
                        6,
                        0,
                        0,
                        0,
                        0.01F
                );
            }

            world.playSound(
                    null,
                    dagger.getX(),
                    dagger.getY(),
                    dagger.getZ(),
                    SoundEvents.ENTITY_ALLAY_ITEM_THROWN,
                    SoundCategory.PLAYERS,
                    1,
                    new Random().nextFloat(0.4F, 0.9F)
            );

            dagger.discard();
        }

        if (world instanceof ServerWorld serverWorld) {
            target.damage(serverWorld, target.getDamageSources().create(ADamageTypes.DAGGER), output);
        }
    }

    public boolean hasNoGravity() {
        return true;
    }
}

