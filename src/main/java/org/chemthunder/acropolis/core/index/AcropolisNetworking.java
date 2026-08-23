package org.chemthunder.acropolis.core.index;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.chemthunder.acropolis.core.networking.c2s.GrimoireEditPayload;
import org.chemthunder.acropolis.core.networking.s2c.BeginMercyPayload;
import org.chemthunder.acropolis.core.networking.s2c.MercyHudPayload;
import org.chemthunder.acropolis.core.networking.s2c.OpenGrimoirePayload;
import org.chemthunder.acropolis.core.networking.s2c.VignetteTriggerPayload;

/**
 * @author Chemthunder
 */
public interface AcropolisNetworking {
    static void register() {
        PayloadTypeRegistry.playC2S().register(GrimoireEditPayload.ID, GrimoireEditPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(VignetteTriggerPayload.ID, VignetteTriggerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenGrimoirePayload.ID, OpenGrimoirePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(BeginMercyPayload.ID, BeginMercyPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(MercyHudPayload.ID, MercyHudPayload.CODEC);
    }

    static void c2s() {
        ServerPlayNetworking.registerGlobalReceiver(GrimoireEditPayload.ID, new GrimoireEditPayload.Reciever());
    }

    @Environment(EnvType.CLIENT)
    static void s2c() {
        ClientPlayNetworking.registerGlobalReceiver(VignetteTriggerPayload.ID, new VignetteTriggerPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(OpenGrimoirePayload.ID, new OpenGrimoirePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(BeginMercyPayload.ID, new BeginMercyPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(MercyHudPayload.ID, new MercyHudPayload.Receiver());
    }
}
