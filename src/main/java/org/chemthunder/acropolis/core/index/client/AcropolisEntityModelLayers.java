package org.chemthunder.acropolis.core.index.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.entity.model.MeteorEntityModel;
import org.chemthunder.acropolis.core.client.entity.model.PestilenceEntityModel;
import org.chemthunder.acropolis.core.client.entity.model.SwarmEntityModel;
import org.chemthunder.acropolis.core.client.model.PlaneModel;

/**
 * @author Chemthunder
 */
public interface AcropolisEntityModelLayers {
    EntityModelLayer PLANE = register("plane");
    EntityModelLayer PESTILENCE = register("pestilence");
    EntityModelLayer SWARM = register("swarm");
    EntityModelLayer METEOR = register("meteor");

    private static EntityModelLayer register(String s) {
        return new EntityModelLayer(Acropolis.id(s), "bone");
    }

    static void clinit() {
        EntityModelLayerRegistry.registerModelLayer(PLANE, PlaneModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PESTILENCE, PestilenceEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SWARM, SwarmEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(METEOR, MeteorEntityModel::getTexturedModelData);
    }
}
