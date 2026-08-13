package org.chemthunder.acropolis.core.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.chemthunder.acropolis.core.networking.c2s.GrimoireEditPayload;

/**
 * @author Chemthunder
 */
public interface AcropolisNetworking {
    static void register() {
        PayloadTypeRegistry.playC2S().register(GrimoireEditPayload.ID, GrimoireEditPayload.CODEC);
    }

    static void c2s() {
        ServerPlayNetworking.registerGlobalReceiver(GrimoireEditPayload.ID, new GrimoireEditPayload.Reciever());
    }
}
