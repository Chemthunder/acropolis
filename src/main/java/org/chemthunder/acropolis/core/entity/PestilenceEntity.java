package org.chemthunder.acropolis.core.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Targeter;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.index.AEntityTypes;
import org.chemthunder.acropolis.core.index.AItems;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class PestilenceEntity extends HostileEntity implements Targeter {
    private boolean targeted = false;

    public PestilenceEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 90, true);

        this.noClip = true;
    }

    public PestilenceEntity(World world) {
        super(AEntityTypes.PESTILENCE, world);
        this.moveControl = new FlightMoveControl(this, 90, true);

        this.noClip = true;
    }

    public void tick() {
        super.tick();

        if (this.getTarget() != null) {
            this.setTarget(null);
        }

        if (this.age % 20 == 0) {
            if (new Random().nextInt(2) == 1) {
                this.targeted = !this.targeted;
            }
        }

        if (this.targeted) {
            PlayerEntity target = this.getEntityWorld().getClosestPlayer(this, 90);
            if (target != null) {
                if (!target.getInventory().contains(AItems.TAMPERED_GRIMOIRE.getDefaultStack())) {
                    this.setTarget(target);

                    Random random1 = new Random();
                    float bound = 0.4F;

                    Vec3d spawnPos = new Vec3d(
                            target.getX() + random1.nextFloat(-bound, bound),
                            (target.getY() + 1.0F) + random1.nextFloat(-bound, bound),
                            target.getZ() + random1.nextFloat(-bound, bound)
                    );

                    Vec3d velocityExcluding = spawnPos.subtract(new Vec3d(
                                    this.getX(),
                                    this.getY() + 1.0F,
                                    this.getZ()
                            ))
                            .normalize()
                            .negate()
                            .multiply(0.3F);

                    Vec3d velocity = spawnPos.subtract(new Vec3d(
                                    this.getX(),
                                    this.getY() + 1.0F,
                                    this.getZ()
                            ))
                            .normalize()
                            .negate()
                            .multiply(this.getDistanceToCenter(target) / 10);

                    target.getEntityWorld().addParticleClient(
                            ParticleTypes.END_ROD,
                            spawnPos.x,
                            spawnPos.y,
                            spawnPos.z,
                            velocity.x,
                            velocity.y,
                            velocity.z
                    );

                    float strength = 15;

                    target.addVelocity(
                            new Vec3d(
                                    velocityExcluding.x / strength,
                                    velocityExcluding.y / strength,
                                    velocityExcluding.z / strength
                            )
                    );

                    target.velocityDirty = false;

                    target.getHungerManager().addExhaustion(1.0F);
                }
            }
        }
    }

    protected void initGoals() {
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(7, new FlyGoal(this, 4.0F));
        this.goalSelector.add(8, new AttackGoal(this));
        this.goalSelector.add(9, new PounceAtTargetGoal(this, 4));

        this.targetSelector.add(9, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return super.damage(world, source, amount / 6);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setMaxFollowRange(48.0F);
        return birdNavigation;
    }

    public void travel(Vec3d movementInput) {
        this.travelFlying(movementInput, this.getMovementSpeed());
    }

    public boolean hasNoGravity() {
        return true;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, 14.0F)
                .add(EntityAttributes.ATTACK_DAMAGE, 4.0F)
                .add(EntityAttributes.FLYING_SPEED, 0.1F)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1F);
    }

    protected void tickCramming() {}

    protected void readCustomData(ReadView view) {
        super.readCustomData(view);

        this.targeted = view.getBoolean("Targeted", false);
    }

    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);

        view.putBoolean("Targeted", targeted);
    }

    public Text getName() {
        return Text.literal("PESTILENCE").withColor(0xFFa71010).formatted(Formatting.OBFUSCATED, Formatting.BOLD);
    }

    public boolean hasCustomName() {
        return false;
    }
}
