package org.chemthunder.acropolis.core.client.skyfall;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class ShoutEvents {
    public static String shoutedtext = "";

    public static float scale = 0.0F;
    public static float opacity = 0.0F;

    public static int fadeOut = 20;

    public static int color = 0xFFffffff;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Acropolis.id("shout"), new Hud());
    }

    public static void send(String input) {
        shoutedtext = input;
        scale = 0.0F;
        opacity = 1.0F;
        fadeOut = 20;
    }

    public static void send(String input, int i) {
        shoutedtext = input;
        scale = 0.0F;
        opacity = 1.0F;
        fadeOut = 20;
        color = i;
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            scale = Math.clamp(scale, 0.0F, 1.0F);
            opacity = Math.clamp(opacity, 0.0F, 1.0F);

            if (!client.isPaused()) {
                if (scale < 1.0F) {
                    scale += 0.01F;
                }

                if (opacity > 0.0F) {
                    opacity -= 0.01F;
                }

                if (opacity <= 0.0F) {
                    if (fadeOut > 0) {
                        fadeOut--;
                        if (fadeOut == 0) {
                            shoutedtext = "";
                        }
                    }
                }
            }
        }
    }

    public static class Hud implements HudElement {
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            MinecraftClient client = MinecraftClient.getInstance();

            if (opacity > 0.0F && !shoutedtext.isBlank()) {
                TextRenderer renderer = client.textRenderer;

                context.getMatrices().pushMatrix();

                context.getMatrices().translate(
                        (context.getScaledWindowWidth() / 2F) - (renderer.getWidth(shoutedtext) / 2F),
                        context.getScaledWindowHeight() / 2F - 30
                );

                context.getMatrices().scaleAround(
                        1 + EasingType.OUT_CUBIC.apply(scale),
                        renderer.getWidth(shoutedtext) / 2F,
                        4
                );

                context.drawText(
                        renderer,
                        Text.literal(shoutedtext),
                        0, 0,
                        ColorHelper.withAlpha(EasingType.OUT_QUAD.apply(opacity) / 2, color),
                        false
                );

                context.getMatrices().popMatrix();

                context.getMatrices().pushMatrix();

                context.getMatrices().translate(
                        (context.getScaledWindowWidth() / 2F) - (renderer.getWidth(shoutedtext) / 2F),
                        context.getScaledWindowHeight() / 2F - 30
                );

                context.getMatrices().scaleAround(
                        1 + (EasingType.OUT_EXPO.apply(scale) / 4),
                        renderer.getWidth(shoutedtext) / 2F,
                        5
                );

                context.drawText(
                        renderer,
                        Text.literal(shoutedtext),
                        0, 0,
                        ColorHelper.withAlpha(EasingType.OUT_QUAD.apply(opacity), color),
                        true
                );

                context.getMatrices().popMatrix();
            }
        }
    }
}