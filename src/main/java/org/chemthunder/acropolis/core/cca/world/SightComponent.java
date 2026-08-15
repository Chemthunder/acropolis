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
public class SightComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<SightComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("sight"),
            SightComponent.class
    );
    private final World world;

    private @Nullable Vec3d pos;

    private float beamWidth = 0.0F;
    private int ticks = 0;

    public SightComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (ticks < 900) {
            // finalize
        }

        if (beamWidth < 1.3F) {
            beamWidth += 0.1F;
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        pos = readView.read("Pos", Vec3d.CODEC).orElse(null);

        beamWidth = readView.getFloat("BeamWidth", 0.0F);
        ticks = readView.getInt("Ticks", 0);
    }

    public void writeData(WriteView writeView) {
        if (pos != null) writeView.put("Pos", Vec3d.CODEC, pos);

        writeView.putFloat("BeamWidth", beamWidth);
        writeView.putInt("Ticks", ticks);
    }

    @Nullable
    public Vec3d getPos() {
        return pos;
    }

    public void setPos(Vec3d pos) {
        this.pos = pos;
        sync();
    }

    public float getBeamWidth() {
        return beamWidth;
    }

    public void setBeamWidth(float beamWidth) {
        this.beamWidth = beamWidth;
        sync();
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
        sync();
    }
}
