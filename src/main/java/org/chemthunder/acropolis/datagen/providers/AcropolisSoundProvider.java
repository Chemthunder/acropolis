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
        soundExporter.add(AcropolisSoundEvents.MERCY_START, sound(id("event/mercy_start")));

        soundExporter.add(AcropolisSoundEvents.RAPTURE_HIT, sound(id("item/rapture_hit"))
                .subtitle("subtitles.acropolis.rapture_hit")
        );

        soundExporter.add(AcropolisSoundEvents.RYN_PLUSH, SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/ryn_plush")))
    //            .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/mede_plush_1")))
                .subtitle("subtitles.acropolis.ryn_plush")
        );

        soundExporter.add(AcropolisSoundEvents.MEDE_PLUSH, SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/mede_plush")))
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/mede_plush_1")))
                .subtitle("subtitles.acropolis.mede_plush")
        );

        soundExporter.add(AcropolisSoundEvents.FLEUR_PLUSH, SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/fleur_plush")))
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/fleur_plush_1")))
                .subtitle("subtitles.acropolis.fleur_plush")
        );
    }

    private static SoundTypeBuilder sound(Identifier id) {
        return SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id));
    }

    public String getName() {
        return "Acropolis Sounds";
    }
}
