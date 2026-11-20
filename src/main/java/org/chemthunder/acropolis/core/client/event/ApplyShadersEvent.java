package org.chemthunder.acropolis.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.mixin.client.access.GameRendererAccessor;

import java.util.Objects;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class ApplyShadersEvent implements ClientTickEvents.StartTick {
    public void onStartTick(MinecraftClient client) {
        GameRendererAccessor accessor = (GameRendererAccessor) client.gameRenderer;
        World world = client.world;

        if (world != null) {
            if (MercyComponent.KEY.get(world).isActive()) {
                if (!Objects.equals(accessor.PostEffectId(), Acropolis.id("mercy"))) {
                    accessor.PostEffectId(Acropolis.id("mercy"));
                    accessor.EffectActive(true);
                }
            } else {
                if (Objects.equals(accessor.PostEffectId(), Acropolis.id("mercy"))) {
                    accessor.EffectActive(false);
                }
            }
        }
    }
}
