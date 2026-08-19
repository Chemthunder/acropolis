package org.chemthunder.acropolis.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisRecipeProvider extends FabricRecipeProvider {
    public AcropolisRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        return new RecipeGenerator(registries, exporter) {
            public void generate() {
                AcropolisBlocks.PLUSHIES.forEach(plushRegistration ->
                        plushRegistration.inputs().forEach(block ->
                                offerStonecuttingRecipe(RecipeCategory.MISC, plushRegistration.plush(), block)
                        )
                );
            }
        };
    }

    public String getName() {
        return "Acropolis Recipes";
    }
}
