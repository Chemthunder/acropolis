package org.chemthunder.acropolis.core.cca;

import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.jetbrains.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class SightInstance {
    public @Nullable Vec3d pos;
    public int age;
    public boolean active = false;

    private final World world;

    public SightInstance(World world) {
        this.world = world;
        this.pos = null;
        this.age = 0;
    }

    public void tick() {
        if (this.pos != null) {
            if (this.active) {
                this.age++;
                if (this.age >= (6 * 20)) {
                    this.active = false;
                    this.age = 0;
                    sync();
                }

                Box capture = new Box(new BlockPos.Mutable(this.pos.x, this.pos.y, this.pos.z)).expand(5.5, 60, 5.5);

                if (this.age % 5 == 0) {
                    for (LivingEntity target : world.getEntitiesByClass(LivingEntity.class, capture, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)) {
                        if (!target.getAttachedOrElse(AAttachmentTypes.SANCTUMITE, false)) {
                            if (world instanceof ServerWorld serverWorld) {
                                target.damage(serverWorld, target.getDamageSources().create(ADamageTypes.SIGHT), 2.5F);
                            }
                        }
                    }
                }
            }
        }
    }

    public SightInstance read(ReadView view) {
        SightInstance readInstance = new SightInstance(this.world);

        readInstance.age = view.getInt("SightAge", 0);
        readInstance.active = view.getBoolean("SightActive", false);

        readInstance.pos = view.read("SightPos", Vec3d.CODEC).orElse(null);

        return readInstance;
    }

    public void write(WriteView view) {
        view.putInt("SightAge", this.age);
        view.putBoolean("SightActive", this.active);

        if (this.pos != null) {
            view.put("SightPos", Vec3d.CODEC, this.pos);
        }
    }

    private void sync() {
        SkyfallComponent.KEY.sync(this.world);
    }
}
