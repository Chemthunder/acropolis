package org.chemthunder.acropolis.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public AcropolisDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        AcropolisDamageTypes.plugin.addEntries(wrapperLookup, entries);
    }

    public String getName() {
        return "Acropolis Registries";
    }
}
