package org.chemthunder.acropolis.core.event.client.entities;

import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.SightComponent;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.client.api.Nitro;

/**
 * @author Chemthunder
 */
public class SkyfallRenderEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext worldRenderContext) {
        MatrixStack stack = worldRenderContext.matrices();
        VertexConsumerProvider consumers = worldRenderContext.consumers();
        OrderedRenderCommandQueue queue = worldRenderContext.commandQueue();
        float delta = MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks();

        Camera camera = worldRenderContext.gameRenderer().getCamera();
        World world = MinecraftClient.getInstance().world;

        if (world != null) {
            SkyfallComponent fall = SkyfallComponent.KEY.get(world);

            if (fall.getBoxTarget() != null) {
                float x = (float) (fall.getBoxTarget().x - camera.getCameraPos().x);
                float y = (float) (fall.getBoxTarget().y - camera.getCameraPos().y);
                float z = (float) (fall.getBoxTarget().z - camera.getCameraPos().z);

                stack.push();

                Nitro.solidColCubeAtPos(
                        stack,
                        consumers.getBuffer(RenderLayers.entityTranslucent(Acropolis.id("textures/gui/sprites/flash.png"))),
                        ColorHelper.withAlpha(EasingType.OUT_EXPO.apply(fall.getBoxLerpUp()), 0xFFffffff),
                        x,
                        y,
                        z,
                        (EasingType.OUT_EXPO.apply(fall.getBoxLerp())) * 14
                );

                stack.pop();
            }
        }
    }
}
