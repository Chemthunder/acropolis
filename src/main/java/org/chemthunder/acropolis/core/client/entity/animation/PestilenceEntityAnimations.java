package org.chemthunder.acropolis.core.client.entity.animation;// Save this class in your mod and generate all required imports

import net.minecraft.client.render.entity.animation.AnimationDefinition;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

/**
 * Made with Blockbench 5.1.6
 * Exported for Minecraft version 1.19 or later with Yarn mappings
 * @author Author
 */
public class PestilenceEntityAnimations {
	public static final AnimationDefinition Hover = AnimationDefinition.Builder.create(3.0F).looping()
		.addBoneAnimation("group", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.76F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.1667F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(5.76F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.9167F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(5.76F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.6667F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.25F, AnimationHelper.createRotationalVector(5.76F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.4167F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(5.76F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(3.1667F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("group", new Transformation(Transformation.Targets.MOVE_ORIGIN,
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.125F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.875F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.25F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.625F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
			new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("leftwing", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(31.5072F, 20.7048F, 24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(50.021F, 51.1216F, 74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(31.5072F, 20.7048F, 24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(50.021F, 51.1216F, 74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(31.5072F, 20.7048F, 24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(50.021F, 51.1216F, 74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.25F, AnimationHelper.createRotationalVector(31.5072F, 20.7048F, 24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(50.021F, 51.1216F, 74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(31.5072F, 20.7048F, 24.1487F), Transformation.Interpolations.CUBIC)
		))
		.addBoneAnimation("rightwing", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(31.5072F, -20.7048F, -24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(50.021F, -51.1216F, -74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(31.5072F, -20.7048F, -24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(50.021F, -51.1216F, -74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(31.5072F, -20.7048F, -24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(50.021F, -51.1216F, -74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.25F, AnimationHelper.createRotationalVector(31.5072F, -20.7048F, -24.1487F), Transformation.Interpolations.CUBIC),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(50.021F, -51.1216F, -74.0662F), Transformation.Interpolations.CUBIC),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(31.5072F, -20.7048F, -24.1487F), Transformation.Interpolations.CUBIC)
		))
		.build();
}