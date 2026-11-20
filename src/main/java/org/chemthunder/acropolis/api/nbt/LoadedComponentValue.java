package org.chemthunder.acropolis.api.nbt;

import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

/**
 * @author Chemthunder
 */
public interface LoadedComponentValue<T> {
    T read(ReadView view);

    void write(WriteView view);

    void sync();
}
