package org.chemthunder.acropolis.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;

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
                .add(AcropolisDamageTypes.GAZE.getValue())
                .add(AcropolisDamageTypes.SMOKE.getValue())
                .add(AcropolisDamageTypes.RETRIBUTION.getValue())
                .add(AcropolisDamageTypes.ABSORB.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(AcropolisDamageTypes.ABSORB.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .add(AcropolisDamageTypes.GAZE.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
                .add(AcropolisDamageTypes.RETRIBUTION.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(AcropolisDamageTypes.RETRIBUTION.getValue())
                .add(AcropolisDamageTypes.SMOKE.getValue());

        this.getTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                .add(AcropolisDamageTypes.RETRIBUTION.getValue());
    }
}
