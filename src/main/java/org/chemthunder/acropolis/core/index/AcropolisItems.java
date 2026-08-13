package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.item.Item;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.core.item.RaptureItem;

/**
 * @author Chemthunder
 */
public interface AcropolisItems {
    ItemRegistrant plugin = new ItemRegistrant(Acropolis.MOD_ID);

    Item CELESTIAL_GRIMOIRE = plugin.register("celestial_grimoire", CelestialGrimoireItem::new, new Item.Settings()
            .maxCount(1)
            .fireproof()
    );

    Item RAPTURE = plugin.register("rapture", RaptureItem::new, new Item.Settings()
            .attributeModifiers(RaptureItem.attributes())
            .maxCount(1)
            .fireproof()
    );

    static void init() {}
}
