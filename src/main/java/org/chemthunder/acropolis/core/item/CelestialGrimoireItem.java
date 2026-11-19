package org.chemthunder.acropolis.core.item;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.networking.s2c.OpenGrimoirePayload;
import org.chemthunder.acropolis.core.utilities.Skyfall;

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

    public static final int TRUSTSELF = 6;

    public CelestialGrimoireItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        ItemStack stack = context.getStack();

        if (player != null && world != null && stack != null) {
            SkyfallComponent sk = Skyfall.getInstance(world);

            if (!sk.externalVisible) {
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
            } else {
                if (player.isSneaking() && player.getOffHandStack().isOf(this)) {
                    Skyfall.summonSight(world, context.getHitPos().add(0, 0.6, 0));
                }
            }
        }
        return super.useOnBlock(context);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        SkyfallComponent sk = Skyfall.getInstance(world);

        if (!sk.externalVisible) {
            if (user.isSneaking()) {
                if (user instanceof ServerPlayerEntity serverPlayer) {
                    user.swingHand(hand);
                    ServerPlayNetworking.send(serverPlayer, new OpenGrimoirePayload());
                }
            }
        } else {
            // repot
            if (!user.isSneaking()) {
                for (PlayerEntity player : world.getEntitiesByClass(PlayerEntity.class, new Box(user.getBlockPos()).expand(20), (player -> true))) {
                    if (player.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, (8 * 60) * 20));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, (90 * 20), 1));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, (8 * 60) * 20));

                        if (world instanceof ServerWorld serverWorld) {
                            serverWorld.spawnParticles(
                                    ParticleTypes.SOUL,
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    14,
                                    0,
                                    1,
                                    0,
                                    0.1F
                            );
                        }
                    }
                }

                user.getItemCooldownManager().set(user.getStackInHand(hand), (7 * 20));
            } else {
                // heal + hunger
                for (PlayerEntity player : world.getEntitiesByClass(PlayerEntity.class, new Box(user.getBlockPos()).expand(20), (player -> true))) {
                    if (player.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
                        player.heal(player.getMaxHealth());
                        player.getHungerManager().add(40, 120);

                        if (world instanceof ServerWorld serverWorld) {
                            serverWorld.spawnParticles(
                                    ParticleTypes.END_ROD,
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    14,
                                    0,
                                    1,
                                    0,
                                    0.1F
                            );
                        }
                    }
                }

                user.getItemCooldownManager().set(user.getStackInHand(hand), (9 * 20));
            }
        }

        return super.use(world, user, hand);
    }

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

            case TRUSTSELF: {

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

            if (itemStack.isOf(AItems.CELESTIAL_GRIMOIRE)) {
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
