package org.chemthunder.acropolis.core.event.client.entities;

import net.acoyt.acornlib.api.client.Easings;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.ExordiumComponent;
import org.chemthunder.acropolis.core.client.api.Nitro;

/**
 * @author Chemthunder
 */
public class ExordiumRenderEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext worldRenderContext) {
        MatrixStack stack = worldRenderContext.matrices();
        VertexConsumerProvider consumers = worldRenderContext.consumers();
        OrderedRenderCommandQueue queue = worldRenderContext.commandQueue();
        float delta = MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks();

        Camera camera = worldRenderContext.gameRenderer().getCamera();
        World world = MinecraftClient.getInstance().world;

        if (world != null) {
            ExordiumComponent ex = ExordiumComponent.KEY.get(world);

            if (ex.getPos() != null && ex.isActive()) {
                float x = (float) (ex.getPos().x - camera.getCameraPos().x);
                float y = (float) (ex.getPos().y - camera.getCameraPos().y);
                float z = (float) (ex.getPos().z - camera.getCameraPos().z);

                stack.push();

                stack.translate(x, y, z);

                Nitro.texSphereAtPos(
                        stack,
                        consumers.getBuffer(RenderLayers.endGateway()),
                        0, 0, 0,
                        EasingType.OUT_EXPO.apply(ex.lerped()) * 60,
                        140,
                        0.0F
                );

                stack.pop();
            }
        }
    }
}
