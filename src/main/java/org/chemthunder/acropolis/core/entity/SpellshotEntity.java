package org.chemthunder.acropolis.core.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class SpellshotEntity extends ThrownEntity {
    private boolean collided = false;

    public SpellshotEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public void tick() {
        super.tick();

        if (this.age >= 400) {
            this.discard();
        }

        if (collided) {
            if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
                this.getEntityWorld().getEntitiesByClass(PlayerEntity.class, new Box(this.getBlockPos()).expand(2.5, 2, 2.5), entity -> true).forEach(entity -> {
                    if (false) {
                        entity.damage(serverWorld, entity.getDamageSources().create(AcropolisDamageTypes.SMOKE), 4.0F);
                    }
                });
            }
        }

        this.getEntityWorld().addImportantParticleClient(
                ParticleTypes.END_ROD,
                this.getX(),
                this.getY(),
                this.getZ(),
                0,
                0,
                0
        );

        for (int i = 0; i < 6; i++) {
            this.getEntityWorld().addImportantParticleClient(
                    ParticleTypes.SMOKE,
                    this.getX() + new Random().nextFloat(-0.4F, 0.4F),
                    this.getY(),
                    this.getZ() + new Random().nextFloat(-0.4F, 0.4F),
                    0,
                    0,
                    0
            );
        }
    }

    protected void onBlockCollision(BlockState state) {
        if (!collided) {
            if (!state.isIn(BlockTags.AIR)) {
                this.getEntityWorld().playSound(this, this.getBlockPos(), SoundEvents.ENTITY_ALLAY_DEATH, SoundCategory.HOSTILE, 1, 1 + new Random().nextFloat(-0.5F, 0.5F));
                collided = true;
            }
        }
    }

    public void writeData(WriteView view) {
        view.putBoolean("Collided", collided);
    }

    public void readData(ReadView view) {
        view.getBoolean("Collided", collided);
    }

    protected void initDataTracker(DataTracker.Builder builder) {}
}
