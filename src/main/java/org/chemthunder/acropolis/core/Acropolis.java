package org.chemthunder.acropolis.core;

import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.command.ACommand;
import org.chemthunder.acropolis.core.event.SightDamageEvent;
import org.chemthunder.acropolis.core.event.StareDamageEvent;
import org.chemthunder.acropolis.core.index.*;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.core.item.TamperedGrimoireItem;
import org.chemthunder.acropolis.core.networking.ANetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Acropolis implements ModInitializer {
    public static final String MOD_ID = "acropolis";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        ANetworking.register();
        ANetworking.c2s();

        AEntityTypes.init();
        AItems.init();
        AComponentTypes.init();
        ASoundEvents.init();
        AParticleTypes.init();
        AAttachmentTypes.init();

        ServerTickEvents.START_WORLD_TICK.register(new StareDamageEvent());
        ServerTickEvents.START_WORLD_TICK.register(new SightDamageEvent());

        BetterItemTooltipEvent.EVENT.register(new CelestialGrimoireItem.Tooltip());
        BetterItemTooltipEvent.EVENT.register(new TamperedGrimoireItem.Tooltip());

        CommandRegistrationCallback.EVENT.register(new ACommand());

        ALib.registerModData(MOD_ID,
            new ALib.ModMenuData(
                    Text.literal("Acropolis").withColor(0xFFac61d0),
                    Text.literal("ready when you are.").formatted(Formatting.ITALIC).withColor(0xFFffffff),
                    Text.literal("ready when you are.").formatted(Formatting.ITALIC).withColor(0xFFffffff)
            )
        );

        MidnightConfig.init(MOD_ID, AcropolisConfig.class);

        LOGGER.info("It's sad to say goodbye, isn't it?");
        LOGGER.info("I mean, we never want things we hold dear to end.");
        LOGGER.info("It's only human to wish it lasted forever.");
        LOGGER.info("But I think that it's better for something to end on a high note,");
        LOGGER.info("Then suffer in prolonged life, don't you think?");
        LOGGER.info("Let's end this. On our high note.");
        LOGGER.info("Ready when you are.");
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
