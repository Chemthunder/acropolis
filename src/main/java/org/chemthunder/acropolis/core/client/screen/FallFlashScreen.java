package org.chemthunder.acropolis.core.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.text.Text.literal;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class FallFlashScreen extends Screen {
    protected final List<Text> texts = new ArrayList<>();

    private int index = 0;
    private int delay = 1;

    public FallFlashScreen() {
        super(Text.empty());

        for (int i = 0; i < 4; i++) {
            texts.add(literal("WAS IT WORTH IT?").append(Text.literal("                                                                 " + i).withColor(ColorHelper.withAlpha(0, 0x00))));
        }

        texts.add(literal("BOW TO YOUR CREATOR"));
        texts.add(literal("SOMETHING YOU COULD NEVER COMPREHEND"));
        texts.add(literal("CRY, FOR THE HEAVENS HAVE FORSAKEN YOU NOW"));
        texts.add(literal("CLASP YOUR HANDS, LOOK TO THE SKY"));
        texts.add(literal("BE NOT AFRAID."));
        texts.add(literal("<o>"));
        texts.add(Text.empty());
    }

    public void tick() {
        if (this.delay > 0) {
            this.delay--;
            if (this.delay <= 0) {
                this.delay = 1;
                this.index++;
                this.client.getSoundManager().play(PositionedSoundInstance.ui(SoundEvents.ENTITY_ALLAY_DEATH, 1));
            }
        }

        if (this.index >= (this.texts.size())) {
            this.close();
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        List<Text> displayed = new ArrayList<>();

        for (Text t : this.texts) {
            if (this.texts.indexOf(t) < index) {
                displayed.add(t);
            }
        }

        for (Text t : displayed) {
            int index = displayed.indexOf(t);

            context.getMatrices().pushMatrix();

            context.getMatrices().translate(
                    20,
                    20 + (index * 20)
            );

            context.getMatrices().scaleAround(
                    1.5F,
                    0,
                    (index * 16)
            );

            context.drawText(
                    MinecraftClient.getInstance().textRenderer,
                    t,
                    0, 0,
                    0xFFffffff,
                    false
            );

            context.getMatrices().popMatrix();
        }
    }
}
