package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.component.OmenComponent;

/**
 * @author Chemthunder
 */
public interface AComponentTypes {
    DataComponentTypeRegistrant plugin = new DataComponentTypeRegistrant(Acropolis.MOD_ID);

    ComponentType<OmenComponent> OMEN = plugin.register("omen", OmenComponent.CODEC, OmenComponent.PACKET_CODEC);

    static void init() {}
}
