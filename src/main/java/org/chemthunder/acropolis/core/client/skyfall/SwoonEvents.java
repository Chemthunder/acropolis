package org.chemthunder.acropolis.core.client.skyfall;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.math.ColorHelper;
import org.chemthunder.acropolis.core.Acropolis;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class SwoonEvents {
    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Acropolis.id("swoon"), new Hud());
    }

    public static float opacity = 0.0F;

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            if (!client.isPaused()) {
                if (opacity > 0.0F) {
                    opacity -= 0.1F;
                }
            }
        }
    }

    public static class Hud implements HudElement {
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            if (opacity > 0.0F) {
                context.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Acropolis.id("hud/swoon"),
                        0,
                        0,
                        context.getScaledWindowWidth(),
                        context.getScaledWindowHeight(),
                        ColorHelper.withAlpha(opacity, 0xFFffffff)
                );
            }
        }
    }
}
