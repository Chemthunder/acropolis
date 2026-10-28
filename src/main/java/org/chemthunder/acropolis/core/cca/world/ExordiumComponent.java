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
public class ExordiumComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ExordiumComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("exordium"),
            ExordiumComponent.class
    );
    private final World world;

    private @Nullable Vec3d pos = null;
    private float radius = 0.0F;
    private boolean active = false;

    public ExordiumComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (pos != null) {
            if (active) {
                if (radius < 80) {
                    radius += 0.5F;
                    sync();
                }
            }
        } else {
            if (active) {
                active = false;
                radius = 0.0F;
                sync();
            }
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        pos = readView.read("Pos", Vec3d.CODEC).orElse(null);

        active = readView.getBoolean("Active", false);
        radius = readView.getFloat("Radius", 0.0F);
    }

    public void writeData(WriteView writeView) {
        if (pos != null) writeView.put("Pos", Vec3d.CODEC, pos);

        writeView.putBoolean("Active", active);
        writeView.putFloat("Radius", radius);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        sync();
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

    public float lerped() {
        return this.radius / 80;
    }
}
