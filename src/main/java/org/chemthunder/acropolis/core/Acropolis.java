package org.chemthunder.acropolis.core;

import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.event.StareDamageEvent;
import org.chemthunder.acropolis.core.index.AcropolisComponentTypes;
import org.chemthunder.acropolis.core.index.AcropolisEntityTypes;
import org.chemthunder.acropolis.core.index.AcropolisItems;
import org.chemthunder.acropolis.core.networking.AcropolisNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Acropolis implements ModInitializer {
	public static final String MOD_ID = "acropolis";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
        AcropolisItems.init();
        AcropolisEntityTypes.init();
        AcropolisComponentTypes.init();

        AcropolisNetworking.register();
        AcropolisNetworking.c2s();

        ServerTickEvents.START_WORLD_TICK.register(new StareDamageEvent());

        ALib.registerModMenu(MOD_ID, 0xFFac61d0);

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
