package org.chemthunder.acropolis.core.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.index.AcropolisSoundEvents;

/**
 * @author Chemthunder
 */
public class ExaltedIdolItem extends Item {
    public ExaltedIdolItem(Settings settings) {
        super(settings);
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        World world = entity.getEntityWorld();
        MercyComponent mercy = MercyComponent.KEY.get(world);

        if (!mercy.isActive()) {
            world.playSound(null, entity.getBlockPos(), AcropolisSoundEvents.MERCY_START, SoundCategory.MASTER, 1, 1);

            mercy.setCountdown(MercyComponent.COUNTDOWN);
        } else {
            mercy.setActive(false);
        }
    }
}
