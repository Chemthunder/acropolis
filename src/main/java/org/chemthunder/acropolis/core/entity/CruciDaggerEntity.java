package org.chemthunder.acropolis.core.entity;

import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
        super.tick();

        Vec3d pos = this.getEntityPos();
        World world = this.getEntityWorld();

        world.addParticleClient(
                new SweepParticleEffect(0xFF605662, 0xFF3c333e),
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

        Box hitbox = new Box(this.getBlockPos()).expand(15);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, hitbox, EntityPredicates.EXCEPT_SPECTATOR);

        for (LivingEntity living : entities) {
            calcAndApply(living, world);
        }
    }

    private void calcAndApply(LivingEntity target, World world) {
        Box detect = new Box(this.getBlockPos()).expand(50);
        List<CruciDaggerEntity> daggers = world.getEntitiesByClass(CruciDaggerEntity.class, detect, EntityPredicates.EXCEPT_SPECTATOR);

        float output = 2.0F;

        for (CruciDaggerEntity dagger : daggers) {
            output += 2.0F;
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
