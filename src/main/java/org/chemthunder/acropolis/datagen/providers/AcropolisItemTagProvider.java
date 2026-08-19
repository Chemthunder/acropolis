package org.chemthunder.acropolis.datagen.providers;

import net.acoyt.acornlib.impl.index.tag.AcornItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public AcropolisItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        AcropolisBlocks.PLUSHIES.forEach(plushRegistration -> this.valueLookupBuilder(AcornItemTags.PLUSHIES)
                .add(plushRegistration.plush().asItem())
                .setReplace(false));
    }
}
