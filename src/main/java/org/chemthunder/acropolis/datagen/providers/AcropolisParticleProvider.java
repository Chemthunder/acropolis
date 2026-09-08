package org.chemthunder.acropolis.datagen.providers;

import net.acoyt.acornlib.data.provider.resources.AcornParticleGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.index.AcropolisParticleTypes;

/**
 * @author Chemthunder
 */
public class AcropolisParticleProvider extends AcornParticleGen {
    public AcropolisParticleProvider(FabricDataOutput output) {
        super(output);
    }

    public void generate(ParticleDataConsumer consumer) {
        consumer.accept(AcropolisParticleTypes.BEAM_ASS_SHIT, Acropolis.id("particle/beam_ass_shit.png"));
    }
}
