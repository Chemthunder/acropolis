package org.chemthunder.acropolis.datagen.providers.client;

import net.acoyt.acornlib.api.util.DataUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.acropolis.core.index.AItems;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class AcropolisLanguageProvider extends FabricLanguageProvider {
    public AcropolisLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(AItems.CELESTIAL_GRIMOIRE, "Celestial Grimoire");
//        translationBuilder.add(AcropolisItems.EXTOLLED_SIMULACRUM, "Extolled Simulacrum");
        translationBuilder.add(AItems.RAPTURE, "Rapture");
        translationBuilder.add(AItems.EXALTED_IDOL, "Exalted Idol");
        translationBuilder.add(AItems.FATAL_OMEN, "Fatal Omen");

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
                ADamageTypes.GAZE,
                "%1$s stared into the abyss",
                "%1$s stared into the abyss whilst fighting %2$s, wielding %3$s",
                "%1$s stared into the abyss whilst fighting %2$s"
        );

        DataUtils.registerDamageType(
                translationBuilder,
                ADamageTypes.SMOKE,
                "%1$s had their soul slip away",
                "%1$s had their soul slip away whilst fighting %2$s, wielding %3$s",
                "%1$s had their soul slip away whilst fighting %2$s"
        );

        DataUtils.registerDamageType(
                translationBuilder,
                ADamageTypes.RETRIBUTION,
                "%1$s had their sins burned away",
                "%1$s had their sins burned away whilst fighting %2$s, wielding %3$s",
                "%1$s had their sins burned away whilst fighting %2$s"
        );

        DataUtils.registerDamageType(
                translationBuilder,
                ADamageTypes.RAPTURE,
                "%1$s was ripped apart violently",
                "%1$s was ripped apart violently by %2$s, wielding %3$s",
                "%1$s was ripped apart violently by %2$s"
        );

        DataUtils.registerDamageType(
                translationBuilder,
                ADamageTypes.PESTILENCE,
                "%1$s was consumed",
                "%1$s was consumed violently by %2$s, wielding %3$s",
                "%1$s was consumed violently by %2$s"
        );

        DataUtils.registerDamageType(
                translationBuilder,
                ADamageTypes.BORDER,
                "%1$s refused its law",
                "%1$s refused its law whilst fighting %2$s, wielding %3$s",
                "%1$s refused its law whilst fighting %2$s"
        );

        DataUtils.registerDamageTypeAll(
                translationBuilder,
                ADamageTypes.REPURPOSE,
                "%1$s was absolved into the cosmos"
        );

        DataUtils.registerDamageTypeAll(
                translationBuilder,
                ADamageTypes.FALL,
                "%1$s fell before the hand of something higher"
        );

        DataUtils.registerDamageTypeAll(
                translationBuilder,
                ADamageTypes.BORDER_TRUE,
                "%1$s's heart gave out painfully"
        );
    }
}
