package org.chemthunder.acropolis.core.skyfall;

import net.minecraft.entity.player.PlayerEntity;

/**
 * @author Chemthunder
 */
public abstract class PowerType {
    private final PowerHolder holder;
    private final String name;

    public PowerType(PowerHolder holder, String name) {
        this.holder = holder;
        this.name = name;
    }

    public PowerHolder getHolder() {
        return holder;
    }

    public String getName() {
        return name;
    }

    public abstract void use(PlayerEntity player);

    public abstract int cooldown();
}
