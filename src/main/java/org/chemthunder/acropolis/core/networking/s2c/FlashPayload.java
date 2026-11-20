package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.event.FlashEvents;
import org.chemthunder.acropolis.core.utilities.ACodecs;

/**
 * @author Chemthunder
 */
public record FlashPayload(int color, EasingType easing) implements CustomPayload {
    public static final Id<FlashPayload> ID = new Id<>(Acropolis.id("customizable_flash"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<ByteBuf, FlashPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, FlashPayload::color,
            ACodecs.EASING_TYPE_PACKET_CODEC, FlashPayload::easing,
            FlashPayload::new
    );

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<FlashPayload> {
        public void receive(FlashPayload payload, ClientPlayNetworking.Context context) {
            FlashEvents.flash(payload.color, payload.easing);
        }
    }
}
