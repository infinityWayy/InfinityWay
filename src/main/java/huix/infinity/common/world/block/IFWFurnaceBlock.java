package huix.infinity.common.world.block;

import com.mojang.serialization.MapCodec;
import huix.infinity.common.world.block.entity.IFWFurnaceBlockEntity;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IFWFurnaceBlock extends AbstractFurnaceBlock {

    private int furnaceLevel = 1;

    public static final MapCodec<IFWFurnaceBlock> CODEC = simpleCodec(IFWFurnaceBlock::new);

    public IFWFurnaceBlock(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof IFWFurnaceBlockEntity) {
            player.openMenu((MenuProvider) blockentity);
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new IFWFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return createFurnaceTicker(level, entityType, IFWBlockEntityTypes.ifw_furnace.get());
    }

    public int furnaceLevel() {
        return this.furnaceLevel;
    }

    public IFWFurnaceBlock furnaceLevel(int furnaceLevel) {
        this.furnaceLevel = furnaceLevel;
        return this;
    }
}
