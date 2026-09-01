package org.chemthunder.acropolis.core.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipData;

import java.util.Optional;

/**
 * @author Chemthunder
 */
public class FatalOmenItem extends Item {
    public FatalOmenItem(Settings settings) {
        super(settings);
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        TooltipDisplayComponent display = stack.getOrDefault(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);

        return !display.shouldDisplay(ModDataComponentTypes.TABLET)
                ? Optional.empty()
                : Optional.of(tablet).map(component -> new OmenTooltipData(stack)
        );
    }
}
