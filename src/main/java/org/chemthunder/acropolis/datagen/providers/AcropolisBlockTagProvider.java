package org.chemthunder.acropolis.datagen.providers;

import net.acoyt.acornlib.impl.index.tag.AcornBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public AcropolisBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.valueLookupBuilder(AcornBlockTags.PLUSHIES)
                .add(AcropolisBlocks.PLUSHIES)
                .setReplace(false);
    }
}
