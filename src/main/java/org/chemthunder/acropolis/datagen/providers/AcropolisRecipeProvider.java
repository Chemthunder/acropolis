package org.chemthunder.acropolis.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisRecipeProvider extends FabricRecipeProvider {
    public static final List<PlushRecipeData> DATA = new ArrayList<>();

    public AcropolisRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            public void generate() {
                DATA.forEach(plushRecipeData -> {
                    plushRecipeData.inputs.forEach(block -> offerStonecuttingRecipe(RecipeCategory.MISC, plushRecipeData.output, block));
                });
            }
        };
    }

    public String getName() {
        return "Acropolis Recipes";
    }

    public record PlushRecipeData(Block output, List<Block> inputs) {}
}
