package org.chemthunder.acropolis.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class RynMaterializeEvent implements ClientTickEvents.StartTick {
    public static float lerp = 0.0F;

    public void onStartTick(MinecraftClient client) {
        if (lerp > 0.0F) {
            lerp -= 0.05F;
        }
    }
}
