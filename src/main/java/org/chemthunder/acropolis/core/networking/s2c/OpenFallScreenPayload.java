package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.screen.FallFlashScreen;

/**
 * @author Chemthunder
 */
public record OpenFallScreenPayload() implements CustomPayload {
    public static final Id<OpenFallScreenPayload> ID = new Id<>(Acropolis.id("open_fall_scr"));

    public static final PacketCodec<ByteBuf, OpenFallScreenPayload> CODEC = PacketCodec.unit(new OpenFallScreenPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<OpenFallScreenPayload> {
        public void receive(OpenFallScreenPayload payload, ClientPlayNetworking.Context context) {
            context.client().setScreen(new FallFlashScreen());
        }
    }
}
