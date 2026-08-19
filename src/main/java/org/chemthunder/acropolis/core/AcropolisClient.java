package org.chemthunder.acropolis.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import org.chemthunder.acropolis.core.event.client.PurpleVignetteEvents;
import org.chemthunder.acropolis.core.event.client.entities.GazeRenderEvent;
import org.chemthunder.acropolis.core.event.client.entities.SightRenderEvent;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;
import org.chemthunder.acropolis.core.index.AcropolisEntityModelLayers;
import org.chemthunder.acropolis.core.index.AcropolisEntityTypes;
import org.chemthunder.acropolis.core.index.AcropolisNetworking;

/**
 * @author Chemthunder
 */
public class AcropolisClient implements ClientModInitializer {
    public static int AGE = 0;

    public void onInitializeClient() {
        AcropolisEntityModelLayers.clinit();
        AcropolisEntityTypes.clinit();
        AcropolisBlocks.clinit();

        AcropolisNetworking.s2c();

        WorldRenderEvents.END_MAIN.register(new GazeRenderEvent());
        WorldRenderEvents.END_MAIN.register(new SightRenderEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> AGE++);
        ClientTickEvents.START_CLIENT_TICK.register(new PurpleVignetteEvents.Ticker());

        HudElementRegistry.addFirst(Acropolis.id("vignette"), new PurpleVignetteEvents.Render());
    }
}
