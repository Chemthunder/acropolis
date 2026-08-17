package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.sound.BlockSoundGroup;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.block.DimLanternBlock;

import java.util.Collections;

/**
 * @author Chemthunder
 */
public interface AcropolisBlocks {
    BlockRegistrant plugin = new BlockRegistrant(Acropolis.MOD_ID);

    Block DIM_LANTERN = plugin.registerWithItem("dim_lantern", DimLanternBlock::new, AbstractBlock.Settings.copy(Blocks.LANTERN)
            .luminance(value -> 1)
            .emissiveLighting((state, world, pos) -> true)
            .sounds(BlockSoundGroup.LANTERN)
    );

    static void init() {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                Collections.singletonList(DIM_LANTERN.asItem()),
                (builder, item) -> builder.add(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
        ));
    }

    static void clinit() {}
}
