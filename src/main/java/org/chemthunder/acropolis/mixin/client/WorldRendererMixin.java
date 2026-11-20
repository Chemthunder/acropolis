package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = WorldRenderer.class)
public abstract class WorldRendererMixin {
    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getTime()J"
            )
    )
    private long acropolis$forceNoon(ClientWorld instance, Operation<Long> original) {
        return MercyComponent.KEY.get(instance).isActive() ? 18000 : original.call(instance);
    }
}
