package org.chemthunder.acropolis.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.chemthunder.acropolis.core.utilities.PlayerData;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @WrapMethod(method = "getPlayerListName")
    private Text acropolis$customName(Operation<Text> original) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        if (PlayerData.sanctum(player)) {
            return Text.literal("Sanctumkeeper.").formatted(Formatting.OBFUSCATED).withColor(0xFFac61d0);
        }
        return original.call();
    }
}
