package org.chemthunder.acropolis.core.item;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;

/**
 * @author Chemthunder
 */
public class RaptureItem extends Item {
    public RaptureItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent attributes() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.ATTACK_DAMAGE,
                        new EntityAttributeModifier(
                                BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                8.5F,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.ATTACK_SPEED,
                        new EntityAttributeModifier(
                                BASE_ATTACK_SPEED_MODIFIER_ID,
                                -2.8F,
                                EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }
}
