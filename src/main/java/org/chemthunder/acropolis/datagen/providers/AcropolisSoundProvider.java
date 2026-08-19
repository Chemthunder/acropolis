package org.chemthunder.acropolis.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.chemthunder.acropolis.core.index.AcropolisBlocks;
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

        AcropolisBlocks.PLUSHIES.forEach(plushRegistration -> {
            soundExporter.add(plushRegistration.squishSound(), SoundTypeBuilder.of(plushRegistration.squishSound())
                    .sound(SoundTypeBuilder.EntryBuilder.create(SoundTypeBuilder.RegistrationType.FILE, id("block/" + Registries.BLOCK.getId(plushRegistration.plush()).getPath())))
                    .subtitle("subtitles.acropolis." + Registries.BLOCK.getId(plushRegistration.plush()).getPath())
            );
        });
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
