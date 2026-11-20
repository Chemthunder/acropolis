package org.chemthunder.acropolis.core.client.event.entities;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.AcropolisClient;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.client.api.Nitro;
import org.chemthunder.acropolis.core.utilities.Skyfall;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class IvRenderEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        World world = client.world;
        if (world == null) return;

        SkyfallWorldComponent sk = Skyfall.ofWorld(world);

        MatrixStack matrix = context.matrices();
        VertexConsumerProvider provider = context.consumers();

        Vec3d camPos = client.gameRenderer.getCamera().getCameraPos();
        float scale = sk.getInnovatorSize();

        Vec3d pos = sk.getPos();

        if (scale > 0.0F && pos != null) {
            final float scaleMult = 50;
            final float ringOutwardPush = 8.66F;

            Vec3d origin = pos.subtract(camPos).add(0, 110, 0);

            PlayerEntity player = sk.getInnovatorTarget();

            if (player != null && !player.isSpectator()) {
                Vec3d pPos = player.getEntityPos().subtract(camPos).add(0, 0.94F, 0);

                matrix.push(); // chem chains

                matrix.translate(pPos);

                matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(25));

                matrix.push();

                matrix.scale(1F, 0.01F, 1F);

                matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45));

                matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.age + MiscUtils.getTickDelta()) / 2));

                Nitro.texSphereAtPos(
                        matrix,
                        provider.getBuffer(RenderLayers.entityAlpha(Acropolis.id("textures/render/innovator_ring.png"))),
                        0, 0, 0,
                        (EasingType.OUT_EXPO.apply(scale) * 1.2F),
                        50,
                        0
                );

                matrix.pop();

                matrix.pop();

                matrix.push(); // chem chains

                matrix.translate(pPos);

                matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-25));

                matrix.push();

                matrix.scale(1F, 0.01F, 1F);

                matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45));

                matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.age + MiscUtils.getTickDelta()) / 2));

                Nitro.texSphereAtPos(
                        matrix,
                        provider.getBuffer(RenderLayers.entityAlpha(Acropolis.id("textures/render/innovator_ring.png"))),
                        0, 0, 0,
                        (EasingType.OUT_EXPO.apply(scale) * 1.2F),
                        50,
                        0
                );

                matrix.pop();

                matrix.pop();
            }

            // innovator
            matrix.push();

            matrix.translate(origin);

            matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(25));

            matrix.push();

            matrix.scale(1F, 0.01F, 1F);

            matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45));

            matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.age + MiscUtils.getTickDelta()) / 2));

            Nitro.texSphereAtPos(
                    matrix,
                    provider.getBuffer(RenderLayers.entityAlpha(Acropolis.id("textures/render/innovator_ring.png"))),
                    0, 0, 0,
                    (EasingType.OUT_EXPO.apply(scale) * scaleMult) + ringOutwardPush,
                    50,
                    0
            );

            matrix.pop();

            matrix.pop();

            //

            matrix.push();

            matrix.translate(origin);

            matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-25));

            matrix.push();

            matrix.scale(1F, 0.01F, 1F);

            matrix.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45));

            matrix.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((AcropolisClient.age + MiscUtils.getTickDelta()) / 2));

            Nitro.texSphereAtPos(
                    matrix,
                    provider.getBuffer(RenderLayers.entityAlpha(Acropolis.id("textures/render/innovator_ring.png"))),
                    0, 0, 0,
                    (EasingType.OUT_EXPO.apply(scale) * scaleMult) + ringOutwardPush,
                    50,
                    0
            );

            matrix.pop();

            matrix.pop();

            matrix.push();

            matrix.translate(origin);

            Nitro.texSphereAtPos(
                    matrix,
                    provider.getBuffer(RenderLayers.entityTranslucentEmissive(Acropolis.id("textures/render/innovator_outer.png"))),
                    0, 0, 0,
                    -((EasingType.OUT_EXPO.apply(scale) * scaleMult) + 1),
                    50,
                    0
            );

            matrix.pop();

            matrix.push();

            matrix.translate(origin);

            Nitro.texSphereAtPos(
                    matrix,
                    provider.getBuffer(RenderLayers.entityTranslucent(Acropolis.id("textures/render/innovator_inner.png"))),
                    0, 0, 0,
                    (EasingType.OUT_EXPO.apply(scale) * scaleMult),
                    50,
                    0
            );

            matrix.pop();
        }
    }
}
