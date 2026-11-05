package org.chemthunder.acropolis.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.event.client.FlashEvents;
import org.chemthunder.acropolis.mixin.client.access.GameRendererAccessor;

/**
 * @author Chemthunder
 */
public record TriggerMercyShadersPayload() implements CustomPayload {
    public static final Id<TriggerMercyShadersPayload> ID = new Id<>(Acropolis.id("flash"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, TriggerMercyShadersPayload> CODEC = PacketCodec.unit(new TriggerMercyShadersPayload());

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<TriggerMercyShadersPayload> {
        public void receive(TriggerMercyShadersPayload payload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> {
                FlashEvents.flash(0x000000, EasingType.OUT_SINE);

                GameRendererAccessor accessor = (GameRendererAccessor) context.client().gameRenderer;

                accessor.PostEffectId(Acropolis.id("mercy"));
                accessor.EffectActive(true);
            });
        }
    }
}
