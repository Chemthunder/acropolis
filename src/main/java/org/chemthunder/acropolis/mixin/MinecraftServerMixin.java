package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @WrapMethod(method = "sendTimeUpdatePackets(Lnet/minecraft/server/world/ServerWorld;)V")
    private void acropolis$haltTime(ServerWorld world, Operation<Void> original) {
        if (!MercyComponent.KEY.get(world).isActive()) {
            original.call(world);
        }
    }
}
