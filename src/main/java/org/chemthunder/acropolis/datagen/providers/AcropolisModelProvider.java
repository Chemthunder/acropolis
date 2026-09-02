package org.chemthunder.acropolis.datagen.providers;

import net.acoyt.acornlib.api.util.DataUtils;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.client.item.OmenProperty;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;
import org.chemthunder.acropolis.core.index.AcropolisItems;

/**
 * @author Chemthunder
 */
public class AcropolisModelProvider extends FabricModelProvider {
    public AcropolisModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(AcropolisBlocks.METEOR);
    }

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(AcropolisItems.CELESTIAL_GRIMOIRE, Models.GENERATED);
        itemModelGenerator.register(AcropolisItems.EXALTED_IDOL, Models.GENERATED);
        itemModelGenerator.register(AcropolisItems.TAMPERED_GRIMOIRE, Models.GENERATED);
//        itemModelGenerator.register(AcropolisItems.EXTOLLED_SIMULACRUM);

        DataUtils.createSimpleGuiVarying(itemModelGenerator, AcropolisItems.RAPTURE);

        createFatalOmen(itemModelGenerator);
    }

    private void createFatalOmen(ItemModelGenerator generator) {
        Item item = AcropolisItems.FATAL_OMEN;
        Identifier id = ModelIds.getItemModelId(item);

        generator.output.accept(item,
                ItemModels.condition(
                        new OmenProperty(),
                        ItemModels.basic(
                                Models.GENERATED.upload(
                                        id.withSuffixedPath("_closed"),
                                        TextureMap.layer0(id.withSuffixedPath("_closed")),
                                        generator.modelCollector
                                )
                        ),
                        ItemModels.basic(
                                Models.GENERATED.upload(
                                        id.withSuffixedPath("_open"),
                                        TextureMap.layer0(id.withSuffixedPath("_open")),
                                        generator.modelCollector
                                )
                        )
                ));
    }
}
