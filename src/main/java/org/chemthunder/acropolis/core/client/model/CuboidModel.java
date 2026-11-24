package org.chemthunder.acropolis.core.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.util.Unit;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class CuboidModel extends Model<Unit> {
	private final ModelPart bone;
	
	public CuboidModel(ModelPart root) {
		super(root, RenderLayers::entityCutout);
		this.bone = root.getChild("bone");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F))
		.uv(0, 48).cuboid(-8.0F, -16.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = bone.addChild("cube_r1", ModelPartBuilder.create().uv(0, 32).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r2 = bone.addChild("cube_r2", ModelPartBuilder.create().uv(0, 16).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, -8.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData cube_r3 = bone.addChild("cube_r3", ModelPartBuilder.create().uv(64, 0).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, 8.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData cube_r4 = bone.addChild("cube_r4", ModelPartBuilder.create().uv(0, 64).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.0F, -8.0F, 1.5708F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
}