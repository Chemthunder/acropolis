package org.chemthunder.acropolis.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.world.ClientWorld;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.client.event.*;
import org.chemthunder.acropolis.core.client.event.entities.GazeRenderEvent;
import org.chemthunder.acropolis.core.client.event.entities.IvRenderEvent;
import org.chemthunder.acropolis.core.client.event.entities.PermakillBoxRenderEvent;
import org.chemthunder.acropolis.core.client.event.entities.SightRenderEvent;
import org.chemthunder.acropolis.core.client.item.AwakenRaptureProperty;
import org.chemthunder.acropolis.core.client.item.FatalOmenProperty;
import org.chemthunder.acropolis.core.client.skyfall.ShoutEvents;
import org.chemthunder.acropolis.core.client.skyfall.SightEvents;
import org.chemthunder.acropolis.core.index.AEntityTypes;
import org.chemthunder.acropolis.core.index.AParticleTypes;
import org.chemthunder.acropolis.core.index.client.AEntityModelLayers;
import org.chemthunder.acropolis.core.networking.ANetworking;
import org.chemthunder.acropolis.core.utilities.AKeybinds;
import org.chemthunder.acropolis.core.utilities.Skyfall;

/**
 * @author Chemthunder
 */
@SuppressWarnings("DataFlowIssue")
@Environment(EnvType.CLIENT)
public class AcropolisClient implements ClientModInitializer {
    public static int age = 0;

    public static final RenderStateDataKey<Boolean> HAS_CHAINS = RenderStateDataKey.create();

    public void onInitializeClient() {
        ANetworking.s2c();

        AKeybinds.init();

        AEntityModelLayers.clinit();
        AEntityTypes.clinit();
        AParticleTypes.clinit();

        SelectProperties.ID_MAPPER.put(FatalOmenProperty.ID, FatalOmenProperty.TYPE);
        BooleanProperties.ID_MAPPER.put(AwakenRaptureProperty.ID, AwakenRaptureProperty.MAP_CODEC);

        WorldRenderEvents.END_MAIN.register(new GazeRenderEvent());
        WorldRenderEvents.END_MAIN.register(new SightRenderEvent());
        WorldRenderEvents.END_MAIN.register(new PermakillBoxRenderEvent());
        WorldRenderEvents.END_MAIN.register(new IvRenderEvent());

        TooltipComponentCallback.EVENT.register(new OmenTooltipEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> age++);

        ClientTickEvents.START_CLIENT_TICK.register(new ApplyShadersEvent());

        PurpleVignetteEvents.init();
        FlashEvents.init();
        MercyHudEvents.init();
        BorderEvents.init();
        SightEvents.init();
        ShoutEvents.init();
    }

    public static boolean mercyActive() {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            return MercyComponent.KEY.get(world).isActive();
        }
        return false;
    }

    public static boolean skyfallActive() {
        ClientWorld world = MinecraftClient.getInstance().world;

        if (world != null) {
            return Skyfall.ofWorld(world).active;
        }
        return false;
    }

    public static SkyfallWorldComponent fallIns() {
        return Skyfall.ofWorld(MinecraftClient.getInstance().world);
    }
}
