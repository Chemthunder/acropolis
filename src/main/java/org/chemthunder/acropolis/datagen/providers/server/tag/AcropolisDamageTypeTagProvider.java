package org.chemthunder.acropolis.datagen.providers.server.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisDamageTypeTagProvider extends FabricTagProvider<DamageType> {
    public AcropolisDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.getTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(ADamageTypes.GAZE.getValue())
                .add(ADamageTypes.SMOKE.getValue())
                .add(ADamageTypes.RETRIBUTION.getValue())
                .add(ADamageTypes.PESTILENCE.getValue())
                .add(ADamageTypes.FALL.getValue())
                .add(ADamageTypes.BORDER_TRUE.getValue())
                .add(ADamageTypes.BORDER.getValue())
                .add(ADamageTypes.ABSORB.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(ADamageTypes.FALL.getValue())
                .add(ADamageTypes.PESTILENCE.getValue())
                .add(ADamageTypes.ABSORB.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .add(ADamageTypes.FALL.getValue())
                .add(ADamageTypes.BORDER_TRUE.getValue())
                .add(ADamageTypes.GAZE.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
                .add(ADamageTypes.FALL.getValue())
                .add(ADamageTypes.BORDER.getValue())
                .add(ADamageTypes.BORDER_TRUE.getValue())
                .add(ADamageTypes.RETRIBUTION.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(ADamageTypes.RETRIBUTION.getValue())
                .add(ADamageTypes.PESTILENCE.getValue())
                .add(ADamageTypes.FALL.getValue())
                .add(ADamageTypes.BORDER_TRUE.getValue())
                .add(ADamageTypes.SMOKE.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                .add(ADamageTypes.RETRIBUTION.getValue())
                .add(ADamageTypes.PESTILENCE.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE)
                .add(ADamageTypes.BORDER.getValue())
                .add(ADamageTypes.BORDER_TRUE.getValue())
                .add(ADamageTypes.PESTILENCE.getValue());
    }
}
