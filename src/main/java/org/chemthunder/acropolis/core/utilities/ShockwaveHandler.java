package org.chemthunder.acropolis.core.utilities;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallEntityComponent;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.networking.s2c.ShockwavePayload;

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

            if (SkyfallEntityComponent.KEY.get(player).dashBuffer > 0) {
                SkyfallEntityComponent.KEY.get(player).dashBuffer = 0;
                SkyfallEntityComponent.KEY.get(player).sync();
            }
        }
    }

    public static void trigger(PlayerEntity player) {
        World world = player.getEntityWorld();
        Vec3d pos = player.getEntityPos();

        Box capture = new Box(new BlockPos.Mutable(pos.x + 0.5, pos.y, pos.z + 0.5)).expand(9, 60, 9);

        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, capture, EntityPredicates.EXCEPT_SPECTATOR);
        List<ServerPlayerEntity> players = new ArrayList<>();

        for (LivingEntity target : entities) {
            if (target instanceof ServerPlayerEntity serverPlayer) {
                players.add(serverPlayer);
            }
        }

        for (ServerPlayerEntity sp : players) {
            ServerPlayNetworking.send(sp, new ShockwavePayload(pos));
        }

        for (LivingEntity target : entities) {
            if (target != player && !target.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
                target.setVelocity(target.getRotationVec(0).multiply(-4));
                target.addVelocity(0, 4, 0);
                target.getComponent(SkyfallEntityComponent.KEY).stunlock();

                if (world instanceof ServerWorld serverWorld) {
                    for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                        serverPlayer.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
                    }
                }
            }
        }
    }
}
