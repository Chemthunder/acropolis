package org.chemthunder.acropolis.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipComponent;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipData;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class OmenTooltipEvent implements TooltipComponentCallback {
    @Nullable
    public TooltipComponent getComponent(TooltipData data) {
        if (data instanceof OmenTooltipData(ItemStack self)) {
            return new OmenTooltipComponent(self);
        }
        return null;
    }
}
