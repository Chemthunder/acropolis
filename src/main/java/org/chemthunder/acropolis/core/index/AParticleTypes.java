package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.ParticleTypeRegistrant;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.particle.BeamAssShitParticleType;
import org.chemthunder.acropolis.core.client.particle.ShockwaveParticleType;

/**
 * @author Chemthunder
 */
public interface AParticleTypes {
    ParticleTypeRegistrant plugin = new ParticleTypeRegistrant(Acropolis.MOD_ID);

    SimpleParticleType BEAM_ASS_SHIT = plugin.register("beam_ass_shit", FabricParticleTypes.simple());
    SimpleParticleType SHOCKWAVE = plugin.register("shockwave", FabricParticleTypes.simple(true));

    static void init() {}

    static void clinit() {
        ParticleFactoryRegistry.getInstance().register(BEAM_ASS_SHIT, BeamAssShitParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SHOCKWAVE, ShockwaveParticleType.Factory::new);
    }
}
