package org.chemthunder.acropolis.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.entity.ImagesComponent;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.client.item.FatalOmenProperty;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipComponent;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipData;
import org.chemthunder.acropolis.core.event.client.FlashEvents;
import org.chemthunder.acropolis.core.event.client.MercyHudEvents;
import org.chemthunder.acropolis.core.event.client.PurpleVignetteEvents;
import org.chemthunder.acropolis.core.event.client.entities.GazeRenderEvent;
import org.chemthunder.acropolis.core.event.client.entities.SightRenderEvent;
import org.chemthunder.acropolis.core.index.*;
import org.chemthunder.acropolis.core.index.client.AcropolisRenderLayers;
import org.chemthunder.acropolis.core.index.client.AcropolisRenderPipelines;
import org.chemthunder.acropolis.core.networking.AcropolisNetworking;
import org.chemthunder.acropolis.mixin.client.access.GameRendererAccessor;

import java.util.List;
import java.util.Objects;

/**
 * @author Chemthunder
 */
public class AcropolisClient implements ClientModInitializer {
    public static int AGE = 0;

    public static final RenderStateDataKey<List<ImagesComponent.Image>> AFTER_IMAGES = RenderStateDataKey.create();

    public void onInitializeClient() {
        AcropolisRenderPipelines.clinit();
        AcropolisRenderLayers.clinit();

        AcropolisEntityModelLayers.clinit();
        AcropolisEntityTypes.clinit();
        AcropolisBlocks.clinit();
        AcropolisBlockEntityTypes.clinit();
        AcropolisParticleTypes.clinit();

        AcropolisNetworking.s2c();

        SelectProperties.ID_MAPPER.put(FatalOmenProperty.ID, FatalOmenProperty.TYPE);

        WorldRenderEvents.END_MAIN.register(new GazeRenderEvent());
        WorldRenderEvents.END_MAIN.register(new SightRenderEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> AGE++);

        ClientTickEvents.START_CLIENT_TICK.register(new PurpleVignetteEvents.Ticker());
        ClientTickEvents.START_CLIENT_TICK.register(new FlashEvents.Ticker());
        ClientTickEvents.START_CLIENT_TICK.register(new MercyHudEvents.Ticker());

        TooltipComponentCallback.EVENT.register(tooltipData -> {
            if (tooltipData instanceof OmenTooltipData(ItemStack self)) {
                return new OmenTooltipComponent(self);
            }
            return null;
        });

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

    public static boolean fallActive() {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            return SkyfallComponent.KEY.get(world).isActive();
        }
        return false;
    }
}
