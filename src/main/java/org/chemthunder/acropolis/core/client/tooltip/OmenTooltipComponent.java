package org.chemthunder.acropolis.core.client.tooltip;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StyleSpriteSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.index.AComponentTypes;
import org.chemthunder.acropolis.core.item.component.OmenComponent;
import org.joml.Matrix3x2fStack;

import java.util.Random;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class OmenTooltipComponent implements TooltipComponent {
    private final ItemStack stack;

    public OmenTooltipComponent(ItemStack stack) {
        this.stack = stack;
    }

    public int getHeight(TextRenderer textRenderer) {
        return 48;
    }

    public int getWidth(TextRenderer textRenderer) {
        return (98 + 16);
    }

    public void drawText(DrawContext context, TextRenderer textRenderer, int x, int y) {
        OmenComponent omen = stack.get(AComponentTypes.OMEN);
        Random random = new Random();
        Matrix3x2fStack matrices = context.getMatrices();

        if (omen != null) {
            if (!omen.finalized()) {
                float rate = !omen.containedNames().isEmpty() ? omen.containedNames().size() / 10.5F : 0.3F;
                float rate1 = 0.4F;
                float rate2 = 0.2F;

                matrices.pushMatrix();

                matrices.translate(random.nextFloat(-rate, rate), random.nextFloat(-rate, rate));

                matrices.translate(x + 4, y + getHeight(textRenderer) / 8F);

                matrices.scale(1.5F);

                context.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Acropolis.id("acropolis"),
                        0, 0,
                        32,
                        32
                );

                matrices.popMatrix();

                matrices.pushMatrix();

                matrices.translate(x + 48, y + getHeight(textRenderer) / 6F);
                matrices.translate(random.nextFloat(-rate1, rate1), random.nextFloat(-rate2, rate2));

                matrices.scale(2F);

                context.drawText(
                        textRenderer,
                        Text.literal("DEATHS").styled(style -> style.withFont(new StyleSpriteSource.Font(Identifier.ofVanilla("alt")))),
                        0, 0,
                        ColorHelper.withAlpha(40, 0xFFffffff),
                        false
                );

                matrices.popMatrix();

                matrices.pushMatrix();

                matrices.translate(x + (64 + 16), y + getHeight(textRenderer) / 6F);
                matrices.translate(random.nextFloat(-rate2, rate2), random.nextFloat(-rate2, rate2));

                matrices.scale(2F);

                context.drawCenteredTextWithShadow(
                        textRenderer,
                        Text.literal("EXALT"),
                        0, 0,
                        0xFFffffff
                );

                matrices.popMatrix();
            } else {
                context.drawCenteredTextWithShadow(
                        textRenderer,
                        Text.literal("Fulfill your purpose."),
                        x + (getWidth(textRenderer) / 2),
                        y,
                        0xFFffffff
                );

                context.drawCenteredTextWithShadow(
                        textRenderer,
                        Text.literal("Swear fealty."),
                        x + (getWidth(textRenderer) / 2),
                        y + 16,
                        0xFFffffff
                );

                int spacing = 14;

                matrices.pushMatrix();

                context.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Acropolis.id("acrop_eye"),
                        x + (getWidth(textRenderer) / 2) - 16 - spacing,
                        y + 30,
                        32,
                        32
                );

                matrices.popMatrix();

                matrices.pushMatrix();

                context.drawGuiTexture(
                        RenderPipelines.GUI_TEXTURED,
                        Acropolis.id("acrop_eye"),
                        x + (getWidth(textRenderer) / 2) - 16 + spacing,
                        y + 30,
                        32,
                        32
                );

                matrices.popMatrix();
            }
        }
    }
}
