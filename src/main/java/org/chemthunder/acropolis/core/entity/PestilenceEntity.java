package org.chemthunder.acropolis.core.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.index.AcropolisEntityTypes;
import org.chemthunder.acropolis.core.index.AcropolisItems;
import org.jspecify.annotations.Nullable;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class PestilenceEntity extends HostileEntity implements Ownable {
    private @Nullable LazyEntityReference<MobEntity> owner;

    private boolean targeted = false;

    public PestilenceEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 90, true);

        this.noClip = true;
    }

    public PestilenceEntity(World world) {
        super(AcropolisEntityTypes.PESTILENCE, world);
        this.moveControl = new FlightMoveControl(this, 90, true);

        this.noClip = true;
    }

    public void tick() {
        super.tick();

        if (this.age % 20 == 0) {
            if (new Random().nextInt(2) == 1) {
                this.targeted = !this.targeted;
            }
        }

        if (this.targeted) {
            PlayerEntity target = this.getEntityWorld().getClosestPlayer(this, 10);
            if (target != null) {
                this.setTarget(target);

                if (!target.getInventory().contains(AcropolisItems.TAMPERED_GRIMOIRE.getDefaultStack())) {
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
                            .multiply(0.6F);

                    Vec3d velocity = spawnPos.subtract(new Vec3d(
                                    this.getX(),
                                    this.getY() + 1.0F,
                                    this.getZ()
                            ))
                            .normalize()
                            .negate()
                            .multiply(this.getDistanceToCenter(target) / 10);

                    for (int i = 0; i < 1; i++) {
                        this.getEntityWorld().addParticleClient(
                                ParticleTypes.END_ROD,
                                spawnPos.x,
                                spawnPos.y,
                                spawnPos.z,
                                velocity.x,
                                velocity.y,
                                velocity.z
                        );
                    }

                    float strength = 15;

                    target.addVelocity(
                            new Vec3d(
                                    velocityExcluding.x / strength,
                                    velocityExcluding.y / strength,
                                    velocityExcluding.z / strength
                            )
                    );

                    target.velocityDirty = false;
                    return;
                }
            }
        }

        this.setTarget(null);
    }

    protected void initGoals() {
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(7, new FlyGoal(this, 2.0F));
        this.goalSelector.add(8, new AttackGoal(this));
        this.goalSelector.add(9, new PounceAtTargetGoal(this, 4));

        this.targetSelector.add(9, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(8, new PounceAtTargetGoal(this, 1));

        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return super.damage(world, source, amount / 2);
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

    public @Nullable MobEntity getOwner() {
        return LazyEntityReference.resolve(this.owner, this.getEntityWorld(), MobEntity.class);
    }

    public void setOwner(@Nullable LazyEntityReference<MobEntity> owner) {
        this.owner = owner;
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
