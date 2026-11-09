package org.chemthunder.acropolis.core.cca.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class TargetComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<TargetComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("target"),
            TargetComponent.class
    );
    private final LivingEntity living;

    public static final int FALL_REF = 20;
    public static final int FALL_DAMAGE_REF = 80;

    public int fallFreezeTicks = 0;
    public int fallDamageHeldTicks = 0;

    public TargetComponent(LivingEntity living) {
        this.living = living;
    }

    public void tick() {
        if (fallFreezeTicks > 0) {
            fallFreezeTicks--;
            if (fallFreezeTicks <= 0) {
                sync();
            }
        }

        if (fallDamageHeldTicks > 0) {
            fallDamageHeldTicks--;
            if (fallDamageHeldTicks <= 0) {
                if (living.getEntityWorld() instanceof ServerWorld serverWorld) {
                    living.damage(serverWorld, living.getDamageSources().create(ADamageTypes.FALL), 8.5F);
                }
                sync();
            }
        }
    }

    public void sync() {
        KEY.sync(living);
    }

    public void readData(ReadView readView) {
        fallFreezeTicks = readView.getInt("FallFreezeTicks", 0);
    }

    public void writeData(WriteView writeView) {
        writeView.putInt("FallFreezeTicks", fallFreezeTicks);
    }

    public void freeze() {
        this.fallFreezeTicks = FALL_REF;
        this.fallDamageHeldTicks = FALL_DAMAGE_REF;
        sync();
    }
}
