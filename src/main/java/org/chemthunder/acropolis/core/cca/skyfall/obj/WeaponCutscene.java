package org.chemthunder.acropolis.core.cca.skyfall.obj;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.chemthunder.acropolis.api.nbt.LoadedComponentValue;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.utilities.Skyfall;

/**
 * @author Chemthunder
 */
public class WeaponCutscene implements LoadedComponentValue<WeaponCutscene> {
    public int nickel;
    public int age;

    public boolean active = false;

    private final World world;

    public WeaponCutscene(World world) {
        this.world = world;

        this.nickel = 0;
        this.age = 999999999;
    }

    public void tick() {
        if (active) {
            age++;

            if (age == (3 * 20)) {
                broadcast(nPrefix("Nickel, today is a special occasion."));
            }

            if (age == (6 * 20)) {
                broadcast(nPrefix(Text.literal("Where mine and ").append(Text.literal("His").formatted(Formatting.BOLD, Formatting.RED)).append(Text.literal(" power will combine into one."))));
            }

            if (age == (12 * 20)) {
                broadcast(nPrefix("Go, Descendant of Death, and kill."));
            }

            if (age == (16 * 20)) {
                Entity unparsed = this.world.getEntityById(this.nickel);

                if (unparsed instanceof PlayerEntity player) {
                    if (player.getMainHandStack().isEmpty()) {
                        player.equipStack(EquipmentSlot.MAINHAND, new ItemStack(AItems.REQUIEM));
                    } else {
                        player.giveItemStack(new ItemStack(AItems.REQUIEM));
                    }

                    broadcast(Text.empty()
                            .append(Text.literal("The "))
                            .append(Text.literal("Eulogy of the Dead").formatted(Formatting.YELLOW))
                            .append(Text.literal(" has been transformed into the "))
                            .append(Text.literal("Requiem").formatted(Formatting.RED))
                            .append(".")
                    );
                }
            }

            if (age == (22 * 20)) {
                broadcast(rPrefix("And yet I see our time has come."));
            }

            if (age == (28 * 20)) {
                broadcast(rPrefix("I've awakened in your hands once more, Rynlei."));
            }

            if (age == (33 * 20)) {
                broadcast(rPrefix("So go on and carry fate as your cutting edge,"));
            }

            if (age == (36 * 20)) {
                broadcast(rPrefix("and bring forth your enemies' rapture."));
            }

            if (age == (39 * 20)) {
                broadcast(rPrefix("Seal the deal,"));
            }

            if (age == (44 * 20)) {
                broadcast(rPrefix(Text.literal("Once and for all, ").append(Text.literal("Matriarch").formatted(Formatting.LIGHT_PURPLE)).append(".")));
            }
        }
    }

    private Text nPrefix(Text suffixed) {
        return Text.literal("<").append(Text.literal("Carrion").formatted(Formatting.OBFUSCATED, Formatting.YELLOW, Formatting.BOLD)).append(">").append(" ").append(suffixed);
    }

    private Text nPrefix(String suffixed) {
        return Text.literal("<").append(Text.literal("Carrion").formatted(Formatting.OBFUSCATED, Formatting.YELLOW, Formatting.BOLD)).append(">").append(" ").append(suffixed);
    }

    private Text rPrefix(Text suffixed) {
        return Text.literal("<").append(Text.literal("RAPTURE").formatted(Formatting.OBFUSCATED, Formatting.LIGHT_PURPLE, Formatting.BOLD)).append(">").append(" ").append(suffixed);
    }

    private Text rPrefix(String suffixed) {
        return Text.literal("<").append(Text.literal("RAPTURE").formatted(Formatting.OBFUSCATED, Formatting.LIGHT_PURPLE, Formatting.BOLD)).append(">").append(" ").append(suffixed);
    }

    private void broadcast(Text message) {
        MinecraftServer server = world.getServer();

        if (server != null) {
            PlayerManager manager = server.getPlayerManager();

            manager.broadcast(message, false);
        }
    }

    public WeaponCutscene read(ReadView view) {
        WeaponCutscene readInstance = new WeaponCutscene(this.world);

        readInstance.nickel = view.getInt("Nickel", 0);
        readInstance.age = view.getInt("CutsceneAge", 0);

        readInstance.active = view.getBoolean("CutsceneActive", false);

        return readInstance;
    }

    public void write(WriteView view) {
        view.putInt("Nickel", nickel);
        view.putInt("CutsceneAge", age);

        view.putBoolean("CutsceneActive", active);
    }

    public void sync() {
        Skyfall.ofWorld(this.world).sync();
    }
}

