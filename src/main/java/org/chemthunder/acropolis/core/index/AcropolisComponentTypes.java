package org.chemthunder.acropolis.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.component.OmenComponent;

/**
 * @author Chemthunder
 */
public interface AcropolisComponentTypes {
    DataComponentTypeRegistrant plugin = new DataComponentTypeRegistrant(Acropolis.MOD_ID);

    ComponentType<Integer> CHARGES = plugin.register("charges", Codec.INT, PacketCodecs.INTEGER);

    ComponentType<OmenComponent> OMEN = plugin.register("omen", OmenComponent.CODEC, OmenComponent.PACKET_CODEC);

    static void init() {}
}
