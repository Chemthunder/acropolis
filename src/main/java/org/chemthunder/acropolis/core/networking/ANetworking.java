package org.chemthunder.acropolis.core.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.chemthunder.acropolis.core.client.event.RynMaterializeEvent;
import org.chemthunder.acropolis.core.networking.c2s.DashPayload;
import org.chemthunder.acropolis.core.networking.c2s.GrimoireEditPayload;
import org.chemthunder.acropolis.core.networking.s2c.*;

/**
 * @author Chemthunder
 */
public interface ANetworking {
    static void register() {
        PayloadTypeRegistry.playC2S().register(GrimoireEditPayload.ID, GrimoireEditPayload.CODEC);

        PayloadTypeRegistry.playC2S().register(DashPayload.ID, DashPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(VignetteTriggerPayload.ID, VignetteTriggerPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(OpenGrimoirePayload.ID, OpenGrimoirePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(TriggerMercyShadersPayload.ID, TriggerMercyShadersPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(MercyOpeningTextTriggerPayload.ID, MercyOpeningTextTriggerPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(FlashPayload.ID, FlashPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ShoutPayload.ID, ShoutPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(CrackBorderHudPayload.ID, CrackBorderHudPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(ShockwavePayload.ID, ShockwavePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ResetSightWidthPayload.ID, ResetSightWidthPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(TriggerAbstractionPayload.ID, TriggerAbstractionPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(MaterializePayload.ID, MaterializePayload.CODEC);
    }

    static void c2s() {
        ServerPlayNetworking.registerGlobalReceiver(GrimoireEditPayload.ID, new GrimoireEditPayload.Receiver());

        ServerPlayNetworking.registerGlobalReceiver(DashPayload.ID, new DashPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void s2c() {
        ClientPlayNetworking.registerGlobalReceiver(VignetteTriggerPayload.ID, new VignetteTriggerPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(OpenGrimoirePayload.ID, new OpenGrimoirePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(TriggerMercyShadersPayload.ID, new TriggerMercyShadersPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(MercyOpeningTextTriggerPayload.ID, new MercyOpeningTextTriggerPayload.Receiver());

        ClientPlayNetworking.registerGlobalReceiver(FlashPayload.ID, new FlashPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(ShoutPayload.ID, new ShoutPayload.Receiver());

        ClientPlayNetworking.registerGlobalReceiver(CrackBorderHudPayload.ID, new CrackBorderHudPayload.Receiver());

        ClientPlayNetworking.registerGlobalReceiver(ShockwavePayload.ID, new ShockwavePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(ResetSightWidthPayload.ID, new ResetSightWidthPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(TriggerAbstractionPayload.ID, new TriggerAbstractionPayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(MaterializePayload.ID, new MaterializePayload.Receiver());
    }
}
