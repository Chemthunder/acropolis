package org.chemthunder.acropolis.core.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class SwarmEntityModel extends EntityModel<EntityRenderState> {
    private final ModelPart bone;

    public SwarmEntityModel(ModelPart root) {
        super(root);
        this.bone = root.getChild("bone");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild(
                "bone",
                ModelPartBuilder.create()
                        .uv(-16, 0)
                        .cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)),
                ModelTransform.origin(0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 16, 16);
    }
}
