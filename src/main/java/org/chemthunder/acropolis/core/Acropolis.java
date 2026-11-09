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

    public static final boolean PLAYTEST = false;

    public void onInitialize() {
        ANetworking.register();
        ANetworking.c2s();

        AEntityTypes.init();
        AItems.init();
        AComponentTypes.init();
        ASoundEvents.init();
        ABlocks.init();
        ABlockEntityTypes.init();
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
                    Text.literal("MERCY").formatted(Formatting.OBFUSCATED).withColor(0xFF69338f),
                    Text.literal("THEN FELL THE HEAVENS, FOR THEN THE DAWNS SHALL RISE.").formatted(Formatting.OBFUSCATED).withColor(0xFF69338f)
            )
        );

        MidnightConfig.init(MOD_ID, AcropolisConfig.class);

        LOGGER.info("Gaze into the stars !!!~");
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
