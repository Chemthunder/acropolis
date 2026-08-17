package org.chemthunder.acropolis.core.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.SightComponent;
import org.chemthunder.acropolis.core.index.AcropolisComponentTypes;

/**
 * @author Chemthunder
 */
public class ExtolledSimulacrumItem extends Item {
    public ExtolledSimulacrumItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        SightComponent sight = SightComponent.KEY.get(world);
        ItemStack stack = user.getStackInHand(hand);

        if (sight.getPos() == null && stack.getOrDefault(AcropolisComponentTypes.CHARGES, 0) > 0) {
            HitResult raycast = user.raycast(250, 0.1F, false);
            Vec3d tail = raycast.getPos();

            sight.setTicks(0);
            sight.setBeamWidth(0.0F);
            sight.setPos(tail);
            sight.setScale(0.0F);

            stack.set(AcropolisComponentTypes.CHARGES, stack.getOrDefault(AcropolisComponentTypes.CHARGES, 0) - 1);
        }
        return super.use(world, user, hand);
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFFac61d0);
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.clamp(Math.round((float) stack.getOrDefault(AcropolisComponentTypes.CHARGES, 3) / 3 * 13), 0, 13);
    }

    public int getItemBarColor(ItemStack stack) {
        return 0xFFac61d0;
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

//    public static class Tooltip implements BetterItemTooltipEvent {
//        public void getTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent component, @Nullable PlayerEntity player, TooltipType type, Consumer<Text> consumer) {
//            int color = 0xFF9740aa;
//
//            if (stack.isOf(AcropolisItems.EXTOLLED_SIMULACRUM)) {
//                if (MinecraftClient.getInstance().isAltPressed()) {
//                    consumer.accept(Text.empty()
//                            .append(Text.literal("[RIGHT-CLICK]").formatted(Formatting.YELLOW))
//                            .append(Text.literal(" in any direction to call down").withColor(color))
//                    );
//                    consumer.accept(Text.empty()
//                            .append(Text.literal("the essence of the Sight.").withColor(color))
//                    );
//                } else {
//                    consumer.accept(Text.empty()
//                            .append(Text.literal("The calling-bell of the Sight, a")).withColor(color)
//                    );
//                    consumer.accept(Text.literal("ferocious beast of the cosmos.").withColor(color));
//                }
//            }
//        }
//    }
}
