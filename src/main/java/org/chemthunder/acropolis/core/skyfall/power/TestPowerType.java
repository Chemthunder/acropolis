package org.chemthunder.acropolis.core.skyfall.power;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.chemthunder.acropolis.core.skyfall.PowerHolder;
import org.chemthunder.acropolis.core.skyfall.PowerType;

/**
 * @author Chemthunder
 */
public class TestPowerType extends PowerType {
    public TestPowerType() {
        super(PowerHolder.CHEM, "test");
    }

    public void use(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.setVelocity(serverPlayer.getRotationVec(0).multiply(2));
            serverPlayer.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayer));
        }
    }

    public int cooldown() {
        return 0;
    }
}
