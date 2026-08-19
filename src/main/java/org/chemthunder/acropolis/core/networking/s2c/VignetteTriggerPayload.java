package org.chemthunder.acropolis.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.event.client.PurpleVignetteEvents;

/**
 * @author Chemthunder
 */
public record VignetteTriggerPayload(float initial) implements CustomPayload {
    public static final Id<VignetteTriggerPayload> ID = new Id<>(Acropolis.id("vignette_trigger"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, VignetteTriggerPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, VignetteTriggerPayload::initial,
            VignetteTriggerPayload::new
    );

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<VignetteTriggerPayload> {
        public void receive(VignetteTriggerPayload vignetteTriggerPayload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> PurpleVignetteEvents.Ticker.opacity = vignetteTriggerPayload.initial);
        }
    }
}
