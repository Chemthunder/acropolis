package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.*;
import org.chemthunder.acropolis.core.item.component.OmenComponent;

import java.util.ArrayList;

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

    Item TAMPERED_GRIMOIRE = plugin.register("tampered_grimoire", TamperedGrimoireItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("tampered"))
            .fireproof()
    );

    Item FATAL_OMEN = plugin.register("fatal_omen", FatalOmenItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("omen"))
            .component(AcropolisComponentTypes.OMEN, new OmenComponent(new ArrayList<>(), true, false))
            .fireproof()
    );

    Item EXTOLLED_SIMULACRUM = plugin.register("extolled_simulacrum", ExtolledSimulacrumItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
            .component(AcropolisComponentTypes.CHARGES, 0)
            .fireproof()
    );

    Item RAPTURE = plugin.register("rapture", RaptureItem::new, new Item.Settings()
            .maxCount(1)
            .sword(ToolMaterial.NETHERITE, 3.0F, -2.4F)
            .fireproof()
            .enchantable(4)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("rapture"))
    );

    Item EXALTED_IDOL = plugin.register("exalted_idol", ExaltedIdolItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
    );

    static void init() {}
}
