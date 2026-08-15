package org.chemthunder.acropolis.core.event.client;

import net.acoyt.acornlib.api.client.Easings;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Easing;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.GazeComponent;
import org.chemthunder.acropolis.core.cca.world.SightComponent;
import org.chemthunder.acropolis.core.client.Nitro;

/**
 * @author Chemthunder
 */
public class SightRenderEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext worldRenderContext) {
        MatrixStack stack = worldRenderContext.matrices();
        VertexConsumerProvider consumers = worldRenderContext.consumers();
        OrderedRenderCommandQueue queue = worldRenderContext.commandQueue();
        float delta = MinecraftClient.getInstance().getRenderTickCounter().getDynamicDeltaTicks();

        Camera camera = worldRenderContext.gameRenderer().getCamera();
        World world = MinecraftClient.getInstance().world;

        if (world != null) {
            SightComponent sight = SightComponent.KEY.get(world);

            if (sight.getPos() != null) {
                float x = (float) (sight.getPos().x - camera.getCameraPos().x);
                float y = (float) (sight.getPos().y - camera.getCameraPos().y);
                float z = (float) (sight.getPos().z - camera.getCameraPos().z);

                stack.push();

                stack.translate(x, y - 50, z);

                Nitro.skyBeamAtPos(
                        stack,
                        consumers.getBuffer(RenderLayers.entityTranslucentEmissive(Acropolis.id("textures/entity/sight.png"))),
                        0, 0, 0,
                        Easing.outExpo(sight.getBeamWidth()),
                        360,
                        0
                );

                stack.pop();
            }
        }
    }
}
