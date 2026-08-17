package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;

/**
 * @author Chemthunder
 */
public interface AcropolisItems {
    ItemRegistrant plugin = new ItemRegistrant(Acropolis.MOD_ID);

    Item CELESTIAL_GRIMOIRE = plugin.register("celestial_grimoire", CelestialGrimoireItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
            .fireproof()
    );

//    Item EXTOLLED_SIMULACRUM = plugin.register("extolled_simulacrum", ExtolledSimulacrumItem::new, new Item.Settings()
//            .maxCount(1)
//            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
//            .component(AcropolisComponentTypes.CHARGES, 0)
//            .fireproof()
//    );

    static void init() {}
}
