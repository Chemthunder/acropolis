package org.chemthunder.acropolis.mixin;

import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = ServerWorld.class)
public abstract class ServerWorldMixin {

//    @WrapOperation(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerWorldProperties;setTime(J)V"))
//    private void acropolis$haltTime(ServerWorldProperties instance, long l, Operation<Void> original) {
//        if (MercyComponent.KEY.get(this).isActive()) {
//            original.call(instance, 18000L);
//        } else {
//            original.call(instance, l);
//        }
//    }
}
