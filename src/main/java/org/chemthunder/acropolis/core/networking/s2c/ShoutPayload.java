package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.shout.ShoutEvents;

/**
 * @author Chemthunder
 */
public record ShoutPayload(String text2Shout) implements CustomPayload {
    public static final Id<ShoutPayload> ID = new Id<>(Acropolis.id("shout"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<ByteBuf, ShoutPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, ShoutPayload::text2Shout,
            ShoutPayload::new
    );

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ShoutPayload> {
        public void receive(ShoutPayload payload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> ShoutEvents.send(payload.text2Shout));
        }
    }
}
