package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.block.MeteorBlock;

/**
 * @author Chemthunder
 */
public interface AcropolisBlocks {
    BlockRegistrant plugin = new BlockRegistrant(Acropolis.MOD_ID);

    Block METEOR = plugin.registerWithItem("meteor", MeteorBlock::new, AbstractBlock.Settings.copy(Blocks.ANCIENT_DEBRIS)
            .sounds(BlockSoundGroup.ANCIENT_DEBRIS)
            .luminance(value -> 4)
            .emissiveLighting((state, world, pos) -> true)
    );

    static void init() {}

    static void clinit() {}
}
