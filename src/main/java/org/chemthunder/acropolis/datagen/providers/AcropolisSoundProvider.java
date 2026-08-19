package org.chemthunder.acropolis.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.index.AcropolisSoundEvents;

import java.util.concurrent.CompletableFuture;

import static org.chemthunder.acropolis.core.Acropolis.id;

/**
 * @author Chemthunder
 */
public class AcropolisSoundProvider extends FabricSoundsProvider {
    public AcropolisSoundProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, SoundExporter soundExporter) {
        soundExporter.add(AcropolisSoundEvents.SIGHT, sound(id("entity/sight")));

        soundExporter.add(AcropolisSoundEvents.FLEUR_PLUSH, SoundTypeBuilder.of(AcropolisSoundEvents.FLEUR_PLUSH)
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/fleur_plush")))
                .subtitle("subtitles.acropolis.fleur_plush")
        );

        soundExporter.add(AcropolisSoundEvents.MEDE_PLUSH, SoundTypeBuilder.of(AcropolisSoundEvents.MEDE_PLUSH)
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/mede_plush")))
                .subtitle("subtitles.acropolis.mede_plush")
        );
    }

    private static SoundTypeBuilder sound(Identifier id) {
        return SoundTypeBuilder.of().sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id));
    }

    private static SoundTypeBuilder sound(Identifier id, float volume) {
        return SoundTypeBuilder.of().sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id).volume(volume));
    }

    public String getName() {
        return "Acropolis Sounds";
    }
}
