package org.chemthunder.acropolis.core.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.core.networking.c2s.GrimoireEditPayload;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Chemthunder
 */
public class GrimoireEditScreen extends Screen {
    private final List<Function<DrawContext, Drawable>> drawableFunctions = new ArrayList<>();

    public GrimoireEditScreen() {
        super(Text.empty());
    }

    protected void init() {
        drawableFunctions.clear();

        PlayerEntity player = MinecraftClient.getInstance().player;

        Screen s = client.currentScreen;

        ButtonWidget ANGER = ButtonWidget.builder(Text.literal("Anger Eye"), (button -> {
                    this.close();

                    GrimoireEditPayload.send(CelestialGrimoireItem.TEST);

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
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawCenteredTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                Text.literal("Celestial Grimoire"),
                context.getScaledWindowWidth() / 2,
                context.getScaledWindowHeight() / 2 - 40,
                0xFFff004f
        );

        drawableFunctions.forEach(function ->
                function.apply(context).render(context, mouseX, mouseY, deltaTicks)
        );
    }
}
