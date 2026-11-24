package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.event.AbstractionEvents;

/**
 * @author Chemthunder
 */
public record TriggerAbstractionPayload() implements CustomPayload {
    public static final Id<TriggerAbstractionPayload> ID = new Id<>(Acropolis.id("trigger_abstraction"));

    public static final PacketCodec<ByteBuf, TriggerAbstractionPayload> CODEC = PacketCodec.unit(new TriggerAbstractionPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<TriggerAbstractionPayload> {
        public void receive(TriggerAbstractionPayload payload, ClientPlayNetworking.Context context) {
            AbstractionEvents.trigger();
        }
    }
}
