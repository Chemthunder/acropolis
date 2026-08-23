package org.chemthunder.acropolis.datagen.providers;

import net.acoyt.acornlib.api.util.DataUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
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
        translationBuilder.add(AcropolisItems.RAPTURE, "Rapture");
        translationBuilder.add(AcropolisItems.EXALTED_IDOL, "Exalted Idol");

        translationBuilder.add("acropolis.midnightconfig.title", "Acropolis");
        translationBuilder.add("acropolis.midnightconfig.epilepsyMode", "Epilepsy Mode");
        translationBuilder.add("acropolis.midnightconfig.motionSicknessMode", "Motion-sick Mode");

        translationBuilder.add("itemGroup.acropolis", "Acropolis");

        translationBuilder.add("subtitles.acropolis.ryn_plush", "how weird");
        translationBuilder.add("subtitles.acropolis.mede_plush", "guh");
        translationBuilder.add("subtitles.acropolis.fleur_plush", "sound of pure fear");

        translationBuilder.add("subtitles.acropolis.rapture_hit", "Rapture slams");

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

        DataUtils.registerDamageType(
                translationBuilder,
                AcropolisDamageTypes.RAPTURE,
                "%1$s was ripped apart violently",
                "%1$s was ripped apart violently by %2$s, wielding %3$s",
                "%1$s was ripped apart violently by %2$s"
        );
    }
}
