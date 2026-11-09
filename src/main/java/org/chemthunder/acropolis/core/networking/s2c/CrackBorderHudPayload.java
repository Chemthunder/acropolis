package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.event.client.BorderEvents;

/**
 * @author Chemthunder
 */
public record CrackBorderHudPayload(boolean bl) implements CustomPayload {
    public static final Id<CrackBorderHudPayload> ID = new Id<>(Acropolis.id("crack_border_hud"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<ByteBuf, CrackBorderHudPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, CrackBorderHudPayload::bl,
            CrackBorderHudPayload::new
    );

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<CrackBorderHudPayload> {
        public void receive(CrackBorderHudPayload payload, ClientPlayNetworking.Context context) {
            if (payload.bl) {
                BorderEvents.crackSize = 0.7F;
            }
        }
    }
}
