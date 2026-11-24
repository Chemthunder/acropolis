package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.item.CelestialGrimoireItem;
import org.chemthunder.acropolis.core.item.ExaltedIdolItem;
import org.chemthunder.acropolis.core.item.FatalOmenItem;
import org.chemthunder.acropolis.core.item.TamperedGrimoireItem;
import org.chemthunder.acropolis.core.item.component.OmenComponent;
import org.chemthunder.acropolis.core.item.skyfall.RaptureItem;
import org.chemthunder.acropolis.core.item.skyfall.RequiemItem;

import java.util.ArrayList;

/**
 * @author Chemthunder
 */
public interface AItems {
    ItemRegistrant plugin = new ItemRegistrant(Acropolis.MOD_ID);

    Item CELESTIAL_GRIMOIRE = plugin.register("celestial_grimoire", CelestialGrimoireItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
            .fireproof()
    );

    Item TAMPERED_GRIMOIRE = plugin.register("tampered_grimoire", TamperedGrimoireItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("tampered"))
            .fireproof()
    );

    Item FATAL_OMEN = plugin.register("fatal_omen", FatalOmenItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("omen"))
            .component(AComponentTypes.OMEN, new OmenComponent(new ArrayList<>(), true, false))
            .fireproof()
    );

    Item RAPTURE = plugin.register("rapture", RaptureItem::new, new Item.Settings()
            .maxCount(1)
            .sword(ToolMaterial.NETHERITE, 3.0F, -2.4F)
            .fireproof()
            .enchantable(4)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("rapture"))
    );

    Item EXALTED_IDOL = plugin.register("exalted_idol", ExaltedIdolItem::new, new Item.Settings()
            .maxCount(1)
            .component(DataComponentTypes.TOOLTIP_STYLE, Acropolis.id("grimoire"))
    );

    Item REQUIEM = plugin.register("requiem", RequiemItem::new, new Item.Settings()
            .maxCount(1)
            .attributeModifiers(AttributeModifiersComponent.builder()
                    .add(
                            EntityAttributes.ATTACK_DAMAGE,
                            new EntityAttributeModifier(
                                    Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                    9.5,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            AttributeModifierSlot.MAINHAND
                    )
                    .add(
                            EntityAttributes.ATTACK_SPEED,
                            new EntityAttributeModifier(
                                    Item.BASE_ATTACK_SPEED_MODIFIER_ID,
                                    -2.4,
                                    EntityAttributeModifier.Operation.ADD_VALUE
                            ),
                            AttributeModifierSlot.MAINHAND
                    )
                    .build()
            )
    );

    Item ARMOR_LOADER = plugin.register("armor_loader", Item::new, new Item.Settings()
            .maxCount(1)
            .equippable(EquipmentSlot.HEAD)
    );

    static void init() {}
}
