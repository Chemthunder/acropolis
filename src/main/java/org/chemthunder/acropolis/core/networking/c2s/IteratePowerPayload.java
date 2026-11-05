package org.chemthunder.acropolis.core.networking.c2s;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.entity.AvatarComponent;

/**
 * @author Chemthunder
 */
public record IteratePowerPayload(boolean bl) implements CustomPayload {
    public static final Id<IteratePowerPayload> ID = new Id<>(Acropolis.id("iterate_power"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<ByteBuf, IteratePowerPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, IteratePowerPayload::bl,
            IteratePowerPayload::new
    );

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<IteratePowerPayload> {
        public void receive(IteratePowerPayload payload, ServerPlayNetworking.Context context) {
            ServerPlayerEntity player = context.player();
            AvatarComponent avatar = player.getComponent(AvatarComponent.KEY);
            avatar.iterate(payload.bl);
        }
    }
}
