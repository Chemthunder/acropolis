package org.chemthunder.acropolis.core.utilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AParticleTypes;
import org.chemthunder.acropolis.core.index.ASoundEvents;

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

        for (LivingEntity target : world.getEntitiesByClass(LivingEntity.class, capture, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)) {
            if (target != player) {
                target.setVelocity(target.getRotationVec(0).multiply(-4));
                target.addVelocity(0, 4, 0);
            }
        }

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
