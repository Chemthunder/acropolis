package org.chemthunder.acropolis.datagen.providers.client;

import net.acoyt.acornlib.api.util.DataUtils;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.property.select.DisplayContextProperty;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.client.item.AwakenRaptureProperty;
import org.chemthunder.acropolis.core.client.item.FatalOmenProperty;
import org.chemthunder.acropolis.core.index.ABlocks;
import org.chemthunder.acropolis.core.index.AItems;

import java.util.Arrays;

/**
 * @author Chemthunder
 */
public class AcropolisModelProvider extends FabricModelProvider {
    public AcropolisModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ABlocks.METEOR);
    }

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(AItems.CELESTIAL_GRIMOIRE, Models.GENERATED);
        itemModelGenerator.register(AItems.EXALTED_IDOL, Models.GENERATED);
        itemModelGenerator.register(AItems.TAMPERED_GRIMOIRE, Models.GENERATED);
//        itemModelGenerator.register(AcropolisItems.EXTOLLED_SIMULACRUM);

//        DataUtils.createSimpleGuiVarying(itemModelGenerator, AItems.RAPTURE);
        DataUtils.createSimpleGuiVarying(itemModelGenerator, AItems.REQUIEM);

        createFatalOmen(itemModelGenerator);
        createRapture(itemModelGenerator);
    }

    private void createFatalOmen(ItemModelGenerator generator) {
        Item item = AItems.FATAL_OMEN;
        Identifier id = ModelIds.getItemModelId(item);

        generator.output.accept(item,
               ItemModels.select(
                       new FatalOmenProperty(),
                       ItemModels.switchCase(
                               "omen_closed",
                               ItemModels.basic(
                                       Models.GENERATED.upload(
                                               id.withSuffixedPath("_closed"),
                                               TextureMap.layer0(id.withSuffixedPath("_closed")),
                                               generator.modelCollector
                                       )
                               )
                       ),
                       ItemModels.switchCase(
                               "omen_open",
                               ItemModels.basic(
                                       Models.GENERATED.upload(
                                               id.withSuffixedPath("_open"),
                                               TextureMap.layer0(id.withSuffixedPath("_open")),
                                               generator.modelCollector
                                       )
                               )
                       ),
                       ItemModels.switchCase(
                               "omen_finalized",
                               ItemModels.basic(
                                       Models.GENERATED.upload(
                                               id.withSuffixedPath("_finalized"),
                                               TextureMap.layer0(id.withSuffixedPath("_finalized")),
                                               generator.modelCollector
                                       )
                               )
                       )
               )
        );
    }

    private void createRapture(ItemModelGenerator generator) {
        Item item = AItems.RAPTURE;
        Identifier id = ModelIds.getItemModelId(item);

        generator.output.accept(item,
                ItemModels.condition(
                        new AwakenRaptureProperty(),
                        ItemModels.select(
                                new DisplayContextProperty(),
                                ItemModels.basic(id.withPath(st -> st + "_awakened_in_hand")),
                                ItemModels.switchCase(
                                        Arrays.asList(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED, ItemDisplayContext.ON_SHELF),
                                        ItemModels.basic(
                                                Models.GENERATED.upload(
                                                        id.withPath(st -> st + "_awakened"),
                                                        TextureMap.layer0(id.withPath(st -> st + "_awakened")),
                                                        generator.modelCollector
                                                )
                                        )
                                )
                        ),
                        ItemModels.select(
                                new DisplayContextProperty(),
                                ItemModels.basic(id.withPath(st -> st + "_in_hand")),
                                ItemModels.switchCase(
                                        Arrays.asList(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED, ItemDisplayContext.ON_SHELF),
                                        ItemModels.basic(
                                                Models.GENERATED.upload(
                                                        id,
                                                        TextureMap.layer0(id),
                                                        generator.modelCollector
                                                )
                                        )
                                )
                        )
                ));
    }
}
