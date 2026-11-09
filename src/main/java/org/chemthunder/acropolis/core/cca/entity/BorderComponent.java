package org.chemthunder.acropolis.core.cca.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class BorderComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<BorderComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("border"),
            BorderComponent.class
    );
    private final LivingEntity living;

    public static final int TIME_ENTERING_COMBAT = (15 * 20);

    private int fightTimer = 0;
    private int timeSpentInCombat = 0;

    private int distState = 0;
    private int fightState = 0;

    private boolean cracked = false;

    public BorderComponent(LivingEntity living) {
        this.living = living;
    }

    public void tick() {
        GazeComponent gaze = living.getEntityWorld().getComponent(GazeComponent.KEY);

        if (gaze.getPos() != null) {
            double dist = living.getEntityPos().distanceTo(gaze.getPos());

            int newValue = 0;
            if (dist < (80 + 80)) {
                newValue = 1;
            }
            if (dist < (60 + 80)) {
                newValue = 2;
            }
            if (dist < (40 + 80)) {
                newValue = 3;
            }
            if (dist < (20 + 80)) {
                newValue = 4;
            }

            if (distState != newValue) {
                distState = newValue;
                sync();
            }
        }

        if (fightTimer > 0) {
            fightTimer--;
            timeSpentInCombat++;
            if (fightTimer <= 0) {
                timeSpentInCombat = 0;
                sync();
            }
        }

        if (living instanceof PlayerEntity player) {
            player.sendMessage(Text.literal("FIGHT: " + fightTimer + " || " + timeSpentInCombat), true);
        }
    }

    public void sync() {
        KEY.sync(living);
    }

    public void readData(ReadView readView) {
        fightTimer = readView.getInt("FightTimer", 0);
        timeSpentInCombat = readView.getInt("TimeSpentInCombat", 0);

        distState = readView.getInt("DistanceState", 0);
        fightState = readView.getInt("FightState", 0);

        cracked = readView.getBoolean("Cracked", false);
    }

    public void writeData(WriteView writeView) {
        writeView.putInt("FightTimer", fightTimer);
        writeView.putInt("TimeSpentInCombat", timeSpentInCombat);

        writeView.putInt("DistanceState", distState);
        writeView.putInt("FightState", fightState);

        writeView.putBoolean("Cracked", cracked);
    }

    public static void apply(LivingEntity head, LivingEntity tail) {
        BorderComponent h = head.getComponent(KEY);
        BorderComponent t = tail.getComponent(KEY);

        h.fightTimer = TIME_ENTERING_COMBAT;
        t.fightTimer = TIME_ENTERING_COMBAT;

        h.sync();
        t.sync();
    }

    public int getDistState() {
        return distState;
    }

    public int getFightState() {
        return fightState;
    }

    public boolean isCracked() {
        return cracked;
    }

    public void setCracked(boolean cracked) {
        this.cracked = cracked;
        sync();
    }

    public boolean inCombat() {
        return this.fightTimer > 0;
    }

    public void incFightState() {
        if (this.fightState < 4) {
            this.fightState++;
        } else {
            this.fightState = 0;
        }
        sync();
    }
}