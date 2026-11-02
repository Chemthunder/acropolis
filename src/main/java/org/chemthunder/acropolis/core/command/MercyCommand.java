package org.chemthunder.acropolis.core.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Chemthunder
 */
public class MercyCommand implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("acropolis::mercy").requires(MercyCommand::isViable)
                .then(literal("toggle").then(argument("state", BoolArgumentType.bool())
                        .executes(context -> {
                            boolean bool = BoolArgumentType.getBool(context, "state");
                            World world = context.getSource().getWorld();

                            MercyComponent.KEY.get(world).setActive(bool);
                            return 1;
                        }))
                )
                .then(literal("begin")
                        .executes(context -> {
                            World world = context.getSource().getWorld();

                            MercyComponent m = MercyComponent.KEY.get(world);

                            m.setCountdown(MercyComponent.COUNTDOWN);
                            m.setTicks(0);
                            return 1;
                        })
                )
        );

        dispatcher.register(literal("exordium")
                .then(literal("spawn")
                        .executes(context -> {
                            PlayerEntity player = context.getSource().getPlayer();

                            if (player != null) {
                                Vec3d pos = new Vec3d(
                                        player.getBlockPos().toCenterPos().x,
                                        player.getBlockPos().toCenterPos().y + 1,
                                        player.getBlockPos().toCenterPos().z
                                );

                                SkyfallComponent ex = SkyfallComponent.KEY.get(player.getEntityWorld());

                                ex.setActive(true);
                                ex.setPos(pos);
                            }

                            return 1;
                        })
                )
                .then(literal("remove")
                        .executes(context -> {
                            PlayerEntity player = context.getSource().getPlayer();

                            if (player != null) {
                                Vec3d pos = new Vec3d(
                                        player.getBlockPos().toCenterPos().x,
                                        player.getBlockPos().toCenterPos().y + 1,
                                        player.getBlockPos().toCenterPos().z
                                );

                                SkyfallComponent ex = SkyfallComponent.KEY.get(player.getEntityWorld());

                                ex.setActive(false);
                                ex.setSunscale(0.0F);
                                ex.setPos(null);
                            }

                            return 1;
                        })
                )
        );
    }

    public static boolean isViable(ServerCommandSource source) {
        return Acropolis.PLAYTEST;
    }
}
