package org.chemthunder.acropolis.core.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.chemthunder.acropolis.core.Acropolis;

/**
 * @author Chemthunder
 */
public interface ATagKeys {
    TagBuilder<Item> item = new TagBuilder<>(Acropolis.MOD_ID, RegistryKeys.ITEM);

    TagKey<Item> ITEM_TRANSFORMED = item.register("transformed");
}
