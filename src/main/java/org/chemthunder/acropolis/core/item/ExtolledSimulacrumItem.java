package org.chemthunder.acropolis.core.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.SightComponent;

/**
 * @author Chemthunder
 */
public class ExtolledSimulacrumItem extends Item {
    public ExtolledSimulacrumItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        HitResult raycast = user.raycast(250, 0.1F, false);
        Vec3d tail = raycast.getPos();
        SightComponent sight = SightComponent.KEY.get(world);

        sight.setTicks(0);
        sight.setBeamWidth(0.0F);
        sight.setPos(tail);

        return super.use(world, user, hand);
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFFac61d0);
    }
}
