package org.chemthunder.acropolis.core.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.EffectParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class FuneralBloomEntity extends Entity implements Ownable {
    private double spreadRadius = 0.0;

    public FuneralBloomEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void tick() {
        super.tick();

        if (spreadRadius < 6) {
            spreadRadius += 0.5;
        }

        if (spreadRadius >= 6) {
            this.discard();
        }

        World level = this.getEntityWorld();

        Box capture = new Box(this.getBlockPos()).expand(spreadRadius);

        LivingEntity owner = LazyEntityReference.getLivingEntity(this.getAttached(AAttachmentTypes.OWNERSHIP), level);

        for (LivingEntity target : level.getEntitiesByClass(LivingEntity.class, capture, EntityPredicates.EXCEPT_SPECTATOR)) {
            boolean bl = true;

            if (owner != null) {
                if (target == owner) {
                    bl = false;
                }
            }

            if (bl) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, (15 * 20), 1));
            }
        }

        for (int i = 0; i < 5; i++) {
            ParticleEffect effect = EffectParticleEffect.of(ParticleTypes.EFFECT, 0xFF000000, 1);

            level.addParticleClient(
                    effect,
                    this.getX() + (spreadRadius),
                    this.getY(),
                    this.getZ(),
                    0,
                    0,
                    0
            );

            level.addParticleClient(
                    effect,
                    this.getX() - (spreadRadius),
                    this.getY(),
                    this.getZ(),
                    0,
                    0,
                    0
            );

            level.addParticleClient(
                    effect,
                    this.getX(),
                    this.getY(),
                    this.getZ() + (spreadRadius),
                    0,
                    0,
                    0
            );

            level.addParticleClient(
                    effect,
                    this.getX(),
                    this.getY(),
                    this.getZ() - (spreadRadius),
                    0,
                    0,
                    0
            );

            level.addParticleClient(
                    effect,
                    this.getX() + (spreadRadius),
                    this.getY(),
                    this.getZ() + (spreadRadius),
                    0,
                    0,
                    0
            );

            level.addParticleClient(
                    effect,
                    this.getX() + (spreadRadius),
                    this.getY(),
                    this.getZ() - (spreadRadius),
                    0,
                    0,
                    0
            );

            level.addParticleClient(
                    effect,
                    this.getX() - (spreadRadius),
                    this.getY(),
                    this.getZ() - (spreadRadius),
                    0,
                    0,
                    0
            );

            level.addParticleClient(
                    effect,
                    this.getX() - (spreadRadius),
                    this.getY(),
                    this.getZ() + (spreadRadius),
                    0,
                    0,
                    0
            );
        }
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    protected void readCustomData(ReadView view) {
        this.spreadRadius = view.getDouble("SpreadRadius", 0.0);
    }

    protected void writeCustomData(WriteView view) {
        view.putDouble("SpreadRadius", spreadRadius);
    }

    @Nullable
    public Entity getOwner() {
        return LazyEntityReference.getLivingEntity(this.getAttached(AAttachmentTypes.OWNERSHIP), this.getEntityWorld());
    }
}

