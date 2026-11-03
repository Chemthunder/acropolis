package org.chemthunder.acropolis.core.client.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import org.chemthunder.acropolis.core.client.entity.state.MeteorEntityRenderState;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class MeteorEntityModel extends EntityModel<MeteorEntityRenderState> {
	private final ModelPart bb_main;

	public MeteorEntityModel(ModelPart root) {
		super(root);
		this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
}