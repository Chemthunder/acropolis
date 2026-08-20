package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.builder.KeyedBuilder;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.chemthunder.acropolis.core.Acropolis;

/**
 * @author Chemthunder
 */
public interface AcropolisDamageTypes {
    KeyedBuilder<DamageType> plugin = new KeyedBuilder<>(Acropolis.MOD_ID, RegistryKeys.DAMAGE_TYPE);

    RegistryKey<DamageType> GAZE = plugin.register("gaze", new DamageType("gaze", 2.0F));
    RegistryKey<DamageType> SMOKE = plugin.register("smoke", new DamageType("smoke", 2.0F));
    RegistryKey<DamageType> RETRIBUTION = plugin.register("retribution", new DamageType("retribution", 0.0F));

    RegistryKey<DamageType> RAPTURE = plugin.register("rapture", new DamageType("rapture", 0.0F));
}
