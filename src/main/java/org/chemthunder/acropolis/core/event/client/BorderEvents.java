package org.chemthunder.acropolis.core.event.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisConfig;
import org.chemthunder.acropolis.core.cca.entity.BorderComponent;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.joml.Matrix3x2fStack;

import java.util.Random;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class BorderEvents {
    public static float opacity = 0.0F;

    public static float lerpX = 1.0F;
    public static float lerpY = -1.0F;

    public static boolean xLerpState = true;
    public static boolean yLerpState = false;

    public static float speed = 0.025F;

    public static float crackSize = 0.0F;

    public static void init() {
        HudElementRegistry.addFirst(Acropolis.id("border_events"), new Hud());

        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        ClientTickEvents.START_CLIENT_TICK.register(new LerpTick());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            PlayerEntity player = client.player;
            if (player == null) return;

            World world = client.world;
            if (world == null) return;

            BorderComponent comp = player.getComponent(BorderComponent.KEY);
            GazeComponent gaze = world.getComponent(GazeComponent.KEY);

            opacity = MathHelper.clamp(opacity, 0.0F, 0.95F);
            crackSize = MathHelper.clamp(crackSize, 0.0F, 0.95F);

            if (gaze.getPos() != null && gaze.isBorder()) {
                double dist = player.getEntityPos().distanceTo(gaze.getPos());

                if (dist < (85 + 80)) {
                    if (opacity < 1.0F) {
                        opacity += 0.025F;
                    }
                } else {
                    if (!comp.inCombat()) {
                        if (opacity > 0.0F) {
                            opacity -= 0.025F;
                        }
                    }
                }
            }

            if (!gaze.isBorder()) {
                if (opacity > 0.0F) {
                    opacity -= 0.025F;
                }
            }

            if (!comp.inCombat()) {
                if (crackSize > 0.0F) {
                    crackSize -= 0.025F;
                }
            } else {
                if (crackSize < 1.0F) {
                    crackSize += 0.025F;
                }
            }
        }
    }

    public static class LerpTick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            PlayerEntity player = client.player;
            if (player == null) return;

            if (xLerpState) {
                if (lerpX > -1.0F) {
                    lerpX -= (speed * 2);
                    if (lerpX <= -1.0F) {
                        xLerpState = false;
                    }
                }
            } else {
                if (lerpX < 1.0F) {
                    lerpX += (speed * 2);
                    if (lerpX >= 1.0F) {
                        xLerpState = true;
                    }
                }
            }

            if (yLerpState) {
                if (lerpY > -1.0F) {
                    lerpY -= speed;
                    if (lerpY <= -1.0F) {
                        yLerpState = false;
                    }
                }
            } else {
                if (lerpY < 1.0F) {
                    lerpY += speed;
                    if (lerpY >= 1.0F) {
                        yLerpState = true;
                    }
                }
            }
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public static class Hud implements HudElement {
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            MinecraftClient client = MinecraftClient.getInstance();

            PlayerEntity player = client.player;
            if (player == null) return;

            BorderComponent comp = player.getComponent(BorderComponent.KEY);

            if (opacity > 0.0F) {
                Matrix3x2fStack matrix = context.getMatrices();

                boolean bl = comp.inCombat();
                int crackLevel = comp.getFightState();

                final float multiplier = 2;

                float xS = (15F / 2F) + (EasingType.IN_OUT_SINE.apply(lerpX) * (multiplier));
                float yS = 40 + (EasingType.IN_OUT_SINE.apply(lerpY) * (multiplier * 2));

                if (crackSize > 0.0F) {
                    if (!AcropolisConfig.epilepsyMode && crackLevel > 3) {
                        matrix.pushMatrix();

                        final int textBound = 40;

                        final String[] texts = new String[] {
                                "YOU ARE PATHETIC",
                                "ONE SIMPLE RULE",
                                "TAKE IT OUTSIDE",
                                "LISTEN WHEN SPOKEN TO",
                                "YOU DON'T NEED TO DO THIS",
                                "BEGONE",
                                "CRY",
                                "RUN",
                                ">:3"
                        };

                        for (int i = 0; i < 5; i++) {
                            Random rand = new java.util.Random();

                            String text = texts[rand.nextInt(0, texts.length)];

                            context.drawText(
                                    client.textRenderer,
                                    Text.literal(text),
                                    ((context.getScaledWindowWidth() / 2) - (client.textRenderer.getWidth(text) / 2)) + rand.nextInt(-textBound, textBound),
                                    ((context.getScaledWindowHeight() / 2) - 30) + rand.nextInt(-textBound, textBound),
                                    ColorHelper.withAlpha(crackSize * 0.77F, 0xFFff0000),
                                    false
                            );
                        }

                        matrix.popMatrix();
                    }

                    matrix.pushMatrix();

                    Random rand = new Random();
                    boolean shake = !AcropolisConfig.motionSicknessMode;

                    matrix.translate(
                            (context.getScaledWindowWidth() / 2F) - (48F / 2F) + (shake ? (rand.nextInt(-1, 1)) : 0),
                            ((context.getScaledWindowHeight() / 2F) - 60F) + (shake ? (rand.nextInt(-1, 1)) : 0)
                    );

                    matrix.scaleAround(
                            0.2F + EasingType.OUT_EXPO.apply(crackSize),
                            48F / 2F,
                            30F
                    );

                    context.drawGuiTexture(
                            RenderPipelines.GUI_TEXTURED,
                            Acropolis.id("hud/border/crack/state_" + comp.getFightState()),
                            0, 0,
                            48,
                            48,
                            ColorHelper.withAlpha(crackSize, 0xFFff0000)
                    );

                    matrix.popMatrix();
                }

                matrix.pushMatrix();

                matrix.translate(
                        (context.getScaledWindowWidth() / 2F) - xS,
                        context.getScaledWindowHeight() / 2F - yS
                );

                matrix.scaleAround(
                        0.5F + (EasingType.OUT_EXPO.apply(opacity)),
                        xS,
                        10 + (EasingType.IN_OUT_SINE.apply(lerpY) * multiplier)
                );

                context.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Acropolis.id(bl ? "hud/border/state_5" : ("hud/border/state_" + comp.getDistState())),
                        0, 0,
                        15,
                        15,
                        ColorHelper.withAlpha(opacity, 0xFFffffff)
                );

                matrix.popMatrix();
            }
        }
    }
}
