package org.chemthunder.acropolis.core.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.utilities.Skyfall;

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
