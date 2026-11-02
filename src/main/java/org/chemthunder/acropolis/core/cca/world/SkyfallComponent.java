package org.chemthunder.acropolis.core.cca.world;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class SkyfallComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<SkyfallComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("skyfall"),
            SkyfallComponent.class
    );
    private final World world;

    private @Nullable Vec3d pos = null;
    private boolean active = false;

    public static final float MAX_SUNSCALE = 14;
    private float sunscale = 0.0F;

    public SkyfallComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (active) {
            if (sunscale < MAX_SUNSCALE) {
                sunscale += 0.001F;
            }
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        pos = readView.read("Pos", Vec3d.CODEC).orElse(null);

        active = readView.getBoolean("Active", false);

        sunscale = readView.getFloat("Sunscale", 0.0F);
    }

    public void writeData(WriteView writeView) {
        if (pos != null) writeView.put("Pos", Vec3d.CODEC, pos);

        writeView.putBoolean("Active", active);

        writeView.putFloat("Sunscale", sunscale);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        sync();
    }

    @Nullable
    public Vec3d getPos() {
        return pos;
    }

    public void setPos(@Nullable Vec3d pos) {
        this.pos = pos;
        sync();
    }

    public float getSunscale() {
        return sunscale;
    }

    public void setSunscale(float sunscale) {
        this.sunscale = sunscale;
        sync();
    }
}
