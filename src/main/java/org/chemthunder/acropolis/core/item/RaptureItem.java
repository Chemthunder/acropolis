package org.chemthunder.acropolis.core.item;

import net.acoyt.acornlib.api.item.HitEffectsItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

/**
 * @author Chemthunder
 */
public class RaptureItem extends Item implements HitEffectsItem {
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
}
