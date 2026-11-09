package org.chemthunder.acropolis.core.utilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.entity.TargetComponent;

/**
 * @author Chemthunder
 */
public abstract class Skyfall {
    public static void rapture$fall(PlayerEntity player, World world, Entity target) {
        if (target instanceof LivingEntity living) {
            living.getComponent(TargetComponent.KEY).freeze();
        }
    }
}
