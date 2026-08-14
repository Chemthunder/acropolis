package org.chemthunder.acropolis.core.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;

/**
 * @author Chemthunder
 */
public class StareDamageEvent implements ServerTickEvents.StartWorldTick  {
    public void onStartTick(ServerWorld serverWorld) {
        serverWorld.getPlayers().forEach(serverPlayer -> {
            GazeComponent gaze = GazeComponent.KEY.get(serverWorld);

            if (gaze.isPresent() && gaze.getPos() != null) {
                HitResult hitResult = serverPlayer.raycast(150, 0.1F, true);
                Vec3d raycastPos = hitResult.getPos();

                Box detectionRange = new Box(new BlockPos.Mutable(gaze.getPos().x, gaze.getPos().y + 55, gaze.getPos().z)).expand(15);

                if (detectionRange.raycast(serverPlayer.getEntityPos(), raycastPos).isPresent()) {
                    serverPlayer.damage(serverWorld, serverPlayer.getDamageSources().create(AcropolisDamageTypes.GAZE), 1.0F);
                }
            }
        });
    }
}
