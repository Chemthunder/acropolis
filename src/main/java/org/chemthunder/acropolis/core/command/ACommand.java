package org.chemthunder.acropolis.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.client.screen.FallFlashScreen;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AComponentTypes;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.item.component.OmenComponent;
import org.chemthunder.acropolis.core.networking.s2c.ResetSightWidthPayload;
import org.chemthunder.acropolis.core.utilities.Skyfall;

import java.util.Collections;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Chemthunder
 */
public class ACommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("skyfall").requires(ACommand::isChem)
                .then(literal("trigger")
                        .executes(context -> {
                            SkyfallComponent sk = Skyfall.getInstance(context.getSource());
                            sk.activate();
                            return 1;
                        })
                )
                .then(literal("close")
                        .executes(context -> {
                            SkyfallComponent sk = Skyfall.getInstance(context.getSource());
                            sk.close();
                            return 1;
                        })
                )

                .then(literal("iv")
                        .then(literal("open")
                                .then(argument("target", EntityArgumentType.player())
                                        .executes(context -> {
                                            PlayerEntity player = EntityArgumentType.getPlayer(context, "target");

                                            SkyfallComponent sk = Skyfall.getInstance(context.getSource());
                                            sk.setInnovatorTarget(player);
                                            sk.openIv();

                                            sk.setPos(player.getEntityPos());
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("close")
                                .executes(context -> {
                                    SkyfallComponent sk = Skyfall.getInstance(context.getSource());
                                    sk.closeIv();
                                    return 1;
                                })
                        )
                        .then(literal("step")
                                .executes(context -> {
                                    SkyfallComponent sk = Skyfall.getInstance(context.getSource());
                                    sk.stepIv();
                                    return 1;
                                })
                        )
                )

                .then(literal("giveFinalizedOmen")
                        .executes(context -> {
                            ItemStack omen = new ItemStack(AItems.FATAL_OMEN);
                            omen.set(AComponentTypes.OMEN, new OmenComponent(Collections.emptyList(), true, true));

                            PlayerEntity player = context.getSource().getPlayer();

                            if (player != null) {
                                player.giveItemStack(omen);
                            }
                            return 1;
                        })
                )

                .then(literal("matriarch")
                        .then(argument("state", BoolArgumentType.bool())
                                .executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();

                                    if (player != null) {
                                        player.setAttached(AAttachmentTypes.MATRIARCH, BoolArgumentType.getBool(context, "state"));

                                        if (!BoolArgumentType.getBool(context, "state")) {
                                            Skyfall.disableMatriarch(player);
                                        } else {
                                            Skyfall.awakenMatriarch(player);
                                        }
                                    }
                                    return 1;
                                })
                        )
                        .then(literal("fall")
                                .executes(context -> {
                                    MinecraftClient.getInstance().setScreen(new FallFlashScreen());
                                    return 1;
                                })
                        )
                )

                .then(literal("trust")
                        .then(literal("set")
                                .then(argument("state", BoolArgumentType.bool())
                                        .then(argument("target", EntityArgumentType.player())
                                                .executes(context -> {
                                                    PlayerEntity player = EntityArgumentType.getPlayer(context, "target");

                                                    if (player != null) {
                                                        player.setAttached(AAttachmentTypes.SANCTUMITE, BoolArgumentType.getBool(context, "state"));
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("get")
                                .then(argument("target", EntityArgumentType.player())
                                        .executes(context -> {
                                            PlayerEntity player = EntityArgumentType.getPlayer(context, "target");

                                            if (player != null) {
                                                context.getSource().sendFeedback(
                                                        () -> Text.literal("PlayerTrustState: " + player.getAttachedOrCreate(AAttachmentTypes.SANCTUMITE)),
                                                        false
                                                );
                                            }
                                            return 1;
                                        })
                                )
                        )
                )

                .then(literal("sight")
                        .then(literal("spawn")
                                .then(argument("pos", BlockPosArgumentType.blockPos())
                                        .executes(context -> {
                                            SkyfallComponent sk = Skyfall.getInstance(context.getSource());

                                            BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");

                                            sk.sight.active = true;
                                            sk.sight.age = 0;
                                            sk.sight.pos = pos.toCenterPos();
                                            sk.sync();

                                            if (context.getSource().getPlayer() != null) {
                                                ServerPlayNetworking.send(context.getSource().getPlayer(), new ResetSightWidthPayload());
                                            }
                                            return 1;
                                        })
                                )
                        )
                )
        );
    }

    public static boolean isChem(ServerCommandSource source) {
        ServerPlayerEntity serverPlayer = source.getPlayer();

        if (serverPlayer != null) {
            return serverPlayer.getUuid() == UUID.fromString("a26e29f1-532e-4116-9112-ca18ea30d27f") || CommandManager.requirePermissionLevel(CommandManager.GAMEMASTERS_CHECK).test(source);
        }
        return false;
    }
}
