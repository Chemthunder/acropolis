package org.chemthunder.acropolis.core.cca.world;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
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
    private float scale = 0.0F;
    private int ticks = 0;

    public SightComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (pos != null) {
            ticks++;

            if (ticks > 0 && ticks < 180) {
                if (scale < 1.0F) {
                    scale += 0.2F;
                }
            }

            if (ticks == 3) {
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            ParticleTypes.END_ROD,
                            pos.x,
                            pos.y,
                            pos.z,
                            15,
                            0,
                            0,
                            0,
                            0.2F
                    );
                }
            }

            if (ticks > 180) {
                if (scale > 0.0F) {
                    scale -= 0.2F;
                }
            }

            if (ticks > 40 && ticks < 120) {
                if (beamWidth < 1.3F) {
                    beamWidth += 0.1F;
                }
            }

            if (ticks > 160) {
                if (beamWidth > 0.0F) {
                    beamWidth -= 0.1F;
                }
            }

            if (ticks >= 200) {
                if (world instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            ParticleTypes.END_ROD,
                            pos.x,
                            pos.y,
                            pos.z,
                            15,
                            0,
                            0,
                            0,
                            0.2F
                    );
                }

                pos = null;
                beamWidth = 0.0F;
                scale = 0.0F;
                ticks = 0;
                sync();
            }
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        pos = readView.read("Pos", Vec3d.CODEC).orElse(null);

        beamWidth = readView.getFloat("BeamWidth", 0.0F);
        scale = readView.getFloat("Scale", 0.0F);
        ticks = readView.getInt("Ticks", 0);
    }

    public void writeData(WriteView writeView) {
        if (pos != null) writeView.put("Pos", Vec3d.CODEC, pos);

        writeView.putFloat("BeamWidth", beamWidth);
        writeView.putFloat("Scale", scale);
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

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        sync();
    }
}
