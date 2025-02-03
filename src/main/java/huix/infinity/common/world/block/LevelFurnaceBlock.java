package huix.infinity.common.world.block;

import huix.infinity.common.world.block.entity.LevelFurnaceBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;

public abstract class LevelFurnaceBlock extends AbstractFurnaceBlock {
    private int furnaceLevel;

    public int furnaceLevel() {
        return furnaceLevel;
    }

    public LevelFurnaceBlock furnaceLevel(int furnaceLevel) {
        this.furnaceLevel = furnaceLevel;
        return this;
    }

    protected LevelFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> ifw_createFurnaceTicker(Level level, BlockEntityType<T> serverType
            , BlockEntityType<? extends LevelFurnaceBlockEntity> clientType) {
        return level.isClientSide ? null : createTickerHelper(serverType, clientType, LevelFurnaceBlockEntity::serverTick);
    }
}
