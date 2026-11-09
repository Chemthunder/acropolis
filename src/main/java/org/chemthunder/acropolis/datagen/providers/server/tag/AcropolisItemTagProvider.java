package org.chemthunder.acropolis.datagen.providers.server.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.index.tag.ATagKeys;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public AcropolisItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        List<TagKey<Item>> SWORD_TAGS = List.of(
                ItemTags.SWORDS,
                ItemTags.DURABILITY_ENCHANTABLE,
                ItemTags.SWEEPING_ENCHANTABLE,
                ItemTags.FIRE_ASPECT_ENCHANTABLE,
                ItemTags.SHARP_WEAPON_ENCHANTABLE,
                ItemTags.MELEE_WEAPON_ENCHANTABLE
        );

        List<Item> WEAPONS = List.of(
                AItems.RAPTURE
        );

        SWORD_TAGS.forEach(itemTagKey ->
                WEAPONS.forEach(item ->
                        this.valueLookupBuilder(itemTagKey)
                                .add(item)
                                .setReplace(false)
                )
        );

        this.valueLookupBuilder(ATagKeys.ITEM_TRANSFORMED)
                .add(AItems.RAPTURE)
                .add(AItems.CELESTIAL_GRIMOIRE)
                .setReplace(false);
    }
}
