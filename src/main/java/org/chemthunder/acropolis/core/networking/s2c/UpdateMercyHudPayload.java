package org.chemthunder.acropolis.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.event.client.MercyHudEvents;

/**
 * @author Chemthunder
 */
public record UpdateMercyHudPayload() implements CustomPayload {
    public static final Id<UpdateMercyHudPayload> ID = new Id<>(Acropolis.id("update_mercy_hud"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, UpdateMercyHudPayload> CODEC = PacketCodec.unit(new UpdateMercyHudPayload());

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<UpdateMercyHudPayload> {
        public void receive(UpdateMercyHudPayload payload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> {
                MercyHudEvents.Ticker.active = true;
                MercyHudEvents.Ticker.opacity = 0.0F;
                MercyHudEvents.Ticker.scalingDown = false;
            });
        }
    }
}
