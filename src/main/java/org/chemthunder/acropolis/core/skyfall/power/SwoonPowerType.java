package org.chemthunder.acropolis.core.skyfall.power;

import net.minecraft.entity.player.PlayerEntity;
import org.chemthunder.acropolis.core.cca.entity.AvatarComponent;
import org.chemthunder.acropolis.core.skyfall.PowerHolder;
import org.chemthunder.acropolis.core.skyfall.PowerType;

/**
 * @author Chemthunder
 */
public class SwoonPowerType extends PowerType {
    public SwoonPowerType() {
        super(PowerHolder.NICKEL, "fall");
    }

    public void use(PlayerEntity player) {
        player.getComponent(AvatarComponent.KEY).swoon();
    }

    public int cooldown() {
        return 0;
    }
}
