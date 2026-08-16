package org.chemthunder.acropolis.core.index;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.chemthunder.acropolis.core.networking.c2s.GrimoireEditPayload;
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
    }

    static void c2s() {
        ServerPlayNetworking.registerGlobalReceiver(GrimoireEditPayload.ID, new GrimoireEditPayload.Reciever());
    }

    @Environment(EnvType.CLIENT)
    static void s2c() {
        ClientPlayNetworking.registerGlobalReceiver(VignetteTriggerPayload.ID, new VignetteTriggerPayload.Reciever());
        ClientPlayNetworking.registerGlobalReceiver(OpenGrimoirePayload.ID, new OpenGrimoirePayload.Reciever());
    }
}
