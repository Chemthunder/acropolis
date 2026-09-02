package org.chemthunder.acropolis.core.client.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.index.AcropolisComponentTypes;
import org.chemthunder.acropolis.core.item.component.OmenComponent;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

/**
 * @author Chemthunder
 */
public class OmenProperty implements BooleanProperty {
    public static final MapCodec<OmenProperty> CODEC = MapCodec.unit(OmenProperty::new);
    public static final Identifier ID = Acropolis.id("omen");

    public MapCodec<? extends BooleanProperty> getCodec() {
        return CODEC;
    }

    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return stack.getOrDefault(AcropolisComponentTypes.OMEN, new OmenComponent(new ArrayList<>(), true)).closed();
    }
}
