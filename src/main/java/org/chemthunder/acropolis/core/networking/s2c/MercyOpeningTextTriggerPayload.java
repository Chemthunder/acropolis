package org.chemthunder.acropolis.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.event.MercyHudEvents;

/**
 * @author Chemthunder
 */
public record MercyOpeningTextTriggerPayload() implements CustomPayload {
    public static final Id<MercyOpeningTextTriggerPayload> ID = new Id<>(Acropolis.id("update_mercy_hud"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, MercyOpeningTextTriggerPayload> CODEC = PacketCodec.unit(new MercyOpeningTextTriggerPayload());

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<MercyOpeningTextTriggerPayload> {
        public void receive(MercyOpeningTextTriggerPayload payload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> {
                MercyHudEvents.Tick.active = true;
                MercyHudEvents.Tick.opacity = 0.0F;
                MercyHudEvents.Tick.scalingDown = false;
            });
        }
    }
}
