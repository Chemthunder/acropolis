package org.chemthunder.acropolis.core.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Easing;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.core.networking.c2s.GrimoireEditPayload;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class GrimoireEditScreen extends Screen {
    private final List<Function<DrawContext, Drawable>> drawableFunctions = new ArrayList<>();
    private float width = 0.0F;

    public GrimoireEditScreen() {
        super(Text.empty());
    }

    protected void init() {
        drawableFunctions.clear();

        PlayerEntity player = MinecraftClient.getInstance().player;
        Screen s = client.currentScreen;

        if (player != null && s != null) {
            ButtonWidget ANGER = ButtonWidget.builder(Text.literal("Anger Eye"), (button -> {
                        this.close();

                        GrimoireEditPayload.send(CelestialGrimoireItem.ANGER);

                        player.swingHand(player.getActiveHand());
                    }))
                    .size(64, 16)
                    .position(s.width / 2 - 32, s.height / 2)
                    .tooltip(Tooltip.of(Text.literal("Toggles the Eye's Anger state.")))
                    .build();

            drawableFunctions.add(context -> ANGER);
            this.addDrawableChild(ANGER);

            ButtonWidget PRESENT = ButtonWidget.builder(Text.literal("Mask Eye"), (button -> {
                        this.close();

                        GrimoireEditPayload.send(CelestialGrimoireItem.MASK);

                        player.swingHand(player.getActiveHand());
                    }))
                    .size(64, 16)
                    .position(s.width / 2 - 32, s.height / 2 + 32)
                    .tooltip(Tooltip.of(Text.literal("Toggles the Eye's Masked State.")))
                    .build();

            drawableFunctions.add(context -> PRESENT);
            this.addDrawableChild(PRESENT);

            ButtonWidget REMOVE = ButtonWidget.builder(Text.literal("Remove Eye"), (button -> {
                        this.close();

                        GrimoireEditPayload.send(CelestialGrimoireItem.REMOVE);

                        player.swingHand(player.getActiveHand());
                    }))
                    .size(64, 16)
                    .position(s.width / 2 - 32, s.height / 2 + 64)
                    .tooltip(Tooltip.of(Text.literal("Removes the Eye.")))
                    .build();

            drawableFunctions.add(context -> REMOVE);
            this.addDrawableChild(REMOVE);

            ButtonWidget BORDER = ButtonWidget.builder(Text.literal("Border Eye"), (button -> {
                        this.close();

                        GrimoireEditPayload.send(CelestialGrimoireItem.BORDER);

                        player.swingHand(player.getActiveHand());
                    }))
                    .size(64, 16)
                    .position(s.width / 2 - 32, s.height / 2 + (64 + 32))
                    .tooltip(Tooltip.of(Text.literal("Toggles the Border created by the Eye.")))
                    .build();

            drawableFunctions.add(context -> BORDER);
            this.addDrawableChild(BORDER);

//            ButtonWidget TRUST = ButtonWidget.builder(Text.literal("Trust Self"), (button -> {
//                        this.close();
//
//                        GrimoireEditPayload.send(CelestialGrimoireItem.TRUSTSELF);
//
//                        player.swingHand(player.getActiveHand());
//                    }))
//                    .size(64, 16)
//                    .position(s.width / 2 - 32, s.height / 2 + (64 + 32))
//                    .tooltip(Tooltip.of(Text.literal("check yoself cuh")))
//                    .build();
//
//            drawableFunctions.add(context -> TRUST);
//            this.addDrawableChild(TRUST);
        }
    }

    public void tick() {
        if (width < 1.0F) {
            width += 0.5F;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        int time = (int) (Easing.outExpo(width) * 50);

        for (int i = 0; i < 30; i++) {
            context.getMatrices().pushMatrix();

            context.getMatrices().translate((float) context.getScaledWindowWidth() / 2, i * 15);
            context.getMatrices().scale(1.4F);

            context.drawCenteredTextWithShadow(
                    MinecraftClient.getInstance().textRenderer,
                    Text.literal("Celestial Grimoire").formatted(Formatting.DARK_GRAY, Formatting.OBFUSCATED),
                    0, 0,
                    0xFFFFFFFF
            );
            context.getMatrices().popMatrix();
        }

        context.fill(
                RenderPipelines.GUI,
                context.getScaledWindowWidth() / 2 - time,
                0,
                context.getScaledWindowWidth() / 2 + time,
                context.getScaledWindowHeight(),
                ColorHelper.withAlpha(0.5F, 0x000000)
        );

        context.drawCenteredTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                Text.literal("Celestial Grimoire"),
                context.getScaledWindowWidth() / 2,
                context.getScaledWindowHeight() / 2 - 60,
                0xFFac61d0
        );

        context.drawTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                Text.literal("Gazer Position -"),
                40,
                40,
                0xFFac61d0
        );

        if (MinecraftClient.getInstance().world != null) {
            GazeComponent gaze = GazeComponent.KEY.get(MinecraftClient.getInstance().world);

            if (gaze.getPos() != null) {
                context.drawTextWithShadow(
                        MinecraftClient.getInstance().textRenderer,
                        Text.literal(gaze.getPos().toString()),
                        40,
                        60,
                        0xFFac61d0
                );
            } else {
                context.drawTextWithShadow(
                        MinecraftClient.getInstance().textRenderer,
                        Text.literal("NULL"),
                        40,
                        55,
                        0xFFac61d0
                );
            }
        }

        drawableFunctions.forEach(function ->
                function.apply(context).render(context, mouseX, mouseY, deltaTicks)
        );
    }
}
