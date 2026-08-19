package org.chemthunder.acropolis.core.utilities;

import net.minecraft.entity.player.PlayerEntity;
import org.chemthunder.acropolis.core.cca.entity.PlayerDataComponent;

/**
 * @author Chemthunder
 */
public class PlayerData {
    public static boolean trusted(PlayerEntity player) {
        return PlayerDataComponent.KEY.get(player).isTrusted() || PlayerDataComponent.KEY.get(player).isSanctum();
    }
}
