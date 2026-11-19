package org.chemthunder.acropolis.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @WrapMethod(method = "hasOutline")
    private boolean acropolis$outline(Entity entity, Operation<Boolean> original) {
        return original.call(entity) || Skyfall.shouldHaveOutline(entity);
    }
}
