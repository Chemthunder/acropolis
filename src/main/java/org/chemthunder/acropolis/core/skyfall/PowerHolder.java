package org.chemthunder.acropolis.core.skyfall;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

import java.util.UUID;

/**
 * @author Chemthunder
 */
public enum PowerHolder implements StringIdentifiable {
    CHEM("chem", 0xFFf467fe, UUID.fromString("a26e29f1-532e-4116-9112-ca18ea30d27f")),
    NICKEL("nickel", 0xFF09e1e3, UUID.fromString("a7d1440e-da2f-4d7b-a33d-cf615ca7a0c2"));

    public final String name;
    public final int color;
    public final UUID uuid;

    public static final Codec<PowerHolder> CODEC = StringIdentifiable.createCodec(PowerHolder::values);

    PowerHolder(String name, int color, UUID uuid) {
        this.name = name;
        this.color = color;
        this.uuid = uuid;
    }

    public String asString() {
        return this.name;
    }
}
