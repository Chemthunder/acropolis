package org.chemthunder.acropolis.core.cca.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
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

        if (gaze.getPos() != null && gaze.isBorder()) {
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

        if (!gaze.isBorder()) {
            if (distState > 0) {
                distState = 0;
                sync();
            }
        }

        if (fightTimer > 0) {
            double dist = living.getEntityPos().distanceTo(gaze.getPos());

            if (fightState < 2) {
                fightTimer--;
            } else if (fightState >= 3 && dist > (80 + 80)) {
                fightTimer--;
            }
            timeSpentInCombat++;
            if (fightTimer <= 0) {
                timeSpentInCombat = 0;
                sync();
            }
        }

        if (fightTimer > 0) {
            double dist = living.getEntityPos().distanceTo(gaze.getPos());

            int newValue = 0;

            if (dist < (80 + 80)) {
                for (int i = 1; i < 5; i++) {
                    if (timeSpentInCombat > ((i * 10) * 20)) {
                        newValue = i;
                    }
                }
            }

            if (fightState != newValue) {
                fightState = newValue;
                sync();
            }

            if (living.getEntityWorld() instanceof ServerWorld serverWorld) {
                if (fightState == 2) {
                    if (living.age % 20 == 0) {
                        living.damage(serverWorld, living.getDamageSources().create(ADamageTypes.BORDER), 2.0F);
                    }
                }
                if (fightState == 3) {
                    if (living.age % 10 == 0) {
                        living.damage(serverWorld, living.getDamageSources().create(ADamageTypes.BORDER_TRUE), 3.0F);
                    }
                }
                if (fightState == 4) {
                    if (living.age % 5 == 0) {
                        living.damage(serverWorld, living.getDamageSources().create(ADamageTypes.BORDER_TRUE), 2.5F);
                    }
                }

                if (fightState >= 3) {
                    if (!living.hasStatusEffect(StatusEffects.GLOWING)) {
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, (10 * 20)));
                    }
                    if (living.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                        living.removeStatusEffect(StatusEffects.INVISIBILITY);
                    }
                }
            }

            if (living instanceof PlayerEntity player) {
                player.sendMessage(Text.literal("FIGHT: " + fightTimer + " || " + (timeSpentInCombat / 20)), true);
            }
        }
    }

    public void sync () {
        KEY.sync(living);
    }

    public void readData (ReadView readView){
        fightTimer = readView.getInt("FightTimer", 0);
        timeSpentInCombat = readView.getInt("TimeSpentInCombat", 0);

        distState = readView.getInt("DistanceState", 0);
        fightState = readView.getInt("FightState", 0);

        cracked = readView.getBoolean("Cracked", false);
    }

    public void writeData (WriteView writeView){
        writeView.putInt("FightTimer", fightTimer);
        writeView.putInt("TimeSpentInCombat", timeSpentInCombat);

        writeView.putInt("DistanceState", distState);
        writeView.putInt("FightState", fightState);

        writeView.putBoolean("Cracked", cracked);
    }

    public static void apply (LivingEntity vic, LivingEntity att){
        BorderComponent h = vic.getComponent(KEY);
        BorderComponent t = att.getComponent(KEY);

        if (h.fightTimer > 0) {
            h.fightTimer = (11 * 20);
        } else {
            h.fightTimer = (5 * 20);
        }

        if (t.fightTimer > 0) {
            t.fightTimer = (11 * 20);
        } else {
            t.fightTimer = (7 * 20);
        }

        h.fightState = 0;
        t.fightState = 0;

        h.sync();
        t.sync();
    }

    public int getDistState () {
        return distState;
    }

    public int getFightState () {
        return fightState;
    }

    public boolean isCracked () {
        return cracked;
    }

    public void setCracked ( boolean cracked){
        this.cracked = cracked;
        sync();
    }

    public boolean inCombat () {
        return this.fightTimer > 0;
    }

    public void incFightState () {
        if (this.fightState < 4) {
            this.fightState++;
        } else {
            this.fightState = 0;
        }
        sync();
    }
}