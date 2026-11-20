package org.chemthunder.acropolis.core.cca.skyfall;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
public class SkyfallEntityComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<SkyfallEntityComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("target"),
            SkyfallEntityComponent.class
    );
    private final LivingEntity living;

    public static final int FALL_REF = 80;

    public int fallTicks = 0;

    public int lockMovementTicks = 0;

    public int dashBuffer = 0;

    public SkyfallEntityComponent(LivingEntity living) {
        this.living = living;
    }

    public void tick() {
        if (dashBuffer > 0) {
            dashBuffer--;
            if (dashBuffer <= 0) {
                sync();
            }
        }

        if (lockMovementTicks > 0) {
            lockMovementTicks--;
            if (lockMovementTicks <= 0) {
                sync();
            }
        }

        if (fallTicks > 0) {
            fallTicks--;
            if (fallTicks <= 0) {
                if (living.getEntityWorld() instanceof ServerWorld serverWorld) {
                    living.damage(serverWorld, living.getDamageSources().create(ADamageTypes.FALL), 8.5F);
                }

                if (living.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                    if (living.getOffHandStack().contains(DataComponentTypes.DEATH_PROTECTION)) {
                        DeathProtectionComponent d = living.getOffHandStack().get(DataComponentTypes.DEATH_PROTECTION);

                        if (d != null) {
                            d.applyDeathEffects(living.getOffHandStack(), living);
                        }
                    }
                    living.getOffHandStack().split(1);
                }

                living.getEntityWorld().playSound(
                        null,
                        living.getX(),
                        living.getY(),
                        living.getZ(),
                        SoundEvents.ENTITY_ALLAY_ITEM_THROWN,
                        SoundCategory.PLAYERS,
                        1,
                        0.44F
                );
                sync();
            }
        }
    }

    public void sync() {
        KEY.sync(living);
    }

    public void readData(ReadView readView) {
        fallTicks = readView.getInt("FallTicks", 0);

        lockMovementTicks = readView.getInt("LockMovementTicks", 0);

        dashBuffer = readView.getInt("DashBuffer", 0);
    }

    public void writeData(WriteView writeView) {
        writeView.putInt("FallTicks", fallTicks);

        writeView.putInt("LockMovementTicks", lockMovementTicks);

        writeView.putInt("DashBuffer", dashBuffer);
    }

    public void freeze() {
        this.fallTicks = FALL_REF;
        sync();
    }

    public boolean isFrozen() {
        return this.lockMovementTicks > 0;
    }

    public void stunlock() {
        this.lockMovementTicks = (4 * 20);
        this.sync();
    }
}
