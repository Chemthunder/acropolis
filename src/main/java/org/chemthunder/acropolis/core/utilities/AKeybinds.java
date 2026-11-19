package org.chemthunder.acropolis.core.utilities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.networking.c2s.DashPayload;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public abstract class AKeybinds {
    public static final KeyBinding.Category CATEGORY = new KeyBinding.Category(Acropolis.id(Acropolis.MOD_ID));

    public static KeyBinding dash;

    public static void init() {
        dash = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.acropolis.dash",
                        InputUtil.GLFW_KEY_R,
                        CATEGORY
                )
        );

        ClientTickEvents.START_CLIENT_TICK.register((client -> {
            if (dash.isPressed()) {
                dash.setPressed(false);

                PlayerEntity player = client.player;
                if (player == null) return;

                if (player.getAttachedOrElse(AAttachmentTypes.MATRIARCH, false)) {
                    ClientPlayNetworking.send(new DashPayload(true));
                }
            }
        }));
    }
}
