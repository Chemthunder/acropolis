package org.chemthunder.acropolis.core.networking.c2s;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;

/**
 * @author Chemthunder
 */
public record GrimoireEditPayload(int code) implements CustomPayload {
    public static final Id<GrimoireEditPayload> ID = new Id<>(Acropolis.id("grimoire_edit"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<RegistryByteBuf, GrimoireEditPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, GrimoireEditPayload::code,
            GrimoireEditPayload::new
    );

    public static void send(int code) {
        ClientPlayNetworking.send(new GrimoireEditPayload(code));
    }

    public static class Reciever implements ServerPlayNetworking.PlayPayloadHandler<GrimoireEditPayload> {
        public void receive(GrimoireEditPayload grimoireEditPayload, ServerPlayNetworking.Context context) {
            PlayerEntity player = context.player();
            World world = player.getEntityWorld();

            CelestialGrimoireItem.applyEditAction(grimoireEditPayload.code, world, player);

            if (world.isClient()) {
                player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE);
            }
        }
    }
}
