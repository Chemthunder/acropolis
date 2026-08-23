package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.SoundEventRegistrant;
import net.minecraft.sound.SoundEvent;
import org.chemthunder.acropolis.core.Acropolis;

/**
 * @author Chemthunder
 */
public interface AcropolisSoundEvents {
    SoundEventRegistrant plugin = new SoundEventRegistrant(Acropolis.MOD_ID);

    SoundEvent SIGHT = plugin.register("entity.sight");
    SoundEvent MERCY_START = plugin.register("event.mercy_start");

    SoundEvent FLEUR_PLUSH = plugin.register("block.fleur_plush");
    SoundEvent MEDE_PLUSH = plugin.register("block.mede_plush");
    SoundEvent RYN_PLUSH = plugin.register("block.ryn_plush");

    SoundEvent RAPTURE_HIT = plugin.register("item.rapture_hit");

    static void init() {}
}
