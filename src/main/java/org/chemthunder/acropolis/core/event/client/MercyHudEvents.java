package org.chemthunder.acropolis.core.event.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix3x2fStack;

/**
 * @author Chemthunder
 */
public class MercyHudEvents {

    public static class Ticker implements ClientTickEvents.StartTick {
        public static boolean active = false;
        public static boolean scalingDown = false;

        public static int ticksPost = 200;

        public static float opacity = 0.0F;

        public void onStartTick(MinecraftClient client) {
            if (active) {
                if (!scalingDown) {
                    if (opacity < 0.9F) {
                        opacity += 0.1f;
                    }

                    if (opacity >= 0.9F) {
                        if (ticksPost > 0) {
                            ticksPost--;
                        }
                    }

                    if (ticksPost <= 0) {
                        scalingDown = true;
                    }
                } else {
                    if (opacity > 0.0F) {
                        opacity -= 0.01F;
                    } else {
                        active = false;
                        ticksPost = 40;
                        opacity = 0.0F;
                    }
                }
            } else {
                ticksPost = 40;
                opacity = 0.0F;
            }
        }
    }

    public static class Render implements HudElement {
        public void render(DrawContext context, RenderTickCounter renderTickCounter) {
            float delta = renderTickCounter.getDynamicDeltaTicks();
            float yPosSub = 40;

            if (Ticker.opacity > 0.0F) {
                Matrix3x2fStack stack = context.getMatrices();

                stack.pushMatrix();

                stack.translate(context.getScaledWindowWidth() / 2F, context.getScaledWindowHeight() / 2F - yPosSub - 80);
                stack.scale(2.6F);

                context.drawCenteredTextWithShadow(
                        MinecraftClient.getInstance().textRenderer,
                        Text.literal("ACROPOLIS // MEDIAN"),
                        0, 0,
                        ColorHelper.withAlpha(Ticker.opacity, 0xFFffffff)
                );

                stack.popMatrix();

                stack.pushMatrix();

                stack.translate(context.getScaledWindowWidth() / 2F, context.getScaledWindowHeight() / 2F - yPosSub - 30);
                stack.scale(4);

                context.drawCenteredTextWithShadow(
                        MinecraftClient.getInstance().textRenderer,
                        Text.literal("THEN FELL THE HEAVENS"),
                        0, 0,
                        ColorHelper.withAlpha(Ticker.opacity, 0xFFffffff)
                );

                stack.popMatrix();
            }
        }
    }
}
