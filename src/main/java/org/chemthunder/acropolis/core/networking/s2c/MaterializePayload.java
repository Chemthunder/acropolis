package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.event.RynMaterializeEvent;

/**
 * @author Chemthunder
 */
public record MaterializePayload() implements CustomPayload {
    public static final Id<MaterializePayload> ID = new Id<>(Acropolis.id("materialize"));

    public static final PacketCodec<ByteBuf, MaterializePayload> CODEC = PacketCodec.unit(new MaterializePayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<MaterializePayload> {
        public void receive(MaterializePayload payload, ClientPlayNetworking.Context context) {
            RynMaterializeEvent.lerp = 1.0F;
        }
    }
}
