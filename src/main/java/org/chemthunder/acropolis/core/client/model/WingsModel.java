package org.chemthunder.acropolis.core.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.client.model.animation.WingAnimations;

/**
 * @author AcoYT
 */
@SuppressWarnings("ALL")
public class WingsModel extends EntityModel<PlayerEntityRenderState> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart wings_left;
    private final ModelPart wing_top_left_1;
    private final ModelPart wing_top_left_2;
    private final ModelPart wing_bottom_left_1;
    private final ModelPart wing_bottom_left_2;
    private final ModelPart wings_right;
    private final ModelPart wing_top_right_1;
    private final ModelPart wing_top_right_2;
    private final ModelPart wing_bottom_right_1;
    private final ModelPart wing_bottom_right_2;

    private final Animation idle;

    public WingsModel(ModelPart root) {
        super(root, RenderLayers::entityCutout);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.wings_left = this.body.getChild("wings_left");
        this.wing_top_left_1 = this.wings_left.getChild("wing_top_left_1");
        this.wing_top_left_2 = this.wing_top_left_1.getChild("wing_top_left_2");
        this.wing_bottom_left_1 = this.wings_left.getChild("wing_bottom_left_1");
        this.wing_bottom_left_2 = this.wing_bottom_left_1.getChild("wing_bottom_left_2");
        this.wings_right = this.body.getChild("wings_right");
        this.wing_top_right_1 = this.wings_right.getChild("wing_top_right_1");
        this.wing_top_right_2 = this.wing_top_right_1.getChild("wing_top_right_2");
        this.wing_bottom_right_1 = this.wings_right.getChild("wing_bottom_right_1");
        this.wing_bottom_right_2 = this.wing_bottom_right_1.getChild("wing_bottom_right_2");

        this.idle = WingAnimations.IDLE.createAnimation(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -12.0F, 0.0F));

        ModelPartData wings_left = body.addChild("wings_left", ModelPartBuilder.create(), ModelTransform.origin(2.0F, -9.0F, 2.0F));

        ModelPartData wing_top_left_1 = wings_left.addChild("wing_top_left_1", ModelPartBuilder.create().uv(10, 47).cuboid(0.0F, -11.0F, 0.0F, 7.0F, 18.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        ModelPartData wing_top_left_2 = wing_top_left_1.addChild("wing_top_left_2", ModelPartBuilder.create().uv(25, 0).cuboid(0.0F, -11.0F, 0.0F, 23.0F, 18.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(7.0F, 0.0F, 0.0F));

        ModelPartData wing_bottom_left_1 = wings_left.addChild("wing_bottom_left_1", ModelPartBuilder.create().uv(83, 20).cuboid(0.0F, -4.0F, 0.0F, 8.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.0F, -0.8727F, 0.0F));

        ModelPartData wing_bottom_left_2 = wing_bottom_left_1.addChild("wing_bottom_left_2", ModelPartBuilder.create().uv(19, 74).cuboid(0.0F, -7.0F, 0.0F, 23.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(8.0F, 3.0F, 0.0F));

        ModelPartData wings_right = body.addChild("wings_right", ModelPartBuilder.create(), ModelTransform.origin(-2.0F, -9.0F, 2.0F));

        ModelPartData wing_top_right_1 = wings_right.addChild("wing_top_right_1", ModelPartBuilder.create().uv(3, 47).cuboid(-7.0F, -11.0F, 0.0F, 7.0F, 18.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        ModelPartData wing_top_right_2 = wing_top_right_1.addChild("wing_top_right_2", ModelPartBuilder.create().uv(2, 0).cuboid(-23.0F, -11.0F, 0.0F, 23.0F, 18.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-7.0F, 0.0F, 0.0F));

        ModelPartData wing_bottom_right_1 = wings_right.addChild("wing_bottom_right_1", ModelPartBuilder.create().uv(91, 20).cuboid(-8.0F, -4.0F, 0.0F, 8.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.0F, 0.8727F, 0.0F));

        ModelPartData wing_bottom_right_2 = wing_bottom_right_1.addChild("wing_bottom_right_2", ModelPartBuilder.create().uv(42, 74).cuboid(-23.0F, -7.0F, 0.0F, 23.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.origin(-8.0F, 3.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    public void setAngles(PlayerEntityRenderState state) {
        super.setAngles(state);
        this.idle.apply(state.getData(AcropolisClient.WING_STATE), state.age);

        root.traverse().forEach(ModelPart::resetTransform);
    }
}
