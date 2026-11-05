package org.chemthunder.acropolis.datagen.providers.client;

import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.index.ASoundEvents;

import java.util.concurrent.CompletableFuture;

import static org.chemthunder.acropolis.core.Acropolis.id;

/**
 * @author Chemthunder
 */
public class AcropolisSoundsProvider extends FabricSoundsProvider {
    public AcropolisSoundsProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, SoundExporter soundExporter) {
        soundExporter.add(ASoundEvents.SIGHT, sound(id("entity/sight")));
        soundExporter.add(ASoundEvents.MERCY_START, sound(id("event/mercy_start")));

        soundExporter.add(ASoundEvents.RAPTURE_HIT, sound(id("item/rapture_hit"))
                .subtitle("subtitles.acropolis.rapture_hit")
        );

        soundExporter.add(ASoundEvents.RYN_PLUSH, SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/ryn_plush")))
    //            .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/mede_plush_1")))
                .subtitle("subtitles.acropolis.ryn_plush")
        );

        soundExporter.add(ASoundEvents.MEDE_PLUSH, SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/mede_plush")))
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/mede_plush_1")))
                .subtitle("subtitles.acropolis.mede_plush")
        );

        soundExporter.add(ASoundEvents.FLEUR_PLUSH, SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/fleur_plush")))
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/fleur_plush_1")))
                .subtitle("subtitles.acropolis.fleur_plush")
        );

        soundExporter.add(ASoundEvents.SWOON, sound(id("event/swoon")));
    }

    private static SoundTypeBuilder sound(Identifier id) {
        return SoundTypeBuilder.of()
                .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id));
    }

    public String getName() {
        return "Acropolis Sounds";
    }
}
