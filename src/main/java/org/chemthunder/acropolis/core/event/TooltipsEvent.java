package org.chemthunder.acropolis.core.event;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class TooltipsEvent implements BetterItemTooltipEvent {
    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipFlag, Consumer<Text> lines) {

    }
}
