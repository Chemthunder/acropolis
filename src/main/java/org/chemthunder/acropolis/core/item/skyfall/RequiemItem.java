package org.chemthunder.acropolis.core.item.skyfall;

import net.acoyt.acornlib.api.item.HitEffectsItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LazyEntityReference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.entity.FuneralBloomEntity;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AEntityTypes;

/**
 * @author Chemthunder
 */
public class RequiemItem extends Item implements HitEffectsItem {
    public RequiemItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(user.getStackInHand(hand))) {
            if (user.isOnGround()) {
                funeralBloom(world, user, hand);
            } else {
                shockwave(world, user, hand);
            }
        }
        return super.use(world, user, hand);
    }

    public void runHitEffects(PlayerEntity player, Entity target) {
        ParticleUtils.spawnSweepParticles(
                new SweepParticleEffect(
                        0xFF4c2828,
                        0xFF281217
                ),
                player
        );
    }

    private static void funeralBloom(World world, PlayerEntity player, Hand hand) {
        FuneralBloomEntity bloom = new FuneralBloomEntity(AEntityTypes.FUNERAL_BLOOM, world);
        bloom.setPosition(player.getEntityPos().add(0, 0.5, 0));
        bloom.setAttached(AAttachmentTypes.OWNERSHIP, LazyEntityReference.of(player));
        world.spawnEntity(bloom);

        player.getItemCooldownManager().set(player.getStackInHand(hand), (4 * 20));
    }

    private static void shockwave(World world, PlayerEntity player, Hand hand) {
        player.setVelocity(player.getRotationVec(0).multiply(4));
        player.setAttached(AAttachmentTypes.EXPECTING_SHOCKWAVE, true);

        player.getItemCooldownManager().set(player.getStackInHand(hand), (6 * 20));
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFFffc421);
    }
}

// Plunging the scythe downwards creates a shockwave that knocks entities back by 10 blocks

// Spreads particles that deal half a heart of damage per second while standing in them and gives Wither for 10 seconds