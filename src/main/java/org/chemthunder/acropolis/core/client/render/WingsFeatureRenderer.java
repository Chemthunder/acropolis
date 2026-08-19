package org.chemthunder.acropolis.core.client.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.model.WingsModel;
import org.chemthunder.acropolis.core.index.AcropolisEntityModelLayers;
import org.jetbrains.annotations.NotNull;

/**
 * @author Chemthunder
 */
public class WingsFeatureRenderer<T extends PlayerEntityRenderState> extends FeatureRenderer<T, PlayerEntityModel> {
    private final WingsModel wingsModel;

    public WingsFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel> context, EntityRendererFactory.@NotNull Context ctx) {
        super(context);
        this.wingsModel = new WingsModel(ctx.getPart(AcropolisEntityModelLayers.WINGS));
    }

    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, T state, float limbAngle, float limbDistance) {
        queue.submitCustom(matrices, RenderLayers.entityCutout(Acropolis.id("textures/entity/wings.png")), (matricesEntry, vertexConsumer) -> {
            create(matrices, vertexConsumer, light, state, limbAngle, limbDistance);
        });
    }

    private void create(MatrixStack stack, VertexConsumer consumer, int light, T state, float limbAngle, float limbDistance) {
        stack.push();

        wingsModel.setAngles(state);

        wingsModel.render(
                stack,
                consumer,
                light,
                OverlayTexture.DEFAULT_UV
        );

        stack.pop();
    }
}
