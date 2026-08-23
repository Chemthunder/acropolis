package org.chemthunder.acropolis.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.event.client.FlashEvents;
import org.chemthunder.acropolis.core.event.client.MercyHudEvents;
import org.chemthunder.acropolis.core.event.client.PurpleVignetteEvents;
import org.chemthunder.acropolis.core.event.client.entities.GazeRenderEvent;
import org.chemthunder.acropolis.core.event.client.entities.SightRenderEvent;
import org.chemthunder.acropolis.core.index.*;
import org.chemthunder.acropolis.mixin.client.access.GameRendererAccessor;

import java.util.Objects;

/**
 * @author Chemthunder
 */
public class AcropolisClient implements ClientModInitializer {
    public static int AGE = 0;

    public void onInitializeClient() {
        AcropolisEntityModelLayers.clinit();
        AcropolisEntityTypes.clinit();
        AcropolisBlocks.clinit();
        AcropolisBlockEntities.clinit();

        AcropolisNetworking.s2c();

        WorldRenderEvents.END_MAIN.register(new GazeRenderEvent());
        WorldRenderEvents.END_MAIN.register(new SightRenderEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> AGE++);

        ClientTickEvents.START_CLIENT_TICK.register(new PurpleVignetteEvents.Ticker());
        ClientTickEvents.START_CLIENT_TICK.register(new FlashEvents.Ticker());
        ClientTickEvents.START_CLIENT_TICK.register(new MercyHudEvents.Ticker());

        HudElementRegistry.addFirst(Acropolis.id("vignette"), new PurpleVignetteEvents.Render());
        HudElementRegistry.addFirst(Acropolis.id("flash"), new FlashEvents.Render());
        HudElementRegistry.addFirst(Acropolis.id("mercy"), new MercyHudEvents.Render());

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
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
        });
    }

    public static boolean mercyActive() {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            return MercyComponent.KEY.get(world).isActive();
        }
        return false;
    }
}
