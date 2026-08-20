package org.chemthunder.acropolis.core.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.chemthunder.acropolis.core.Acropolis;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class HeadacheComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<HeadacheComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("headache"),
            HeadacheComponent.class
    );
    private final PlayerEntity player;

    private float opacity = 0.0F;
    private int postTicks = 0;

    public HeadacheComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {

    }

    public void readData(ReadView readView) {

    }

    public void writeData(WriteView writeView) {

    }
}
