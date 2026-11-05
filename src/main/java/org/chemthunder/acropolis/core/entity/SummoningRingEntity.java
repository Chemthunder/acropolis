package org.chemthunder.acropolis.core.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;

/**
 * @author Chemthunder
 */
public class SummoningRingEntity extends Entity {
    private float opacity = 0.0F;
    private boolean lifecycleComplete = false;

    public SummoningRingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void tick() {
        super.tick();
    }

    protected void initDataTracker(DataTracker.Builder builder) {}

    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    protected void readCustomData(ReadView view) {
        opacity = view.getFloat("Opacity", 0.0F);
        lifecycleComplete = view.getBoolean("LifecycleComplete", false);
    }

    protected void writeCustomData(WriteView view) {
        view.putFloat("Opacity", opacity);
        view.putBoolean("LifecycleComplete", lifecycleComplete);
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
}
