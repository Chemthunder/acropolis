package org.chemthunder.acropolis.core.cca;

import net.minecraft.client.network.ClientPlayerEntity;
import org.chemthunder.acropolis.core.cca.entity.ImagesComponent;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.cca.world.SightComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

/**
 * @author Chemthunder
 */
public class AcropolisCCA implements WorldComponentInitializer, EntityComponentInitializer {
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry module) {
        module.register(GazeComponent.KEY, GazeComponent::new);
        module.register(SightComponent.KEY, SightComponent::new);
        module.register(MercyComponent.KEY, MercyComponent::new);
        module.register(SkyfallComponent.KEY, SkyfallComponent::new);
    }

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry module) {
        module.registerFor(ClientPlayerEntity.class, ImagesComponent.KEY, ImagesComponent::new);
    }
}
