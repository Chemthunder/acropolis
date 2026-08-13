package org.chemthunder.acropolis.core.cca;

import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

/**
 * @author Chemthunder
 */
public class AcropolisCCA implements WorldComponentInitializer {
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry module) {
        module.register(GazeComponent.KEY, GazeComponent::new);
    }
}
