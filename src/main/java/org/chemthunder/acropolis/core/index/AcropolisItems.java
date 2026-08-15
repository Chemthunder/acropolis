package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.core.item.ExtolledSimulacrumItem;

import java.util.function.Function;

/**
 * @author Chemthunder
 */
public interface AcropolisItems {
    Item CELESTIAL_GRIMOIRE = register("celestial_grimoire", CelestialGrimoireItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
            .fireproof()
    );

    Item EXTOLLED_SIMULACRUM = register("extolled_simulacrum", ExtolledSimulacrumItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
            .fireproof()
    );

    private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return Items.register(RegistryKey.of(RegistryKeys.ITEM, Acropolis.id(name)), factory, settings);
    }

    static void init() {
        Acropolis.LOGGER.info("Test");
    }
}
