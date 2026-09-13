package org.chemthunder.acropolis.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.chemthunder.acropolis.core.index.AcropolisBlockEntityTypes;

/**
 * @author Chemthunder
 */
public class MeteorBlockEntity extends BlockEntity {
    public MeteorBlockEntity(BlockPos pos, BlockState state) {
        super(AcropolisBlockEntityTypes.METEOR, pos, state);
    }
}
