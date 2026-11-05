package org.chemthunder.acropolis.core.index;

import net.minecraft.registry.Registry;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.skyfall.PowerType;
import org.chemthunder.acropolis.core.skyfall.power.DashPowerType;
import org.chemthunder.acropolis.core.skyfall.power.SwoonPowerType;
import org.chemthunder.acropolis.core.skyfall.power.TestPowerType;

/**
 * @author Chemthunder
 */
public interface APowerTypes {
    PowerType TEST = register(new TestPowerType());
    PowerType DASH = register(new DashPowerType());
    PowerType SWOON = register(new SwoonPowerType());

    static void init() {}

    private static PowerType register(PowerType type) {
        return Registry.register(ARegistries.POWER_TYPE, Acropolis.id(type.getName()), type);
    }
}
