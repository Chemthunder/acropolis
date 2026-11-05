package org.chemthunder.acropolis.core.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.chemthunder.acropolis.core.cca.world.SightComponent;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;

/**
 * @author Chemthunder
 */
public class SightDamageEvent implements ServerTickEvents.StartWorldTick {
    public void onStartTick(ServerWorld serverWorld) {
        SightComponent sight = SightComponent.KEY.get(serverWorld);

        serverWorld.getPlayers().forEach(capture -> tickHitboxes(capture, sight, serverWorld));
    }

    private void tickHitboxes(ServerPlayerEntity serverPlayer, SightComponent sight, ServerWorld serverWorld) {
        if (sight.getPos() != null) {
            Box hitbox = new Box(new BlockPos.Mutable(sight.getPos().x, sight.getPos().y, sight.getPos().z))
                    .expand(5, 360, 5);

            if (hitbox.contains(serverPlayer.getEntityPos())) {
                serverPlayer.damage(serverWorld, serverPlayer.getDamageSources().create(ADamageTypes.RETRIBUTION), 4.0F);
            }
        }
    }
}
