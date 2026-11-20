package org.chemthunder.acropolis.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.acoyt.acornlib.api.util.NetworkingUtils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AComponentTypes;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.item.component.OmenComponent;
import org.chemthunder.acropolis.core.networking.s2c.ResetSightWidthPayload;
import org.chemthunder.acropolis.core.networking.s2c.ShoutPayload;
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
                            SkyfallWorldComponent sk = Skyfall.ofWorld(context.getSource());
                            sk.activate();
                            return 1;
                        })
                )
                .then(literal("close")
                        .executes(context -> {
                            SkyfallWorldComponent sk = Skyfall.ofWorld(context.getSource());
                            sk.close();
                            return 1;
                        })
                )

                .then(literal("iv")
                        .then(literal("open")
                                .then(argument("target", EntityArgumentType.player())
                                        .executes(context -> {
                                            PlayerEntity player = EntityArgumentType.getPlayer(context, "target");

                                            SkyfallWorldComponent sk = Skyfall.ofWorld(context.getSource());
                                            sk.setInnovatorTarget(player);
                                            sk.openIv();

                                            sk.setPos(player.getEntityPos());
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("close")
                                .executes(context -> {
                                    SkyfallWorldComponent sk = Skyfall.ofWorld(context.getSource());
                                    sk.closeIv();
                                    return 1;
                                })
                        )
                        .then(literal("step")
                                .executes(context -> {
                                    SkyfallWorldComponent sk = Skyfall.ofWorld(context.getSource());
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
                                            SkyfallWorldComponent sk = Skyfall.ofWorld(context.getSource());

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

                .then(literal("shout")
                        .then(argument("input", MessageArgumentType.message())
                                .executes(context -> {
                                    NetworkingUtils.sendForAllPlayers(
                                            context.getSource().getWorld(),
                                            new ShoutPayload(
                                                    MessageArgumentType.getMessage(context, "input").getString(),
                                                    0xFFffffff
                                            )
                                    );
                                    return 1;
                                })
                        )
                )
        );

        dispatcher.register(literal("skyfall:weaponcutscene").requires(ACommand::isChemOrNickel)
                .then(literal("execute")
                        .executes(context -> {
                            PlayerEntity player = context.getSource().getPlayer();

                            if (player != null) {
                                Skyfall.prepareRequiem(player);
                            }
                            return 1;
                        })
                )
                .then(literal("executeTargeted")
                        .then(argument("nickel", EntityArgumentType.player())
                                .executes(context -> {
                                    PlayerEntity nickel = EntityArgumentType.getPlayer(context, "nickel");

                                    if (nickel != null) {
                                        Skyfall.prepareRequiem(nickel);
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }

    public static boolean isChem(ServerCommandSource source) {
        ServerPlayerEntity serverPlayer = source.getPlayer();

        if (serverPlayer != null) {
            return serverPlayer.getUuid().equals(UUID.fromString("a26e29f1-532e-4116-9112-ca18ea30d27f")) || (FabricLoader.getInstance().isDevelopmentEnvironment() && CommandManager.requirePermissionLevel(CommandManager.GAMEMASTERS_CHECK).test(source));
        }
        return false;
    }

    public static boolean isChemOrNickel(ServerCommandSource source) {
        ServerPlayerEntity serverPlayer = source.getPlayer();

        if (serverPlayer != null) {
            return serverPlayer.getUuid().equals(UUID.fromString("a7d1440e-da2f-4d7b-a33d-cf615ca7a0c2")) || isChem(source);
        }
        return false;
    }
}
