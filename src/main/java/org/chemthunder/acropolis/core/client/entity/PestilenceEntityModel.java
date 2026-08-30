package org.chemthunder.acropolis.core.client.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.VexEntityModel;
import net.minecraft.util.math.Easing;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
@SuppressWarnings({"unused", "FieldCanBeLocal", "SpellCheckingInspection"})
public class PestilenceEntityModel extends EntityModel<PestilenceEntityRenderState> {
	private final ModelPart group;
	private final ModelPart head;
	private final ModelPart wings;
	private final ModelPart leftwing;
	private final ModelPart rightwing;

	private final Animation hoverAnimation;
	
	public PestilenceEntityModel(ModelPart root) {
		super(root, RenderLayers::entityCutout);
		this.group = root.getChild("group");
		this.head = this.group.getChild("head");
		this.wings = this.group.getChild("wings");
		this.leftwing = this.wings.getChild("leftwing");
		this.rightwing = this.wings.getChild("rightwing");

		this.hoverAnimation = PestilenceEntityAnimations.Hover.createAnimation(root);
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData group = modelPartData.addChild("group", ModelPartBuilder.create().uv(28, 0).cuboid(-3.0F, -7.0F, -2.0F, 6.0F, 7.0F, 4.0F, new Dilation(0.0F))
		.uv(22, 29).cuboid(-3.0F, 0.0F, -2.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 18.0F, 0.0F));

		ModelPartData head = group.addChild("head", ModelPartBuilder.create().uv(22, 15).cuboid(-3.5F, -6.0F, -3.5F, 7.0F, 7.0F, 7.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-3.5F, -6.0F, -3.5F, 7.0F, 7.0F, 7.0F, new Dilation(0.25F)), ModelTransform.origin(0.0F, -8.0F, 0.0F));

		ModelPartData wings = group.addChild("wings", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 2.0F));

		ModelPartData leftwing = wings.addChild("leftwing", ModelPartBuilder.create().uv(0, 15).cuboid(0.0F, -6.0F, 0.0F, 0.0F, 9.0F, 11.0F, new Dilation(0.0F)), ModelTransform.origin(1.5F, -1.0F, 0.0F));

		ModelPartData rightwing = wings.addChild("rightwing", ModelPartBuilder.create().uv(0, 15).mirrored().cuboid(0.0F, -6.0F, 0.0F, 0.0F, 9.0F, 11.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.origin(-1.5F, -1.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(PestilenceEntityRenderState state) {
		super.setAngles(state);

		this.hoverAnimation.apply(state.HOVER, state.age);

		this.leftwing.yaw = Easing.inOutExpo(60);
		this.rightwing.yaw = -this.leftwing.yaw;
	}
}