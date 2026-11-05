package org.chemthunder.acropolis.core.index;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.skyfall.PowerType;

/**
 * @author Chemthunder
 */
public interface ARegistries {
    RegistryKey<Registry<PowerType>> POWER_TYPE_REGISTRY_KEY = RegistryKey.ofRegistry(Acropolis.id("power_type"));
    Registry<PowerType> POWER_TYPE = FabricRegistryBuilder.createSimple(POWER_TYPE_REGISTRY_KEY)
            .attribute(RegistryAttribute.MODDED)
            .buildAndRegister();

    static void init() {}
}
