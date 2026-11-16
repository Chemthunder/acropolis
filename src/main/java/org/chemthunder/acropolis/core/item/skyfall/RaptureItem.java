package org.chemthunder.acropolis.core.item.skyfall;

import net.acoyt.acornlib.api.item.CritEffectItem;
import net.acoyt.acornlib.api.item.HitEffectsItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.entity.TargetComponent;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.entity.CruciDaggerEntity;
import org.chemthunder.acropolis.core.index.AEntityTypes;
import org.chemthunder.acropolis.core.networking.s2c.ShoutPayload;
import org.chemthunder.acropolis.core.utilities.Skyfall;

/**
 * @author Chemthunder
 */
public class RaptureItem extends Item implements HitEffectsItem, CritEffectItem {
    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0xFF0d0508, 0xFF1a151e),
            new SweepParticleEffect(0xFF605662, 0xFF3c333e)
    };

    public RaptureItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFF39355c);
    }

    public void runHitEffects(PlayerEntity player, Entity entity) {
        ParticleUtils.spawnSweepParticles(EFFECTS[Random.create().nextInt(EFFECTS.length)], player);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        SkyfallComponent skyfall = Skyfall.getInstance(world);
        TargetComponent tc = user.getComponent(TargetComponent.KEY);

        if (!tc.matriarch) {
            if (skyfall.isActive()) {
                tc.awakenMatriarch();
                return ActionResult.SUCCESS;
            }
        } else {
            Vec3d ray = user.raycast(0.6, 0, true).getPos();

            for (int i = 0; i < 3; i++) {
                CruciDaggerEntity dagger = new CruciDaggerEntity(AEntityTypes.DAGGER, world);

                java.util.Random rand = new java.util.Random();

                dagger.setVelocity(user.getRotationVec(0).multiply(2));
                dagger.setPosition(ray.add(rand.nextFloat(-0.677F, 0.677F)));

                world.spawnEntity(dagger);
            }

            user.swingHand(hand);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.SMOKE,
                        ray.x,
                        ray.y,
                        ray.z,
                        10,
                        0,
                        0,
                        0,
                        0.25F
                );

                serverWorld.spawnParticles(
                        ParticleTypes.PORTAL,
                        ray.x,
                        ray.y,
                        ray.z,
                        10,
                        0,
                        0,
                        0,
                        0.26F
                );
            }
        }
        return super.use(world, user, hand);
    }

    public void critEffect(PlayerEntity player, ItemStack stack, Entity target) {
        if (target instanceof PlayerEntity living) {
            living.getComponent(TargetComponent.KEY).freeze();

            if (player instanceof ServerPlayerEntity a) {
                ServerPlayNetworking.send(a, new ShoutPayload("FALL", 0xFFff0000));
            }

            if (living instanceof ServerPlayerEntity a) {
                ServerPlayNetworking.send(a, new ShoutPayload("FALL", 0xFFff0000));
            }

            Skyfall.shout("FALL", 0xFFff0000, new PlayerEntity[]{player, living});

            player.getEntityWorld().playSound(
                    null,
                    living.getX(),
                    living.getY(),
                    living.getZ(),
                    SoundEvents.ENTITY_ALLAY_DEATH,
                    SoundCategory.PLAYERS,
                    1,
                    0.3F
            );
        }
    }

    public boolean hasGlint(ItemStack stack) {
        return false;
    }
}

// Fall
// Complete hit chain of five. After a few seconds, deal 4 hearts true damage
// and pop totem if existing
