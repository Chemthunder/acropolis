package org.chemthunder.acropolis.core.item;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.world.SkyfallComponent;
import org.chemthunder.acropolis.core.client.tooltip.OmenTooltipData;
import org.chemthunder.acropolis.core.index.AComponentTypes;
import org.chemthunder.acropolis.core.item.component.OmenComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author Chemthunder
 */
@SuppressWarnings({"SpellCheckingInspection", "ConstantValue"})
public class FatalOmenItem extends Item {
    public static final int REQUIRED_AMNT = 9;

    public FatalOmenItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        OmenComponent omen = stack.get(AComponentTypes.OMEN);

        if (omen != null) {
            if (user.isSneaking()) {
                Entity entity = getRaycastEntity(user);

                if (entity != null) {
                    if (entity instanceof PlayerEntity) {
                        if (!omen.finalized()) {
                            if (!omen.containedNames().contains(entity.getName().getString())) {
                                if (!user.getItemCooldownManager().isCoolingDown(stack)) {
                                    user.setCurrentHand(hand);
                                    stack.set(AComponentTypes.OMEN, new OmenComponent(omen.containedNames(), false, omen.finalized()));
                                    return ActionResult.CONSUME;
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();

        OmenComponent omen = stack.get(AComponentTypes.OMEN);

        if (context.getPlayer() != null) {
            if (omen != null) {
                if (context.getPlayer().isSneaking() && omen.finalized()) {
                    SkyfallComponent fall = context.getPlayer().getEntityWorld().getComponent(SkyfallComponent.KEY);

                    fall.activate();
                    fall.setPos(context.getHitPos());

                    if (context.getWorld() instanceof ServerWorld serverWorld) {
                        serverWorld.setTimeOfDay(18000);
                    }
                }
            }
        }

        return super.useOnBlock(context);
    }

    public static Entity getRaycastEntity(LivingEntity user) {
        Vec3d pos = user.raycast(3, 0, true).getPos();
        return user.getEntityWorld().getClosestPlayer(pos.x, pos.y, pos.z, 3, (entity -> entity != user));
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        Entity entity = getRaycastEntity(user);
        int time = this.getMaxUseTime(stack, user) - remainingUseTicks;
        OmenComponent omen = stack.get(AComponentTypes.OMEN);

        if (omen != null) {
            if (user.isSneaking()) {
                if (entity == null) {
                    haltUsage(stack, user, true);
                }

                if (entity instanceof PlayerEntity target) {
                    Random random = new Random();
                    float bound = target.getWidth();

                    Vec3d spawnPos = new Vec3d(
                            target.getX() + random.nextFloat(-bound, bound),
                            (target.getY() + (target.getHeight() / 2F)) + random.nextFloat(-bound, bound),
                            target.getZ() + random.nextFloat(-bound, bound)
                    );

                    Vec3d velocity = spawnPos.subtract(new Vec3d(
                                    user.getX(),
                                    user.getY() + 1.0F,
                                    user.getZ()
                            ))
                            .normalize()
                            .negate()
                            .multiply(0.4F);

                    for (int i = 0; i < random.nextInt(3, 16); i++) {
                        world.addImportantParticleClient(
                                ParticleTypes.END_ROD,
                                spawnPos.x,
                                spawnPos.y,
                                spawnPos.z,
                                velocity.x,
                                velocity.y,
                                velocity.z
                        );
                    }

                    if (time >= 80) {
                        user.stopUsingItem();

                        if (user instanceof PlayerEntity playerEntity) {
                            playerEntity.getItemCooldownManager().set(stack, 10);
                        }

                        List<String> entries = new ArrayList<>(omen.containedNames());

                        entries.add(target.getName().getString());

                        stack.set(AComponentTypes.OMEN, new OmenComponent(
                                entries,
                                true,
                                omen.finalized()
                        ));

                        OmenComponent postOmen = stack.get(AComponentTypes.OMEN);

                        if (postOmen != null) {
                            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                                if (postOmen.containedNames().size() >= REQUIRED_AMNT) {
                                    stack.set(AComponentTypes.OMEN, new OmenComponent(
                                            postOmen.containedNames(),
                                            postOmen.closed(),
                                            true
                                    ));

                                    SkyfallComponent fall = SkyfallComponent.KEY.get(world);

                                    fall.createBox(target.getEntityPos(), target);
                                }
                            }
                        }
                    }
                }
            } else {
                haltUsage(stack, user, true);
            }
        }
    }

    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int time = this.getMaxUseTime(stack, user) - remainingUseTicks;
        OmenComponent omen = stack.get(AComponentTypes.OMEN);

        if (omen != null) {
            if (time < 80) {
                haltUsage(stack, user, false);
            }
        }
        return super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public static void haltUsage(ItemStack stack, LivingEntity user, boolean stop) {
        OmenComponent omen = stack.get(AComponentTypes.OMEN);

        if (omen != null) {
            if (stop) {
                user.stopUsingItem();
            }
            if (user instanceof PlayerEntity playerEntity) {
                playerEntity.getItemCooldownManager().set(stack, 10);
                stack.set(AComponentTypes.OMEN, new OmenComponent(
                        omen.containedNames(),
                        true,
                        omen.finalized()
                ));
            }
        }
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        TooltipDisplayComponent display = stack.getOrDefault(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplayComponent.DEFAULT);

        return !display.shouldDisplay(AComponentTypes.OMEN)
                ? Optional.empty()
                : Optional.of(AComponentTypes.OMEN).map(component -> new OmenTooltipData(stack)
        );
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(0xFFac61d0);
    }
}
