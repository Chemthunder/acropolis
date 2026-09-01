package org.chemthunder.acropolis.core.client.tooltip;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;

/**
 * @author Chemthunder
 */
public class OmenTooltipComponent implements TooltipComponent {
    private final ItemStack stack;

    public OmenTooltipComponent(ItemStack stack) {
        this.stack = stack;
    }

    public int getHeight(TextRenderer textRenderer) {
        return 32;
    }

    public int getWidth(TextRenderer textRenderer) {
        return 48;
    }

    public void drawItems(TextRenderer textRenderer, int x, int y, int width, int height, DrawContext context) {
        //
    }

    public void drawText(DrawContext context, TextRenderer textRenderer, int x, int y) {
        //
    }
}
