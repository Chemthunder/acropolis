package org.chemthunder.acropolis.core.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.util.Unit;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ChainsModel extends Model<Unit> {
	private final ModelPart bone;

	public ChainsModel(ModelPart root) {
		super(root, RenderLayers::entityCutout);
		this.bone = root.getChild("bone");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -3.0F, -6.0F, 12.0F, 3.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
}