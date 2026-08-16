package org.chemthunder.acropolis.core;

import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.event.SightDamageEvent;
import org.chemthunder.acropolis.core.event.StareDamageEvent;
import org.chemthunder.acropolis.core.index.*;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.core.item.ExtolledSimulacrumItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Acropolis implements ModInitializer {
	public static final String MOD_ID = "acropolis";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ALib.ModMenuData DATA = new ALib.ModMenuData(
            Text.literal("Acropolis").withColor(0xFFac61d0),
            Text.literal("Stars, countless stars...").withColor(0xFF9740aa),
            Text.literal("A gaze into the infinity, and the thousands of galaxies beyond.").withColor(0xFF9740aa)
    );

	public void onInitialize() {
        AcropolisNetworking.register();
        AcropolisNetworking.c2s();

        AcropolisEntityTypes.init();
        AcropolisItems.init();
        AcropolisComponentTypes.init();
        AcropolisSoundEvents.init();

        ServerTickEvents.START_WORLD_TICK.register(new StareDamageEvent());
        ServerTickEvents.START_WORLD_TICK.register(new SightDamageEvent());

        BetterItemTooltipEvent.EVENT.register(new CelestialGrimoireItem.Tooltip());
        BetterItemTooltipEvent.EVENT.register(new ExtolledSimulacrumItem.Tooltip());

        ALib.registerModIcon(MOD_ID, Acropolis.id("mini_icon.png"));
        ALib.registerModMenu(MOD_ID, 0xFFac61d0);

		LOGGER.info("Gaze into the stars !!!~");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
