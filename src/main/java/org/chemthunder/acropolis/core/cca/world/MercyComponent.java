package org.chemthunder.acropolis.core.cca.world;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
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

    private boolean active = false;
    private float skyBrightness = 0.0F;

    public MercyComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (active) {
            if (skyBrightness > 0.0F) {
                skyBrightness -= 0.1F;
                sync();
            }
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        active = readView.getBoolean("Active", false);
        skyBrightness = readView.getFloat("SkyBrightness", 0.0F);
    }

    public void writeData(WriteView writeView) {
        writeView.putBoolean("Active", active);
        writeView.putFloat("SkyBrightness", skyBrightness);
    }

    public float getSkyBrightness() {
        return skyBrightness;
    }

    public void setSkyBrightness(float skyBrightness) {
        this.skyBrightness = skyBrightness;
        sync();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        sync();
    }
}
