package org.chemthunder.acropolis.core.item;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.client.screen.GrimoireEditScreen;
import org.chemthunder.acropolis.core.networking.s2c.OpenGrimoirePayload;

/**
 * @author Chemthunder
 */
public class CelestialGrimoireItem extends Item {
    public static final int TEST = 0;
    public static final int MASK = 1;
    public static final int REMOVE = 2;
    public static final int BORDER = 3;
    public static final int RELOCATE = 4;

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
                    ServerPlayNetworking.send(serverPlayer, new OpenGrimoirePayload());
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
            case TEST: {
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
                                    gaze.getPos(),
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
                break;
            }
        }
    }
}
