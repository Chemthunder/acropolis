package org.chemthunder.acropolis.core;

import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.event.SightDamageEvent;
import org.chemthunder.acropolis.core.event.StareDamageEvent;
import org.chemthunder.acropolis.core.index.*;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.mixin.access.GameRendererAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Acropolis implements ModInitializer {
    public static final String MOD_ID = "acropolis";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        AcropolisNetworking.register();
        AcropolisNetworking.c2s();

        AcropolisEntityTypes.init();
        AcropolisItems.init();
        AcropolisComponentTypes.init();
        AcropolisSoundEvents.init();
        AcropolisBlocks.init();
        AcropolisItemGroups.init();
        AcropolisBlockEntities.init();

        ServerTickEvents.START_WORLD_TICK.register(new StareDamageEvent());
        ServerTickEvents.START_WORLD_TICK.register(new SightDamageEvent());

        BetterItemTooltipEvent.EVENT.register(new CelestialGrimoireItem.Tooltip());
//        BetterItemTooltipEvent.EVENT.register(new ExtolledSimulacrumItem.Tooltip());

        ALib.registerModIcon(MOD_ID, Acropolis.id("mini_icon.png"));
        ALib.registerModMenu(MOD_ID, 0xFFac61d0);

        AcropolisBlocks.PLUSHIES.forEach(plushRegistration ->
                ALib.registerPlush(
                        plushRegistration.plush(),
                        plushRegistration.squishSound(),
                        plushRegistration.descColor()
                )
        );

        MidnightConfig.init(MOD_ID, AcropolisConfig.class);

        LOGGER.info("Gaze into the stars !!!~");
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
