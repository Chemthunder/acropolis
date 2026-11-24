package org.chemthunder.acropolis.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.event.ShoutEvents;
import org.chemthunder.acropolis.core.index.AParticleTypes;
import org.chemthunder.acropolis.core.index.ASoundEvents;
import org.chemthunder.acropolis.core.utilities.Skyfall;

import java.util.Random;

/**
 * @author Chemthunder
 */
public record ShockwavePayload(Vec3d pos) implements CustomPayload {
    public static final Id<ShockwavePayload> ID = new Id<>(Acropolis.id("shockwave"));

    public static final PacketCodec<ByteBuf, ShockwavePayload> CODEC = PacketCodec.tuple(
            Vec3d.PACKET_CODEC, ShockwavePayload::pos,
            ShockwavePayload::new
    );

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ShockwavePayload> {
        public void receive(ShockwavePayload payload, ClientPlayNetworking.Context context) {
            ClientWorld world = context.client().world;
            if (world == null) return;

            world.addParticleClient(
                    AParticleTypes.SHOCKWAVE,
                    payload.pos.x,
                    payload.pos.y + 0.5F,
                    payload.pos.z,
                    0,
                    0,
                    0
            );

            ShoutEvents.send(
                    Skyfall.getShockwaveTexts().get(new Random().nextInt(Skyfall.getShockwaveTexts().size())),
                    0xFFffffff
            );

            context.client().getSoundManager().play(
                    new PositionedSoundInstance(
                            ASoundEvents.SHOCKWAVE.id(),
                            SoundCategory.PLAYERS,
                            1,
                            1,
                            SoundInstance.createRandom(),
                            false,
                            0,
                            SoundInstance.AttenuationType.NONE,
                            0.0, 0.0, 0.0,
                            true
                    )
            );
        }
    }
}

