package org.chemthunder.acropolis.core.utilities;

import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;

import java.util.List;

/**
 * @author Chemthunder
 */
public abstract class Skyfall {
    public static SkyfallComponent getInstance(Entity entity) {
        return entity.getEntityWorld().getComponent(SkyfallComponent.KEY);
    }

    public static SkyfallComponent getInstance(World world) {
        return world.getComponent(SkyfallComponent.KEY);
    }

    public static SkyfallComponent getInstance(ServerCommandSource src) {
        return src.getWorld().getComponent(SkyfallComponent.KEY);
    }

    public static List<Text> getInnovatorSpeech() {
        return List.of(
                lang("LET'S TAKE A BREAK FROM THE VIOLENCE."),
                lang("I BELIEVE SOMEONE HERE IS OF NEED TO ME."),
                lang("THANK YOU FOR NOT RESISTING, ENTITIES OF THIS WORLD. IT MAKES MY TIME HERE MUCH MORE BEARABLE."),
                lang("YOUR ATTEMPTS ARE PITIFUL."),
                lang("MY TIME HERE IS OVER, AND MY GOAL IS COMPLETE."),
                lang("AND YOU..."), // crack
                lang("ARE FIT TO BE DISPOSED OF."),
                lang("DO YOU HAVE ANYTHING LEFT TO SAY FOR YOURSELF, 46?"),
                Text.literal("Chemthunder has left the game").formatted(Formatting.YELLOW),
                lang("YOU REALLY ARE AS PATHETIC AS I THOUGHT."), // end
                lang("FAREWELL, BEINGS. I AM TRULY SORRY FOR THIS INCONVENIENCE."),
                lang("CARRY ON."),
                Text.empty()
        );
    }

    public static Text lang(String input) {
        return Text.literal(input).formatted(Formatting.BOLD, Formatting.WHITE);
    }
}
