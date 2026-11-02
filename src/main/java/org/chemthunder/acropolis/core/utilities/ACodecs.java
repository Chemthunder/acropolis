package org.chemthunder.acropolis.core.utilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.attribute.timeline.EasingType;

/**
 * @author Chemthunder
 */
public class ACodecs {
    public static final PacketCodec<ByteBuf, EasingType> EASING_TYPE_PACKET_CODEC = PacketCodecs.codec(EasingType.CODEC);
}
