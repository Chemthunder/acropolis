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
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class FlashEvents {
    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Acropolis.id("customizable_flash"), new Hud());
    }

    public static void flash(int color, EasingType type) {
        Tick.color = color;
        Tick.opacity = 1.0F;
        Tick.type = type;
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public static float opacity = 0.0F;
        public static int color = 0x000000;
        public static EasingType type = EasingType.OUT_SINE;

        public void onStartTick(MinecraftClient client) {
            if (!client.isPaused()) {
                opacity = Math.clamp(opacity, 0.0F, 1.0F);

                if (opacity > 0.0F) {
                    opacity -= 0.01F;
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
                        Acropolis.id("flash"),
                        0,
                        0,
                        drawContext.getScaledWindowWidth(),
                        drawContext.getScaledWindowHeight(),
                        ColorHelper.withAlpha(Tick.type.apply(o), Tick.color)
                );
            }
        }
    }
}
