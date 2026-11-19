package org.chemthunder.acropolis.core.networking.c2s;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.utilities.Skyfall;

/**
 * @author Chemthunder
 */
public record DashPayload(boolean buffer) implements CustomPayload {
    public static final Id<DashPayload> ID = new Id<>(Acropolis.id("dash"));

    public static final PacketCodec<ByteBuf, DashPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, DashPayload::buffer,
            DashPayload::new
    );

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<DashPayload> {
        public void receive(DashPayload payload, ServerPlayNetworking.Context context) {
            if (payload.buffer) {
                Skyfall.dash(context.player());
            }
        }
    }
}
