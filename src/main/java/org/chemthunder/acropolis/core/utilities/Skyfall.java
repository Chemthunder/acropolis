package org.chemthunder.acropolis.core.utilities;

import net.acoyt.acornlib.api.util.NetworkingUtils;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallEntityComponent;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.cca.skyfall.obj.SightInstance;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.networking.s2c.ResetSightWidthPayload;
import org.chemthunder.acropolis.core.networking.s2c.ShoutPayload;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public abstract class Skyfall {
    public static SkyfallWorldComponent ofWorld(Entity entity) {
        return entity.getEntityWorld().getComponent(SkyfallWorldComponent.KEY);
    }

    public static SkyfallWorldComponent ofWorld(World world) {
        return world.getComponent(SkyfallWorldComponent.KEY);
    }

    public static SkyfallWorldComponent ofWorld(ServerCommandSource src) {
        return src.getWorld().getComponent(SkyfallWorldComponent.KEY);
    }

    public static SkyfallEntityComponent ofEntity(LivingEntity entity) {
        return entity.getComponent(SkyfallEntityComponent.KEY);
    }

    public static void shout(String input, int color, List<Entity> entities) {
        List<ServerPlayerEntity> serverPlayerEntities = new ArrayList<>();

        for (Entity en : entities) {
            if (en instanceof ServerPlayerEntity serverPlayer) {
                serverPlayerEntities.add(serverPlayer);
            }
        }

        serverPlayerEntities.forEach(cap -> ServerPlayNetworking.send(cap, new ShoutPayload(input, color)));
    }

    public static void shout(String input, int color, ServerPlayerEntity[] serverPlayerEntities) {
        for (ServerPlayerEntity sp : serverPlayerEntities) {
            ServerPlayNetworking.send(sp, new ShoutPayload(input, color));
        }
    }

    public static void shout(String input, int color, Entity[] serverPlayerEntities) {
        for (Entity e : serverPlayerEntities) {
            if (e instanceof ServerPlayerEntity sp) {
                ServerPlayNetworking.send(sp, new ShoutPayload(input, color));
            }
        }
    }

    public static boolean isMatri(Entity entity) {
        if (entity instanceof PlayerEntity player) {
            return player.getAttachedOrElse(AAttachmentTypes.MATRIARCH, false);
        }
        return false;
    }

    public static List<Text> getInnovatorSpeech() {
        return List.of(
                lang("LET'S TAKE A BREAK FROM THE VIOLENCE."),
                lang("I BELIEVE SOMEONE HERE IS OF NEED TO ME."),
                lang("THANK YOU FOR NOT RESISTING, ENTITIES OF THIS WORLD. IT MAKES MY TIME HERE MUCH MORE BEARABLE."),
                lang("YOUR ATTEMPTS ARE PITIFUL."),
                lang("MY TIME HERE IS OVER, AND MY GOAL IS COMPLETE."),
                lang("AND YOU..."), // crack
                lang("ARE FIT TO BE DISPOSED OF."),
                lang("DO YOU HAVE ANYTHING LEFT TO SAY FOR YOURSELF, 46?"),
                Text.literal("Chemthunder has left the game").formatted(Formatting.YELLOW),
                lang("YOU REALLY ARE AS PATHETIC AS I THOUGHT."), // end
                lang("FAREWELL, BEINGS. I AM TRULY SORRY FOR THIS INCONVENIENCE."),
                lang("CARRY ON."),
                Text.empty()
        );
    }

    public static Text lang(String input) {
        return Text.literal(input).formatted(Formatting.BOLD, Formatting.WHITE);
    }

    public static List<String> getShockwaveTexts() {
        return List.of(
                "I WILL NOT FALTER",
                "THY END IS NOW",
                "TODAY WILL BE YOUR LAST",
                "GROVEL BEFORE ME"
        );
    }

    public static void awakenMatriarch(PlayerEntity player) {
        EntityAttributeInstance atkDamage = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        EntityAttributeInstance mv = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);

        if (atkDamage != null && mv != null) {
            if (atkDamage.hasModifier(Acropolis.id("matriarch_attack_damage"))) {
                atkDamage.removeModifier(Acropolis.id("matriarch_attack_damage"));
            }

            if (mv.hasModifier(Acropolis.id("matriarch_attack_speed"))) {
                mv.removeModifier(Acropolis.id("matriarch_attack_speed"));
            }

            atkDamage.addTemporaryModifier(
                    new EntityAttributeModifier(
                            Acropolis.id("matriarch_attack_damage"),
                            2.0,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
            );

            mv.addTemporaryModifier(
                    new EntityAttributeModifier(
                            Acropolis.id("matriarch_attack_speed"),
                            0.05,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    )
            );
        }
    }

    public static void disableMatriarch(PlayerEntity player) {
        EntityAttributeInstance atkDamage = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        EntityAttributeInstance mv = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);

        if (atkDamage != null && mv != null) {
            atkDamage.removeModifier(Acropolis.id("matriarch_attack_damage"));

            mv.removeModifier(Acropolis.id("matriarch_attack_speed"));
        }
    }

    public static void dash(ServerPlayerEntity player) {
        SkyfallEntityComponent t = player.getComponent(SkyfallEntityComponent.KEY);

        if (t.dashBuffer <= 0) {
            player.setVelocity(player.getRotationVec(0).multiply(2));
            player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));
            t.dashBuffer = 20;
            t.sync();
        } else {
            player.setVelocity(0, 0, 0);
            player.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(player));
            t.dashBuffer = 0;
            t.sync();
        }
    }

    public static void summonSight(World world, Vec3d hit) {
        SkyfallWorldComponent sk = ofWorld(world);
        SightInstance ins = sk.sight;

        ins.pos = hit;
        ins.active = true;
        ins.age = 0;

        sk.sync();

        NetworkingUtils.sendForAllPlayers(world, new ResetSightWidthPayload());
    }

    public static boolean shouldHaveOutline(Entity entity) {
        SkyfallWorldComponent sk = ofWorld(entity);
        return entity.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false) && sk.externalVisible;
    }

    public static void prepareRequiem(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();
        SkyfallWorldComponent sk = ofWorld(player);

        for (ItemStack stack : inventory) {
            if (stack.getRegistryEntry().matchesId(Identifier.of("divine_arms", "eulogy_of_the_dead"))) {
                stack.split(1);
            }
        }

        sk.weaponCutscene.active = true;
        sk.weaponCutscene.age = 0;
        sk.weaponCutscene.nickel = player.getId();
        sk.weaponCutscene.sync();
    }

    public static void broadcast(World world, Text message) {
        MinecraftServer server = world.getServer();

        if (server != null) {
            PlayerManager manager = server.getPlayerManager();

            manager.broadcast(message, false);
        }
    }
}
