package org.chemthunder.acropolis.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.entity.PestilenceEntity;
import org.chemthunder.acropolis.core.entity.SwarmEntity;
import org.chemthunder.acropolis.core.index.AEntityTypes;
import org.chemthunder.acropolis.core.index.AItems;

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

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        List<PestilenceEntity> gremlins = world.getEntitiesByClass(PestilenceEntity.class, new Box(user.getBlockPos()).expand(world.getWorldBorder().getSize() / 6), entity -> true);

        if (gremlins.isEmpty()) {
            SwarmEntity entity = new SwarmEntity(AEntityTypes.SWARM, world);

            Vec3d pos = user.getBlockPos().toCenterPos();

            entity.setPosition(pos.x, pos.y, pos.z);
            entity.setOwner(user);

            world.spawnEntity(entity);
            user.swingHand(hand);
        } else {
            if (user.isSneaking()) {
                if (world instanceof ServerWorld serverWorld) {
                    for (PestilenceEntity entity : gremlins) {
                        entity.teleportTo(
                                new TeleportTarget(
                                        serverWorld,
                                        new Vec3d(
                                                user.getEntityPos().x,
                                                user.getEntityPos().y + 3,
                                                user.getEntityPos().z
                                        ),
                                        new Vec3d(
                                                0,
                                                0,
                                                0
                                        ),
                                        entity.getYaw(),
                                        entity.getPitch(),
                                        TeleportTarget.NO_OP
                                )
                        );
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        PlayerEntity player = context.getPlayer();

        if (player != null) {
            if (state.isOf(Blocks.AMETHYST_BLOCK)) {
                ItemStack stack = player.getStackInHand(context.getHand());

                stack.decrement(1);
                player.equipStack(context.getHand().getEquipmentSlot(), new ItemStack(AItems.CELESTIAL_GRIMOIRE));
            }
        }
        return super.useOnBlock(context);
    }

    public static class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack stack, TooltipContext tooltipContext, TooltipType tooltipFlag, Consumer<Text> lines) {
            if (stack.isOf(AItems.TAMPERED_GRIMOIRE)) {
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
