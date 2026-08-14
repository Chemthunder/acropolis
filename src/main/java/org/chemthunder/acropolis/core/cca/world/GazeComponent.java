package org.chemthunder.acropolis.core.cca.world;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.entity.SpellshotEntity;
import org.chemthunder.acropolis.core.index.AcropolisEntityTypes;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class GazeComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<GazeComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("gaze"),
            GazeComponent.class
    );
    private final World world;

    private @Nullable Vec3d pos;

    private boolean present = false;
    private boolean angered = false;
    private boolean border = false;

    public GazeComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (angered) {
            if (world.getTime() % 20 == 0) {
                if (pos != null && present) {

                    PlayerEntity closest = world.getClosestPlayer(pos.x, pos.y + 50, pos.z, 150, false);

                    if (closest != null) {
                        for (int i = 0; i < new Random().nextInt(3, 9); i++) {
                            Random random = new Random();
                            SpellshotEntity shot = new SpellshotEntity(AcropolisEntityTypes.SPELL_SHOT, world);

                            shot.setPos(pos.x, pos.y + 50, pos.z);
                            shot.setVelocity(random.nextFloat(-1.0F, 1.0F), 1, random.nextFloat(-1.0F, 1.0F));

                            world.spawnEntity(shot);
                        }
                    }
                }
            }
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        pos = readView.read("Pos", Vec3d.CODEC).orElse(null);

        present = readView.getBoolean("Present", false);
        angered = readView.getBoolean("Angered", false);
        border = readView.getBoolean("Border", border);
    }

    public void writeData(WriteView writeView) {
        if (pos != null) writeView.put("Pos", Vec3d.CODEC, pos);

        writeView.putBoolean("Present", present);
        writeView.putBoolean("Angered", angered);
        writeView.putBoolean("Border", border);
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
        sync();
    }

    @Nullable
    public Vec3d getPos() {
        return pos;
    }

    public void setPos(Vec3d pos) {
        this.pos = pos;
        sync();
    }

    public boolean isAngered() {
        return angered;
    }

    public void setAngered(boolean angered) {
        this.angered = angered;
        sync();
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
        sync();
    }
}
