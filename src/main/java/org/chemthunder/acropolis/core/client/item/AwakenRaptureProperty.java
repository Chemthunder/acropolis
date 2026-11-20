package org.chemthunder.acropolis.core.client.item;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.skyfall.RaptureItem;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class AwakenRaptureProperty implements BooleanProperty {
    public static final MapCodec<AwakenRaptureProperty> MAP_CODEC = MapCodec.unit(new AwakenRaptureProperty());
    public static final Identifier ID = Acropolis.id("awaken_rapture");

    public MapCodec<? extends BooleanProperty> getCodec() {
        return MAP_CODEC;
    }

    public boolean test(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if (entity != null) {
            return RaptureItem.awakened(entity);
        }
        return false;
    }
}
