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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import org.chemthunder.acropolis.core.cca.entity.TargetComponent;
import org.chemthunder.acropolis.core.networking.s2c.ShoutPayload;

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

    public void critEffect(PlayerEntity player, ItemStack stack, Entity target) {
        if (target instanceof PlayerEntity living) {
            TargetComponent targetComponent = living.getComponent(TargetComponent.KEY);
            targetComponent.freeze();

            if (player instanceof ServerPlayerEntity a) {
                ServerPlayNetworking.send(a, new ShoutPayload("FALL", 0xFFff0000));
            }

            if (living instanceof ServerPlayerEntity a) {
                ServerPlayNetworking.send(a, new ShoutPayload("FALL", 0xFFff0000));
            }

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
// Complete hit chain of five. After a few seconds, deal 4 hearts true damage and pop totem if existing