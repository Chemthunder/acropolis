package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.acoyt.acornlib.impl.block.PlushBlock;
import net.acoyt.acornlib.impl.block.PlushBlockItem;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.sound.SoundEvent;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.datagen.providers.AcropolisRecipeProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public interface AcropolisBlocks {
    List<PlushRegistration> PLUSHIES = new ArrayList<>();
    BlockRegistrant plugin = new BlockRegistrant(Acropolis.MOD_ID);

    Block FLEUR_PLUSH = plugin.registerWithItem("fleur_plush", PlushBlock::new, AbstractBlock.Settings.copy(Blocks.PINK_WOOL)
            .nonOpaque(), (block, settings) -> new PlushBlockItem(block, settings, 0xff8bfd));

    Block MEDE_PLUSH = plugin.registerWithItem("mede_plush", PlushBlock::new, AbstractBlock.Settings.copy(Blocks.PINK_WOOL)
            .nonOpaque(), (block, settings) -> new PlushBlockItem(block, settings, 0x4aff35));

    static void init() {
        PLUSHIES.add(new PlushRegistration(FLEUR_PLUSH, AcropolisSoundEvents.FLEUR_PLUSH, "the op lumiere", 0xff8bfd, List.of(
                Blocks.BLACK_WOOL,
                Blocks.PINK_WOOL,
                Blocks.BROWN_WOOL
        )));

        PLUSHIES.add(new PlushRegistration(MEDE_PLUSH, AcropolisSoundEvents.MEDE_PLUSH, "mikeiscooler than me", 0x4aff35, List.of(
                Blocks.GREEN_WOOL,
                Blocks.PURPLE_WOOL,
                Blocks.BLACK_WOOL
        )));
    }

    static void clinit() {
        PLUSHIES.forEach(plushRegistration -> BlockRenderLayerMap.putBlock(plushRegistration.plush, BlockRenderLayer.CUTOUT));
    }

    record PlushRegistration(Block plush, SoundEvent squishSound, String desc, int descColor, List<Block> inputs) {}
}
