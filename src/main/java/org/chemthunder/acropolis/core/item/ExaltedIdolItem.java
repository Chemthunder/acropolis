package org.chemthunder.acropolis.core.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;

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

        mercy.setActive(true);
        mercy.setSkyBrightness(1.0F);
    }
}
