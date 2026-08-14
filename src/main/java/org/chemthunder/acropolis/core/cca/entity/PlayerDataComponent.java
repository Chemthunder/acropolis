package org.chemthunder.acropolis.core.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.chemthunder.acropolis.core.Acropolis;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

/**
 * @author Chemthunder
 */
public class PlayerDataComponent implements AutoSyncedComponent {
    public static final ComponentKey<PlayerDataComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("player_data"),
            PlayerDataComponent.class
    );
    private final PlayerEntity player;

    private boolean trusted = false;

    public PlayerDataComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readData(ReadView readView) {
        trusted = readView.getBoolean("Trusted", trusted);
    }

    public void writeData(WriteView writeView) {
        writeView.putBoolean("Trusted", trusted);
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
        sync();
    }
}
