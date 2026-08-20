package org.chemthunder.acropolis.core.item;

import net.acoyt.acornlib.api.item.CustomHitParticleItem;
import net.acoyt.acornlib.api.item.CustomHitSoundItem;
import net.acoyt.acornlib.api.item.CustomKillSourceItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;
import org.chemthunder.acropolis.core.index.AcropolisSoundEvents;

/**
 * @author Chemthunder
 */
public class RaptureItem extends Item implements CustomHitParticleItem, CustomHitSoundItem, CustomKillSourceItem {
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

    public void spawnHitParticles(PlayerEntity player, Entity target) {
        ParticleUtils.spawnSweepParticles(EFFECTS[Random.create().nextInt(EFFECTS.length)], player);
    }

    public void playHitSound(PlayerEntity player, Entity target) {
        player.getEntityWorld().playSound(
                null,
                target.getBlockPos(),
                AcropolisSoundEvents.RAPTURE_HIT,
                SoundCategory.PLAYERS,
                1,
                new java.util.Random().nextFloat(0.3F, 0.7F)
        );
    }

    public DamageSource getKillSource(LivingEntity living) {
        return living.getDamageSources().create(AcropolisDamageTypes.RAPTURE);
    }
}
