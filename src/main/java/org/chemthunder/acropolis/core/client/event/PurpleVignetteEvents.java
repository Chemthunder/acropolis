package org.chemthunder.acropolis.core.client.event;

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
public class PurpleVignetteEvents {
    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Acropolis.id("purple_vignette"), new Hud());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public static float opacity = 0.0F;

        public void onStartTick(MinecraftClient client) {
            if (!client.isPaused()) {
                opacity = Math.clamp(opacity, 0.0F, 1.0F);

                if (opacity > 0.0F) {
                    opacity -= 0.1F;
                }
            }
        }
    }

    public static class Hud implements HudElement {
        public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
            float o = Tick.opacity;

            if (o > 0.0F) {
                drawContext.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Acropolis.id("purple"),
                        0,
                        0,
                        drawContext.getScaledWindowWidth(),
                        drawContext.getScaledWindowHeight(),
                        ColorHelper.withAlpha(o, 0xFFFFFF)
                );
            }
        }
    }
}
