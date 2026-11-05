package org.chemthunder.acropolis.core.skyfall;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.networking.c2s.IteratePowerPayload;
import org.chemthunder.acropolis.core.networking.c2s.UsePowerPayload;

/**
 * @author Chemthunder
 */
public class AKeybindings {
    public static final KeyBinding.Category CATEGORY = new KeyBinding.Category(Acropolis.id(Acropolis.MOD_ID));

    public static KeyBinding ITERATE_NEXT;
    public static KeyBinding ITERATE_LAST;
    public static KeyBinding USE_POWER;

    public static void init() {
        ITERATE_NEXT = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.acropolis.iterate_next",
                InputUtil.GLFW_KEY_RIGHT,
                CATEGORY
        ));

        ITERATE_LAST = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.acropolis.iterate_last",
                InputUtil.GLFW_KEY_LEFT,
                CATEGORY
        ));

        USE_POWER = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.acropolis.activate",
                InputUtil.GLFW_KEY_C,
                CATEGORY
        ));

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (ITERATE_NEXT.isPressed()) {
                ITERATE_NEXT.setPressed(false);
                ClientPlayNetworking.send(new IteratePowerPayload(true));
            }

            if (ITERATE_LAST.isPressed()) {
                ITERATE_LAST.setPressed(false);
                ClientPlayNetworking.send(new IteratePowerPayload(false));
            }

            if (USE_POWER.isPressed()) {
                USE_POWER.setPressed(false);
                ClientPlayNetworking.send(new UsePowerPayload());
            }
        });
    }
}
