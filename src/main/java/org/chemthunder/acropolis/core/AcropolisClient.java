package org.chemthunder.acropolis.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.client.item.AwakenRaptureProperty;
import org.chemthunder.acropolis.core.client.item.FatalOmenProperty;
import org.chemthunder.acropolis.core.client.skyfall.ShoutEvents;
import org.chemthunder.acropolis.core.client.skyfall.SightEvents;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipComponent;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipData;
import org.chemthunder.acropolis.core.event.client.BorderEvents;
import org.chemthunder.acropolis.core.event.client.FlashEvents;
import org.chemthunder.acropolis.core.event.client.MercyHudEvents;
import org.chemthunder.acropolis.core.event.client.PurpleVignetteEvents;
import org.chemthunder.acropolis.core.event.client.entities.GazeRenderEvent;
import org.chemthunder.acropolis.core.event.client.entities.IvRenderEvent;
import org.chemthunder.acropolis.core.event.client.entities.SightRenderEvent;
import org.chemthunder.acropolis.core.event.client.entities.SkyfallRenderEvent;
import org.chemthunder.acropolis.core.index.ABlockEntityTypes;
import org.chemthunder.acropolis.core.index.ABlocks;
import org.chemthunder.acropolis.core.index.AEntityTypes;
import org.chemthunder.acropolis.core.index.AParticleTypes;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;
import org.chemthunder.acropolis.core.index.client.ARenderLayers;
import org.chemthunder.acropolis.core.index.client.ARenderPipelines;
import org.chemthunder.acropolis.core.networking.ANetworking;
import org.chemthunder.acropolis.core.utilities.AKeybinds;
import org.chemthunder.acropolis.mixin.client.access.GameRendererAccessor;

import java.util.Objects;

/**
 * @author Chemthunder
 */
public class AcropolisClient implements ClientModInitializer {
    public static int AGE = 0;

    public void onInitializeClient() {
        ARenderPipelines.clinit();
        ARenderLayers.clinit();

        AEntityModelLayers.clinit();
        AEntityTypes.clinit();
        ABlocks.clinit();
        ABlockEntityTypes.clinit();
        AParticleTypes.clinit();

        ANetworking.s2c();

        AKeybinds.init();

        SelectProperties.ID_MAPPER.put(FatalOmenProperty.ID, FatalOmenProperty.TYPE);
        BooleanProperties.ID_MAPPER.put(AwakenRaptureProperty.ID, AwakenRaptureProperty.MAP_CODEC);

        WorldRenderEvents.END_MAIN.register(new GazeRenderEvent());
        WorldRenderEvents.END_MAIN.register(new SightRenderEvent());
        WorldRenderEvents.END_MAIN.register(new SkyfallRenderEvent());
        WorldRenderEvents.END_MAIN.register(new IvRenderEvent());

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

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ShoutEvents.init();
        }

        BorderEvents.init();
        SightEvents.init();

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

    public static SkyfallComponent fall() {
        return SkyfallComponent.KEY.get(MinecraftClient.getInstance().world);
    }
}
