package org.chemthunder.acropolis.core.networking.c2s;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.entity.AvatarComponent;

/**
 * @author Chemthunder
 */
public record UsePowerPayload() implements CustomPayload {
    public static final Id<UsePowerPayload> ID = new Id<>(Acropolis.id("use_power"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<ByteBuf, UsePowerPayload> CODEC = PacketCodec.unit(new UsePowerPayload());

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<UsePowerPayload> {
        public void receive(UsePowerPayload payload, ServerPlayNetworking.Context context) {
            ServerPlayerEntity player = context.player();
            AvatarComponent avatar = player.getComponent(AvatarComponent.KEY);
            avatar.deployCurrent();
        }
    }
}
