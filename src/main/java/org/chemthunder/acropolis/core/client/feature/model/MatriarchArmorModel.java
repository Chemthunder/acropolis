package org.chemthunder.acropolis.core.client.feature.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"unused"})
public class MatriarchArmorModel extends Model<BipedEntityRenderState> {
	public final ModelPart Waist;
	public final ModelPart Head;
	public final ModelPart RightArm;
	public final ModelPart LeftArm;
	public final ModelPart Body;
	public final ModelPart LeftLeg;
	public final ModelPart RightLeg;
	
	public MatriarchArmorModel(ModelPart root) {
		super(root, RenderLayers::entityCutout);
		this.Waist = root.getChild("Waist");
		this.Head = this.Waist.getChild("Head");
		this.RightArm = this.Waist.getChild("RightArm");
		this.LeftArm = this.Waist.getChild("LeftArm");
		this.Body = root.getChild("Body");
		this.LeftLeg = root.getChild("LeftLeg");
		this.RightLeg = root.getChild("RightLeg");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Waist = modelPartData.addChild("Waist", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 12.0F, 0.0F));

		ModelPartData Head = Waist.addChild("Head", ModelPartBuilder.create().uv(28, 16).cuboid(-4.5F, -3.5F, -2.5F, 9.0F, 4.0F, 7.0F, new Dilation(0.01F))
		.uv(56, 0).cuboid(2.5F, -13.5F, 1.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F))
		.uv(56, 0).mirrored().cuboid(-10.5F, -13.5F, 1.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(0.0F, -12.0F, 0.0F));

		ModelPartData RightArm = Waist.addChild("RightArm", ModelPartBuilder.create().uv(32, 8).cuboid(-4.0F, 7.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 34).cuboid(-3.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 52).cuboid(-8.5F, -7.5F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, -10.0F, 0.0F));

		ModelPartData LeftArm = Waist.addChild("LeftArm", ModelPartBuilder.create().uv(28, 27).cuboid(-1.5F, -2.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F))
		.uv(32, 0).cuboid(-2.0F, 7.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 52).mirrored().cuboid(0.5F, -7.5F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(5.0F, -10.0F, 0.0F));

		ModelPartData Body = modelPartData.addChild("Body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.5F, -24.5F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(0.01F))
		.uv(48, 27).cuboid(0.0F, -30.0F, 2.5F, 0.0F, 11.0F, 7.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-8.0F, -40.0F, 8.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData LeftLeg = modelPartData.addChild("LeftLeg", ModelPartBuilder.create().uv(40, 45).cuboid(-2.5F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(2.0F, 12.0F, 0.0F));

		ModelPartData cube_r1 = LeftLeg.addChild("cube_r1", ModelPartBuilder.create().uv(56, 0).cuboid(-1.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 9.5F, 2.5F, 0.0F, -0.7854F, 0.0F));

		ModelPartData RightLeg = modelPartData.addChild("RightLeg", ModelPartBuilder.create().uv(20, 45).cuboid(-2.5F, -0.5F, -2.5F, 5.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.origin(-2.0F, 12.0F, 0.0F));

		ModelPartData cube_r2 = RightLeg.addChild("cube_r2", ModelPartBuilder.create().uv(56, 0).cuboid(-1.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 9.5F, 2.5F, 0.0F, -2.3562F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
}