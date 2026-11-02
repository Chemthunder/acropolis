package org.chemthunder.acropolis.core.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.chemthunder.acropolis.core.networking.c2s.GrimoireEditPayload;
import org.chemthunder.acropolis.core.networking.s2c.*;

/**
 * @author Chemthunder
 */
public interface AcropolisNetworking {
    static void register() {
        PayloadTypeRegistry.playC2S().register(GrimoireEditPayload.ID, GrimoireEditPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(VignetteTriggerPayload.ID, VignetteTriggerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenGrimoirePayload.ID, OpenGrimoirePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(TriggerMercyShadersPayload.ID, TriggerMercyShadersPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(MercyOpeningTextTriggerPayload.ID, MercyOpeningTextTriggerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(FlashPayload.ID, FlashPayload.CODEC);
    }

    static void c2s() {
        ServerPlayNetworking.registerGlobalReceiver(GrimoireEditPayload.ID, new GrimoireEditPayload.Reciever());
    }

    @Environment(EnvType.CLIENT)
    static void s2c() {
        ClientPlayNetworking.registerGlobalReceiver(VignetteTriggerPayload.ID, new VignetteTriggerPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(OpenGrimoirePayload.ID, new OpenGrimoirePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(TriggerMercyShadersPayload.ID, new TriggerMercyShadersPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(MercyOpeningTextTriggerPayload.ID, new MercyOpeningTextTriggerPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(FlashPayload.ID, new FlashPayload.Receiever());
    }
}
