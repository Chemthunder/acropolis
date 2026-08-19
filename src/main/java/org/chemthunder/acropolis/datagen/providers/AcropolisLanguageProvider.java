package org.chemthunder.acropolis.datagen.providers;

import net.acoyt.acornlib.api.util.DataUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;
import org.chemthunder.acropolis.core.index.AcropolisDamageTypes;
import org.chemthunder.acropolis.core.index.AcropolisItems;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisLanguageProvider extends FabricLanguageProvider {
    public AcropolisLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(AcropolisItems.CELESTIAL_GRIMOIRE, "Celestial Grimoire");
//        translationBuilder.add(AcropolisItems.EXTOLLED_SIMULACRUM, "Extolled Simulacrum");

        translationBuilder.add("acropolis.midnightconfig.title", "Acropolis");
        translationBuilder.add("acropolis.midnightconfig.epilepsyMode", "Epilepsy Mode");

        translationBuilder.add(AcropolisBlocks.FLEUR_PLUSH, "Fleur Plush");
        translationBuilder.add(AcropolisBlocks.FLEUR_PLUSH.getTranslationKey() + ".desc", "the op lumiere");

        translationBuilder.add(AcropolisBlocks.MEDE_PLUSH, "Mede Plush");
        translationBuilder.add(AcropolisBlocks.MEDE_PLUSH.getTranslationKey() + ".desc", "mikeiscooler than me");

        DataUtils.registerDamageType(
                translationBuilder,
                AcropolisDamageTypes.GAZE,
                "%1$s stared into the abyss",
                "%1$s stared into the abyss whilst fighting %2$s, wielding %3$s",
                "%1$s stared into the abyss whilst fighting %2$s"
        );

        DataUtils.registerDamageType(
                translationBuilder,
                AcropolisDamageTypes.SMOKE,
                "%1$s had their soul slip away",
                "%1$s had their soul slip away whilst fighting %2$s, wielding %3$s",
                "%1$s had their soul slip away whilst fighting %2$s"
        );

        DataUtils.registerDamageType(
                translationBuilder,
                AcropolisDamageTypes.RETRIBUTION,
                "%1$s had their sins burned away",
                "%1$s had their sins burned away whilst fighting %2$s, wielding %3$s",
                "%1$s had their sins burned away whilst fighting %2$s"
        );
    }
}
