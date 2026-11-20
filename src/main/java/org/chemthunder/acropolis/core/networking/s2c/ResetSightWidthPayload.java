package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.skyfall.SightEvents;

/**
 * @author Chemthunder
 */
public record ResetSightWidthPayload() implements CustomPayload {
    public static final Id<ResetSightWidthPayload> ID = new Id<>(Acropolis.id("reset_sight_width"));

    public static final PacketCodec<ByteBuf, ResetSightWidthPayload> CODEC = PacketCodec.unit(new ResetSightWidthPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ResetSightWidthPayload> {
        public void receive(ResetSightWidthPayload payload, ClientPlayNetworking.Context context) {
            SightEvents.width = 0;
        }
    }
}
