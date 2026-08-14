package org.chemthunder.acropolis.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.event.StareDamageEvent;
import org.chemthunder.acropolis.core.index.AcropolisEntityTypes;
import org.chemthunder.acropolis.core.index.AcropolisItems;
import org.chemthunder.acropolis.core.networking.AcropolisNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Acropolis implements ModInitializer {
	public static final String MOD_ID = "acropolis";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
        AcropolisNetworking.register();
        AcropolisNetworking.c2s();

        AcropolisEntityTypes.init();
        AcropolisItems.init();

        ServerTickEvents.START_WORLD_TICK.register(new StareDamageEvent());

		LOGGER.info("Acropolis'ing");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
