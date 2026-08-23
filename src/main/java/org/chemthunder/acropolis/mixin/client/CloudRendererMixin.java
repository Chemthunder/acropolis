package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.CloudRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * @author Chemthunder
 */
@Mixin(value = CloudRenderer.class)
public abstract class CloudRendererMixin {

    @WrapMethod(method = "renderClouds")
    private void acropolis$solidfyClouds(int color, CloudRenderMode mode, float cloudHeight, Vec3d vec3d, long l, float f, Operation<Void> original) {
        original.call(mercyActive() ? 0xFF000000 : color, mode, cloudHeight, vec3d, l, f);
    }

    @Unique
    private static boolean mercyActive() {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            return MercyComponent.KEY.get(world).isActive();
        }
        return false;
    }
}
