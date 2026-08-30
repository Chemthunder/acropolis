package org.chemthunder.acropolis.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.chemthunder.acropolis.core.index.AcropolisItems;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class TamperedGrimoireItem extends Item {
    public TamperedGrimoireItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        return Text.literal("Tampered").withColor(0xFFa71010).formatted(Formatting.OBFUSCATED, Formatting.BOLD).append(Text.literal(" Grimoire")).withColor(0xFFa71010);
    }

    public static class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack stack, TooltipContext tooltipContext, TooltipType tooltipFlag, Consumer<Text> lines) {
            if (stack.isOf(AcropolisItems.TAMPERED_GRIMOIRE)) {
                List<String> list = List.of(
                        "morality.",
                        "impotence.",
                        "greed.",
                        "wrath.",
                        "betrayal.",
                        "idiotic.",
                        "frustrated.",
                        "stupid.",
                        "distrustful.",
                        "volatile."
                );

                lines.accept(Text.literal("Incompetence.").formatted(Formatting.ITALIC).withColor(0xFFa71010));

                for (int i = 0; i < 4; i++) {
                    lines.accept(Text.literal(list.get(new Random().nextInt(list.size()))).withColor(0xFFa71010));
                }
            }
        }
    }
}
