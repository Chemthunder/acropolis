package org.chemthunder.acropolis.core.cca.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import org.chemthunder.acropolis.core.Acropolis;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class ImagesComponent implements AutoSyncedComponent, ClientTickingComponent {
    public static final ComponentKey<ImagesComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("images"),
            ImagesComponent.class
    );
    private final ClientPlayerEntity player;

    private List<Image> afterImages = new ArrayList<>();

    public ImagesComponent(ClientPlayerEntity player) {
        this.player = player;
    }

    public void clientTick() {
        if (player.age % 5 == 0) {
            List<Image> pre = new ArrayList<>(afterImages);

            pre.add(
                    new Image(
                            player.getEntityPos(),
                            player.getBodyYaw(),
                            player.getHeadYaw(),
                            player.getPitch()
                    )
            );

            List<Image> toPost = new ArrayList<>(pre);

            toPost.removeLast();

            this.setAfterImages(toPost);
        }
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readData(ReadView readView) {
        afterImages = readView.read("AfterImages", Image.CODEC.listOf()).orElse(new ArrayList<>());
    }

    public void writeData(WriteView writeView) {
        if (!afterImages.isEmpty()) {
            writeView.put("AfterImages", Image.CODEC.listOf(), afterImages);
        }
    }

    public List<Image> getAfterImages() {
        return afterImages;
    }

    public void setAfterImages(List<Image> afterImages) {
        this.afterImages = afterImages;
        sync();
    }

    public record Image(
            Vec3d position,
            float bodyYaw,
            float headYaw,
            float pitch
    ) {
        public static final Codec<Image> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Vec3d.CODEC
                        .fieldOf("position")
                        .forGetter(Image::position),
                Codec.FLOAT
                        .fieldOf("bodyYaw")
                        .forGetter(Image::bodyYaw),
                Codec.FLOAT
                        .fieldOf("headYaw")
                        .forGetter(Image::headYaw),
                Codec.FLOAT
                        .fieldOf("pitch")
                        .forGetter(Image::pitch)
        ).apply(instance, Image::new));
    }
}
