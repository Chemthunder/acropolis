package org.chemthunder.acropolis.core.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.api.EntityAssembler;
import org.chemthunder.acropolis.core.cca.world.MercyComponent;
import org.chemthunder.acropolis.core.index.ASoundEvents;

/**
 * @author Chemthunder
 */
public class ExaltedIdolItem extends Item {
    public ExaltedIdolItem(Settings settings) {
        super(settings);
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        World world = entity.getEntityWorld();
        MercyComponent mercy = MercyComponent.KEY.get(world);
        Vec3d pos = entity.getEntityPos();
        BlockPos blockPos = entity.getBlockPos();

        if (!mercy.isActive()) {
            world.playSound(null, blockPos, ASoundEvents.MERCY_START, SoundCategory.MASTER, 1, 1);

            EntityAssembler.create(world, EntityType.LIGHTNING_BOLT)
                    .build((lightningEntity, world1) -> {
                        lightningEntity.setPosition(pos);
                        lightningEntity.shouldRender(world1.getWorldBorder().getSize());
                    })
                    .spawn();

            mercy.setCountdown(MercyComponent.COUNTDOWN);
            mercy.setTicks(0);
        } else {
            mercy.setActive(false);
        }
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFFac61d0);
    }
}
