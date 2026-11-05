package org.chemthunder.acropolis.core.client.skyfall;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.entity.AvatarComponent;
import org.chemthunder.acropolis.core.skyfall.PowerType;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PowerHudEvents {
    public static void init() {
        HudElementRegistry.addFirst(Acropolis.id("power_hud"), new Hud());
    }

    public static class Hud implements HudElement {
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            MinecraftClient client = MinecraftClient.getInstance();

            PlayerEntity player = client.player;
            if (player == null) return;

            AvatarComponent avatar = player.getComponent(AvatarComponent.KEY);

            PowerType type = avatar.getCurrent();

            int heightSubtractor = 60;

            context.drawGuiTexture(
                    RenderPipelines.GUI_TEXTURED,
                    Acropolis.id("hud/power/frame"),
                    context.getScaledWindowWidth() / 2 - 9,
                    context.getScaledWindowHeight() - heightSubtractor,
                    18,
                    18
            );

            context.drawGuiTexture(
                    RenderPipelines.GUI_TEXTURED,
                    Acropolis.id("hud/power/icon/" + type.getName()),
                    context.getScaledWindowWidth() / 2 - 8,
                    context.getScaledWindowHeight() - (heightSubtractor - 1),
                    16,
                    16
            );

            if (!avatar.getPowers().isEmpty()) {
                if (avatar.getPowerIndex() < avatar.getPowers().size() - 1) {
                    String name = avatar.getPowers().get(avatar.getPowerIndex() + 1).getName();

                    context.drawCenteredTextWithShadow(
                            client.textRenderer,
                            Text.literal(name),
                            (context.getScaledWindowWidth() / 2 - 8) + (client.textRenderer.getWidth(name)),
                            context.getScaledWindowHeight() - (heightSubtractor - 8),
                            0xFFffffff
                    );
                }

                if (avatar.getPowerIndex() > 0) {
                    String name = avatar.getPowers().get(avatar.getPowerIndex() - 1).getName();

                    context.drawCenteredTextWithShadow(
                            client.textRenderer,
                            Text.literal(name),
                            (context.getScaledWindowWidth() / 2 - 8) - (client.textRenderer.getWidth(name)),
                            context.getScaledWindowHeight() - (heightSubtractor - 8),
                            0xFFffffff
                    );
                }
            }
        }
    }
}
