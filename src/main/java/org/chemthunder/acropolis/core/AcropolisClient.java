package org.chemthunder.acropolis.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import org.chemthunder.acropolis.core.event.client.GazeRenderEvent;
import org.chemthunder.acropolis.core.index.AcropolisEntityModelLayers;
import org.chemthunder.acropolis.core.index.AcropolisEntityTypes;

/**
 * @author Chemthunder
 */
public class AcropolisClient implements ClientModInitializer {
    public static int AGE = 0;

    public void onInitializeClient() {
        AcropolisEntityModelLayers.clinit();
        AcropolisEntityTypes.clinit();

        WorldRenderEvents.END_MAIN.register(new GazeRenderEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> AGE++);
    }
}
