package org.chemthunder.acropolis.core.networking.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.event.client.FlashEvents;
import org.chemthunder.acropolis.mixin.access.GameRendererAccessor;

/**
 * @author Chemthunder
 */
public record BeginMercyPayload() implements CustomPayload {
    public static final Id<BeginMercyPayload> ID = new Id<>(Acropolis.id("flash"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, BeginMercyPayload> CODEC = PacketCodec.unit(new BeginMercyPayload());

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<BeginMercyPayload> {
        public void receive(BeginMercyPayload payload, ClientPlayNetworking.Context context) {
            context.client().execute(() -> {
                FlashEvents.Ticker.opacity = 1.0F;

                GameRendererAccessor accessor = (GameRendererAccessor) context.client().gameRenderer;

                accessor.PostEffectId(Acropolis.id("mercy"));
                accessor.EffectActive(true);
            });
        }
    }
}
