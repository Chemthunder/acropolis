package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.block.MeteorBlockEntity;

/**
 * @author Chemthunder
 */
public interface ABlockEntityTypes {
    BlockEntityTypeRegistrant plugin = new BlockEntityTypeRegistrant(Acropolis.MOD_ID);

    BlockEntityType<MeteorBlockEntity> METEOR = plugin.register("meteor", FabricBlockEntityTypeBuilder.create(
            MeteorBlockEntity::new,
            ABlocks.METEOR
    ));

    static void init() {}

    static void clinit() {}
}
