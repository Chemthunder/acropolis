package org.chemthunder.acropolis.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public class EntityAssembler<T extends Entity> {
    private BiConsumer<T, World> consumer;
    private final @Nullable T entity;

    private final World world;

    public EntityAssembler(World world, EntityType<T> type) {
        this.world = world;

        this.entity = type.create(world, SpawnReason.MOB_SUMMONED);
    }

    public EntityAssembler(World world, EntityType<T> type, SpawnReason reason) {
        this.world = world;

        this.entity = type.create(world, reason);
    }

    public static <T extends Entity> EntityAssembler<T> create(World world, EntityType<T> type) {
        return new EntityAssembler<>(world, type);
    }

    public static <T extends Entity> EntityAssembler<T> create(World world, EntityType<T> type, SpawnReason reason) {
        return new EntityAssembler<>(world, type, reason);
    }

    public EntityAssembler<T> build(BiConsumer<T, World> consumer) {
        this.consumer = consumer;
        return this;
    }

    public void spawn() {
        if (this.entity != null) {
            this.consumer.accept(this.entity, this.world);
            this.world.spawnEntity(this.entity);
            return;
        }
        Acropolis.LOGGER.info("Unable to create entity!");
    }
}
