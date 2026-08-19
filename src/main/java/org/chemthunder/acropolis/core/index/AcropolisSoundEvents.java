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

    SoundEvent FLEUR_PLUSH = plugin.register("block.fleur_plush");
    SoundEvent MEDE_PLUSH = plugin.register("block.mede_plush");

    static void init() {}
}
