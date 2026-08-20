package org.chemthunder.acropolis.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public class MeteorBlock extends BlockWithEntity {
    private static final MapCodec<MeteorBlock> CODEC = createCodec(MeteorBlock::new);

    public MeteorBlock(Settings settings) {
        super(settings);
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}
