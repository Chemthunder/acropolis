package org.chemthunder.acropolis.core.cca.world;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.networking.s2c.TriggerMercyShadersPayload;
import org.chemthunder.acropolis.core.networking.s2c.MercyOpeningTextTriggerPayload;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class MercyComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<MercyComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("mercy"),
            MercyComponent.class
    );
    private final World world;

    public static final int DELAY = 140;

    private boolean active = false;

    public static final int COUNTDOWN = 50;

    private int ticks = 0;
    private int countdown = 0;

    public MercyComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (active) {
            ticks++;

            if (ticks == DELAY) {
                MinecraftServer server = world.getServer();

                if (server != null) {
                    for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
                        ServerPlayNetworking.send(serverPlayer, new MercyOpeningTextTriggerPayload());
                    }
                }
            }
        }

        if (!active) {
            if (countdown > 0) {
                countdown--;
                if (countdown == 0) {
                    active = true;

                    MinecraftServer server = world.getServer();

                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.setTimeOfDay(18000);
                    }

                    if (server != null) {
                        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
                            ServerPlayNetworking.send(serverPlayer, new TriggerMercyShadersPayload());
                            server.getPlayerManager().sendToAll(new WorldTimeUpdateS2CPacket(18000, 18000, false));
                        }
                    }

                    sync();
                }
            }
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        active = readView.getBoolean("Active", false);

        ticks = readView.getInt("Ticks", 0);
        countdown = readView.getInt("Countdown", 0);
    }

    public void writeData(WriteView writeView) {
        writeView.putBoolean("Active", active);

        writeView.putInt("Ticks", ticks);
        writeView.putInt("Countdown", countdown);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        sync();
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
        sync();
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
        sync();
    }
}
