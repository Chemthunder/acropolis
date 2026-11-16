package org.chemthunder.acropolis.core.utilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.entity.TargetComponent;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AParticleTypes;
import org.chemthunder.acropolis.core.index.ASoundEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public abstract class ShockwaveHandler {
    public static void additionalTick(PlayerEntity player) {
        if (player.isOnGround()) {
            player.setAttached(AAttachmentTypes.EXPECTING_SHOCKWAVE, false);
            trigger(player);
        }
    }

    public static void trigger(PlayerEntity player) {
        World world = player.getEntityWorld();
        Vec3d pos = player.getEntityPos();

        world.addParticleClient(
                AParticleTypes.SHOCKWAVE,
                pos.x,
                pos.y + 0.5,
                pos.z,
                0,
                0,
                0
        );

        Box capture = new Box(new BlockPos.Mutable(pos.x + 0.5, pos.y, pos.z + 0.5)).expand(15, 60, 15);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, capture, EntityPredicates.EXCEPT_SPECTATOR);

        for (LivingEntity target : entities) {
            if (target != player) {
                target.setVelocity(target.getRotationVec(0).multiply(-4));
                target.addVelocity(0, 4, 0);
                target.getComponent(TargetComponent.KEY).stunlock();
            }
        }

        List<ServerPlayerEntity> players = new ArrayList<>();

        for (LivingEntity target : entities) {
            if (target instanceof ServerPlayerEntity serverPlayer) {
                players.add(serverPlayer);
            }
        }

        Skyfall.shout("I WILL NOT FALTER", 0xFF1580A1, players);

        world.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                ASoundEvents.SHOCKWAVE,
                SoundCategory.PLAYERS,
                1,
                1
        );
    }
}
