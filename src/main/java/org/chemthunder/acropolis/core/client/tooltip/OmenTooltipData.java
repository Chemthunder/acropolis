package org.chemthunder.acropolis.core.client.tooltip;

import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;

/**
 * @author Chemthunder
 */
public record OmenTooltipData(ItemStack self) implements TooltipData {
}
