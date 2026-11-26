package org.chemthunder.acropolis.core.cca.skyfall.obj;

import net.acoyt.acornlib.api.util.NetworkingUtils;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.api.nbt.LoadedComponentValue;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallEntityComponent;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.networking.s2c.ShoutPayload;
import org.chemthunder.acropolis.core.networking.s2c.TriggerAbstractionPayload;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * @author Chemthunder
 */
public class AbstractionInstance implements LoadedComponentValue<AbstractionInstance> {
    public @Nullable Vec3d pos;
    public @Nullable UUID userUuid;

    public static final double RANGE = 20;

    private final World world;

    public AbstractionInstance(World world) {
        this.world = world;
        this.pos = null;
        this.userUuid = null;
    }

    public void trigger(LivingEntity user) {
        if (this.pos != null) {
            Box capture = new Box(new BlockPos.Mutable(this.pos.x, this.pos.y, this.pos.z)).expand(RANGE);

            NetworkingUtils.sendForAllPlayers(world, new TriggerAbstractionPayload());

            for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, capture, (living -> true))) {
                if (!living.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
                    SkyfallEntityComponent skec = Skyfall.ofEntity(living);

                    skec.abstractedTicks = (5 * 20);
                    skec.sync();

                    if (living instanceof ServerPlayerEntity serverPlayer) {
                        ServerPlayNetworking.send(serverPlayer, new ShoutPayload("HALT", 0xFFffffff));
                    }
                }
            }
        }
    }

    public AbstractionInstance read(ReadView view) {
        AbstractionInstance abs = new AbstractionInstance(world);

        abs.pos = view.read("AbstractionPos", Vec3d.CODEC).orElse(null);
        abs.userUuid = view.read("AbsUserUUID", Uuids.CODEC).orElse(null);

        return abs;
    }

    public void write(WriteView view) {
        if (pos != null) {
            view.put("AbstractionPos", Vec3d.CODEC, pos);
        }
        if (userUuid != null) {
            view.put("AbsUserUUID", Uuids.CODEC, userUuid);
        }
    }

    public void sync() {
        Skyfall.ofWorld(this.world).sync();
    }
}
