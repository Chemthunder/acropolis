package org.chemthunder.acropolis.core.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.List;

/**
 * @author Chemthunder
 */
public record OmenComponent(List<String> containedNames, boolean closed, boolean finalized) {
    public static final Codec<OmenComponent> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING.listOf().fieldOf("names").forGetter(OmenComponent::containedNames),
            Codec.BOOL.fieldOf("closed").forGetter(OmenComponent::closed),
            Codec.BOOL.fieldOf("finalized").forGetter(OmenComponent::finalized)
    ).apply(codec, OmenComponent::new));

    public static final PacketCodec<ByteBuf, OmenComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
