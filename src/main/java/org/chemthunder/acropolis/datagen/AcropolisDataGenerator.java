package org.chemthunder.acropolis.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.chemthunder.acropolis.datagen.providers.client.AcropolisLanguageProvider;
import org.chemthunder.acropolis.datagen.providers.client.AcropolisModelProvider;
import org.chemthunder.acropolis.datagen.providers.client.AcropolisParticleProvider;
import org.chemthunder.acropolis.datagen.providers.client.AcropolisSoundsProvider;
import org.chemthunder.acropolis.datagen.providers.server.tag.AcropolisDamageTypeTagProvider;
import org.chemthunder.acropolis.datagen.providers.server.AcropolisDynamicRegistryProvider;
import org.chemthunder.acropolis.datagen.providers.server.tag.AcropolisItemTagProvider;

public class AcropolisDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fdg) {
        var pack = fdg.createPack();

        pack.addProvider(AcropolisDamageTypeTagProvider::new);
        pack.addProvider(AcropolisItemTagProvider::new);

        pack.addProvider(AcropolisDynamicRegistryProvider::new);

        pack.addProvider(AcropolisLanguageProvider::new);
        pack.addProvider(AcropolisModelProvider::new);
        pack.addProvider(AcropolisSoundsProvider::new);
        pack.addProvider(AcropolisParticleProvider::new);
	}

    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, ADamageTypes.plugin::bootstrap);
    }
}
