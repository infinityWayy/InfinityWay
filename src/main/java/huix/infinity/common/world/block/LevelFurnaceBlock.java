package huix.infinity.common.world.block;

import com.mojang.serialization.MapCodec;
import huix.infinity.common.world.block.entity.LevelFurnaceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class LevelFurnaceBlock extends AbstractFurnaceBlock {

    protected LevelFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> ifw_createFurnaceTicker(Level level, BlockEntityType<T> serverType, BlockEntityType<? extends AbstractFurnaceBlockEntity> clientType) {
        return level.isClientSide ? null : createTickerHelper(serverType, clientType, LevelFurnaceBlockEntity::serverTick);
    }
}
