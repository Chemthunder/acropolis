package org.chemthunder.acropolis.core.index;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.model.PlaneModel;
import org.chemthunder.acropolis.core.client.model.WingsModel;

/**
 * @author Chemthunder
 */
public interface AcropolisEntityModelLayers {
    EntityModelLayer PLANE = register("plane");
    EntityModelLayer WINGS = register("wings");

    private static EntityModelLayer register(String s) {
        return new EntityModelLayer(Acropolis.id(s), "bone");
    }

    static void clinit() {
        EntityModelLayerRegistry.registerModelLayer(PLANE, PlaneModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(WINGS, WingsModel::getTexturedModelData);
    }
}
