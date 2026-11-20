package org.chemthunder.acropolis.core.client.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.index.AComponentTypes;
import org.chemthunder.acropolis.core.item.component.OmenComponent;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class FatalOmenProperty implements SelectProperty<String> {
    public static final MapCodec<FatalOmenProperty> CODEC = MapCodec.unit(FatalOmenProperty::new);
    public static final Identifier ID = Acropolis.id("fatal_omen");

    public static final Type<FatalOmenProperty, String> TYPE = Type.create(
            CODEC,
            Codec.STRING
    );

    public @Nullable String getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        OmenComponent omen = stack.get(AComponentTypes.OMEN);

        if (omen != null) {
            if (!omen.finalized()) {
                return "omen_" + (omen.closed() ? "closed" : "open");
            } else {
                return "omen_finalized";
            }
        }

        return "omen_closed";
    }

    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    public Type<? extends SelectProperty<String>, String> getType() {
        return TYPE;
    }
}
