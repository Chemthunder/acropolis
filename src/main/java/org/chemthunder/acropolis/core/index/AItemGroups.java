package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.CreativeModeTabRegistrant;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import org.chemthunder.acropolis.core.Acropolis;

import static org.chemthunder.acropolis.core.Acropolis.MOD_ID;

/**
 * @author Chemthunder
 */
public interface AItemGroups {
    CreativeModeTabRegistrant plugin = new CreativeModeTabRegistrant(MOD_ID);

    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Acropolis.id(MOD_ID));
    ItemGroup ITEM_GROUP = plugin.register(GROUP_KEY.getValue().getPath(), FabricItemGroup.builder()
            .icon(() -> new ItemStack(AItems.CELESTIAL_GRIMOIRE))
            .displayName(Text.translatable("itemGroup." + MOD_ID).withColor(0xFF9740aa))
            .build());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(AItemGroups::addEntries);
    }

    private static void addEntries(FabricItemGroupEntries entries) {
        for (Item item : AItems.plugin.toRegister) {
            entries.add(item);
        }
    }
}
