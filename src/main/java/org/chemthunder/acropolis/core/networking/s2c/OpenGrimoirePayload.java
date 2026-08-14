package org.chemthunder.acropolis.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.screen.GrimoireEditScreen;
import org.chemthunder.acropolis.core.event.client.PurpleVignetteEvents;

/**
 * @author Chemthunder
 */
public record OpenGrimoirePayload() implements CustomPayload {
    public static final Id<OpenGrimoirePayload> ID = new Id<>(Acropolis.id("grimoire_open"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, OpenGrimoirePayload> CODEC = PacketCodec.unit(new OpenGrimoirePayload());

    public static class Reciever implements ClientPlayNetworking.PlayPayloadHandler<OpenGrimoirePayload> {
        public void receive(OpenGrimoirePayload openGrimoirePayload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> context.client().setScreen(new GrimoireEditScreen()));
        }
    }
}
