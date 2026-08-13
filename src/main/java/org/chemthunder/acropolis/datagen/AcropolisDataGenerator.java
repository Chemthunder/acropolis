package org.chemthunder.acropolis.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;
import org.chemthunder.acropolis.datagen.providers.AcropolisDamageTypeTagProvider;
import org.chemthunder.acropolis.datagen.providers.AcropolisDynamicRegistryProvider;
import org.chemthunder.acropolis.datagen.providers.AcropolisLanguageProvider;
import org.chemthunder.acropolis.datagen.providers.AcropolisModelProvider;

public class AcropolisDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fdg) {
        var pack = fdg.createPack();

        pack.addProvider(AcropolisDamageTypeTagProvider::new);

        pack.addProvider(AcropolisDynamicRegistryProvider::new);

        pack.addProvider(AcropolisLanguageProvider::new);
        pack.addProvider(AcropolisModelProvider::new);
	}

    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, AcropolisDamageTypes.plugin::bootstrap);
    }
}
