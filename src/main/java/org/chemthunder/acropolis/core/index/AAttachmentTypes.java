package org.chemthunder.acropolis.core.index;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.LazyEntityReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.codec.PacketCodecs;
import org.chemthunder.acropolis.core.Acropolis;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public interface AAttachmentTypes {
    AttachmentType<Boolean> AWAKENED = register("awakened",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .syncWith(PacketCodecs.BOOLEAN, AttachmentSyncPredicate.all())
                    .initializer(() -> false)
    );

    AttachmentType<LazyEntityReference<LivingEntity>> OWNERSHIP = register("ownership",
            builder -> builder
                    .persistent(LazyEntityReference.createCodec())
                    .syncWith(LazyEntityReference.createPacketCodec(), AttachmentSyncPredicate.all())
    );

    AttachmentType<Boolean> EXPECTING_SHOCKWAVE = register("expecting_shockwave",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .syncWith(PacketCodecs.BOOLEAN, AttachmentSyncPredicate.all())
                    .initializer(() -> false)
    );

    static void init() {}

    static <T> AttachmentType<T> register(String name, Consumer<AttachmentRegistry.Builder<T>> consumer) {
        return AttachmentRegistry.create(Acropolis.id(name), consumer);
    }
}
