package org.chemthunder.acropolis.datagen.providers;

import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.data.provider.resources.AcornModelGen;
import net.acoyt.acornlib.impl.util.Util;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;
import org.chemthunder.acropolis.core.index.AcropolisItems;

/**
 * @author Chemthunder
 */
public class AcropolisModelProvider extends FabricModelProvider {
    public AcropolisModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(AcropolisItems.CELESTIAL_GRIMOIRE, Models.GENERATED);
//        itemModelGenerator.register(AcropolisItems.EXTOLLED_SIMULACRUM);
    }
}
