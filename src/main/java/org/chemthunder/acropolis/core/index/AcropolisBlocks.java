package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.acoyt.acornlib.data.provider.resources.AcornModelGen;
import net.acoyt.acornlib.impl.block.PlushBlock;
import net.acoyt.acornlib.impl.block.PlushBlockItem;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.BlockRenderLayer;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.datagen.providers.AcropolisRecipeProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public interface AcropolisBlocks {
    List<Block> PLUSHIES = new ArrayList<>();
    BlockRegistrant plugin = new BlockRegistrant(Acropolis.MOD_ID);

    Block FLEUR_PLUSH = plugin.registerWithItem("fleur_plush", PlushBlock::new, AbstractBlock.Settings.copy(Blocks.PINK_WOOL)
            .nonOpaque(), (block, settings) -> new PlushBlockItem(block, settings, 0xff8bfd));

    Block MEDE_PLUSH = plugin.registerWithItem("mede_plush", PlushBlock::new, AbstractBlock.Settings.copy(Blocks.PINK_WOOL)
            .nonOpaque(), (block, settings) -> new PlushBlockItem(block, settings, 0x4aff35));

    static void init() {
        ALib.registerPlush(FLEUR_PLUSH, AcropolisSoundEvents.FLEUR_PLUSH, 0xff8bfd);
        ALib.registerPlush(MEDE_PLUSH, AcropolisSoundEvents.MEDE_PLUSH, 0x4aff35);

        PLUSHIES.add(FLEUR_PLUSH);
        PLUSHIES.add(MEDE_PLUSH);

        AcropolisRecipeProvider.DATA.add(new AcropolisRecipeProvider.PlushRecipeData(FLEUR_PLUSH, List.of(
                Blocks.BLACK_WOOL,
                Blocks.PINK_WOOL,
                Blocks.BROWN_WOOL
        )));

        AcropolisRecipeProvider.DATA.add(new AcropolisRecipeProvider.PlushRecipeData(MEDE_PLUSH, List.of(
                Blocks.GREEN_WOOL,
                Blocks.PURPLE_WOOL,
                Blocks.BROWN_WOOL
        )));
    }

    static void clinit() {
        BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                FLEUR_PLUSH,
                MEDE_PLUSH
        );
    }
}
