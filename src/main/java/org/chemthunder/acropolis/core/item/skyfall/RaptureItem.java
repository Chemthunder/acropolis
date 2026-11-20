package org.chemthunder.acropolis.core.item.skyfall;

import net.acoyt.acornlib.api.item.CritEffectItem;
import net.acoyt.acornlib.api.item.HitEffectsItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LazyEntityReference;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.acropolis.core.cca.skyfall.SkyfallWorldComponent;
import org.chemthunder.acropolis.core.entity.CruciDaggerEntity;
import org.chemthunder.acropolis.core.index.AAttachmentTypes;
import org.chemthunder.acropolis.core.index.AEntityTypes;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Random;

/**
 * @author Chemthunder
 */
public class RaptureItem extends Item implements HitEffectsItem, CritEffectItem {
    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0xFF0d0508, 0xFF1a151e),
            new SweepParticleEffect(0xFF605662, 0xFF3c333e)
    };

    public static final SweepParticleEffect[] AWAKENED_EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0xFF0d0508, 0xFF1a151e),
            new SweepParticleEffect(0xFF561060, 0xFFa31eb5)
    };

    public static final int SHAKE_AMNT = 1;

    public RaptureItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        return super.getName(stack).copy().withColor(awakened(stack.getHolder()) ? 0xFFe636ff : 0xFF39355c);
    }

    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);

        if (awakened(entity)) {
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, awakenedAttributes());
        } else {
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, baseAttributes());
        }
    }

    public void runHitEffects(PlayerEntity player, Entity entity) {
        ParticleUtils.spawnSweepParticles(
                awakened(player) ?
                        AWAKENED_EFFECTS[new Random().nextInt(AWAKENED_EFFECTS.length)]
                        : EFFECTS[new Random().nextInt(EFFECTS.length)],
                player
        );
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        SkyfallWorldComponent skyfall = Skyfall.ofWorld(world);

        if (!user.getAttachedOrElse(AAttachmentTypes.MATRIARCH, false)) {
            if (skyfall.isActive()) {
                user.setAttached(AAttachmentTypes.MATRIARCH, true);
                Skyfall.awakenMatriarch(user);
                return ActionResult.SUCCESS;
            }
        } else {

            Vec3d ray = user.raycast(0.6, 0, true).getPos();

            for (int i = 0; i < 3; i++) {
                CruciDaggerEntity dagger = new CruciDaggerEntity(AEntityTypes.DAGGER, world);

                Random rand = new Random();

                dagger.setVelocity(user, user.getPitch(), user.getYaw(), 0, 1.4F, 2);
                dagger.setPosition(ray.add(rand.nextFloat(-2F, 2F)));
                dagger.setAttached(AAttachmentTypes.OWNERSHIP, LazyEntityReference.of(user));

                dagger.setPitch(user.getPitch());
                dagger.setYaw(user.getYaw());

                dagger.setOwner(user);

                world.spawnEntity(dagger);
            }

            user.swingHand(hand);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.SMOKE,
                        ray.x,
                        ray.y,
                        ray.z,
                        10,
                        0,
                        0,
                        0,
                        0.025F
                );

                serverWorld.spawnParticles(
                        ParticleTypes.PORTAL,
                        ray.x,
                        ray.y,
                        ray.z,
                        10,
                        0,
                        0,
                        0,
                        0.026F
                );
            }

            ParticleUtils.spawnSweepParticles(
                    new SweepParticleEffect(0xFF0d0508, 0xFF1a151e),
                    user
            );

            user.getItemCooldownManager().set(user.getStackInHand(hand), (4 * 20));
        }
        return super.use(world, user, hand);
    }

    public void critEffect(PlayerEntity player, ItemStack stack, Entity target) {
        if (target instanceof PlayerEntity living && awakened(player)) {
            Skyfall.ofEntity(living).freeze();

            Skyfall.shout("FALL", 0xFFff0000, List.of(player, living));

            player.getEntityWorld().playSound(
                    null,
                    living.getX(),
                    living.getY(),
                    living.getZ(),
                    SoundEvents.ENTITY_ALLAY_DEATH,
                    SoundCategory.PLAYERS,
                    1,
                    0.3F
            );
        }
    }

    public static boolean awakened(Entity player) {
        return Skyfall.isMatri(player);
    }

    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    public static AttributeModifiersComponent baseAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                7.0,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(
                                BASE_ATTACK_SPEED_MODIFIER_ID,
                                -2.4,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public static AttributeModifiersComponent awakenedAttributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                9.0,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(
                                BASE_ATTACK_SPEED_MODIFIER_ID,
                                -2.2,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }
}

// Fall
// Complete hit chain of five. After a few seconds, deal 4 hearts true damage
// and pop totem if existing
