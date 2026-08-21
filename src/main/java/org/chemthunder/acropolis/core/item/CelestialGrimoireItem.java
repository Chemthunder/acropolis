package org.chemthunder.acropolis.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.entity.PlayerDataComponent;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.index.AcropolisItems;
import org.chemthunder.acropolis.core.networking.s2c.OpenGrimoirePayload;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class CelestialGrimoireItem extends Item {
    public static final int ANGER = 0;
    public static final int MASK = 1;
    public static final int REMOVE = 2;
    public static final int BORDER = 3;
    public static final int RELOCATE = 4;

    public static final int SANCTUMKEEPER = 6;

    public CelestialGrimoireItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        ItemStack stack = context.getStack();

        if (player != null && world != null && stack != null) {
            if (player.isSneaking() && player.getOffHandStack().isEmpty()) {
                player.swingHand(player.getActiveHand());

                GazeComponent gaze = GazeComponent.KEY.get(world);

                gaze.setPos(context.getBlockPos().toCenterPos());
                gaze.setPresent(true);

                if (gaze.getPos() != null) {
                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(
                                ParticleTypes.END_ROD,
                                gaze.getPos().x,
                                gaze.getPos().y + 1.5F,
                                gaze.getPos().z,
                                9,
                                0,
                                0,
                                0,
                                0.15F
                        );
                    }
                }

                return ActionResult.FAIL;
            }
        }
        return super.useOnBlock(context);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
            if (user.isSneaking()) {
                if (user instanceof ServerPlayerEntity serverPlayer) {
                    user.swingHand(hand);
                    ServerPlayNetworking.send(serverPlayer, new OpenGrimoirePayload());
                }
            }

        return super.use(world, user, hand);
    }

//    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
//        if (clickType == ClickType.RIGHT) {
//            if (otherStack.isOf(Items.TOTEM_OF_UNDYING)) {
//                otherStack.decrement(1);
//
//                if (player.getEntityWorld().isClient()) {
//                    player.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE);
//                }
//
//                cursorStackReference.set(new ItemStack(AcropolisItems.EXTOLLED_SIMULACRUM));
//                return true;
//            }
//        }
//
//        return false;
//    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFFac61d0);
    }

    public static void applyEditAction(int code, World world, PlayerEntity player) {
        GazeComponent gaze = GazeComponent.KEY.get(world);

        switch (code) {
            case ANGER: {
                gaze.setAngered(!gaze.isAngered());
                break;
            }
            case MASK: {
                gaze.setPresent(!gaze.isPresent());
                break;
            }
            case REMOVE: {
                gaze.setPresent(false);
                gaze.setPos(null);
                gaze.setAngered(false);
                break;
            }
            case BORDER: {
                gaze.setBorder(!gaze.isBorder());
                break;
            }
            case RELOCATE: {
                if (gaze.getPos() != null) {
                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(
                                ParticleTypes.END_ROD,
                                player.getX(),
                                player.getY() + 0.5F,
                                player.getZ(),
                                15,
                                0,
                                0,
                                0,
                                0.2F
                        );

                        player.teleportTo(
                                new TeleportTarget(
                                        serverWorld,
                                        new Vec3d(gaze.getPos().x, gaze.getPos().y + 1, gaze.getPos().z),
                                        player.getVelocity(),
                                        player.getYaw(),
                                        player.getPitch(),
                                        TeleportTarget.NO_OP
                                )
                        );

                        serverWorld.spawnParticles(
                                ParticleTypes.END_ROD,
                                player.getX(),
                                player.getY() + 0.5F,
                                player.getZ(),
                                15,
                                0,
                                0,
                                0,
                                0.2F
                        );
                    }
                }
                break;
            }

            case SANCTUMKEEPER: {
                PlayerDataComponent data = PlayerDataComponent.KEY.get(player);

                data.setSanctum(!data.isSanctum());

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    MinecraftServer server = world.getServer();

                    if (server != null) {
                        if (server.getPlayerManager() != null) {
                            server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, serverPlayer));
                        }
                    }
                }
            }
        }
    }

    public static class Tooltip implements BetterItemTooltipEvent {
        public void getTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipType tooltipType, Consumer<Text> consumer) {
            int color = 0xFF9740aa;

            if (itemStack.isOf(AcropolisItems.CELESTIAL_GRIMOIRE)) {
                if (MinecraftClient.getInstance().isAltPressed()) {
                    consumer.accept(Text.empty()
                            .append(Text.literal("[SNEAK]").formatted(Formatting.YELLOW))
                            .append(Text.literal(" and ").withColor(color))
                            .append(Text.literal("[RIGHT-CLICK]").formatted(Formatting.YELLOW))
                            .append(Text.literal(" to access").withColor(color))
                    );
                    consumer.accept(Text.empty()
                            .append(Text.literal("the Grimoire's pages.").withColor(color))
                    );
                    consumer.accept(
                            Text.literal("Bound to the ")
                                    .append(Text.literal("Sanctumkeeper.")
                                            .formatted(Formatting.OBFUSCATED))
                                    .withColor(color)
                    );
                } else {
                    consumer.accept(Text.empty()
                            .append(Text.literal("A gift from the stars, passed down")).withColor(color)
                    );
                    consumer.accept(Text.literal("from generation to generation.").withColor(color));
                    consumer.accept(Text.literal("Bound to the ").append(Text.literal("Sanctumkeeper.").formatted(Formatting.OBFUSCATED)).withColor(color));
                }
            }
        }
    }
}
