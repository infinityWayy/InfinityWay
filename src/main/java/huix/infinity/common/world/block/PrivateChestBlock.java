package huix.infinity.common.world.block;

import huix.infinity.common.world.block.entity.PrivateChestBlockEntity;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class PrivateChestBlock extends ChestBlock {

    protected PrivateChestBlock(Properties properties) {
        super(properties, IFWBlockEntityTypes.private_chest::get);
    }

    @Override
    @NotNull
    protected InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            MenuProvider menuprovider = this.getMenuProvider(state, level, pos);
            if (menuprovider != null && blockentity instanceof PrivateChestBlockEntity privateChestBlock) {
                String ownerName = privateChestBlock.owner_name();
                String playerName = player.getDisplayName().getString();

                if (ownerName != null && ownerName.equals(playerName)) {
                    player.openMenu(menuprovider);
                    player.awardStat(this.getOpenChestStat());
                    PiglinAi.angerNearbyPiglins(player, true);
                } else {
                    // 播放箱子被阻挡的声音
                    level.playSound(null, pos, SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PrivateChestBlockEntity entity) {
            if (placer instanceof Player player) {
                entity.setOwner(player);
            } else {
                entity.emptyOwner();
            }
        }
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        return this.defaultBlockState()
                .setValue(FACING, direction)
                .setValue(TYPE, ChestType.SINGLE)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public @NotNull BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new PrivateChestBlockEntity(blockPos, blockState);
    }

    @Override
    @NotNull
    public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState,
                                  @NotNull net.minecraft.world.level.LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction.getAxis().isHorizontal() && state.getValue(TYPE) == ChestType.SINGLE) {
            Direction chestDirection = state.getValue(FACING);
            if (direction == chestDirection.getClockWise() || direction == chestDirection.getCounterClockWise()) {
                if (canConnectTo(state, neighborState, level, currentPos, neighborPos)) {
                    ChestType chestType = direction == chestDirection.getClockWise() ? ChestType.LEFT : ChestType.RIGHT;
                    return state.setValue(TYPE, chestType);
                }
            }
        } else if (state.getValue(TYPE) != ChestType.SINGLE) {
            Direction chestDirection = state.getValue(FACING);
            Direction oppositeDirection = state.getValue(TYPE) == ChestType.LEFT ? chestDirection.getClockWise() : chestDirection.getCounterClockWise();
            if (direction == oppositeDirection) {
                if (!canConnectTo(state, neighborState, level, currentPos, neighborPos)) {
                    return state.setValue(TYPE, ChestType.SINGLE);
                }
            }
        }

        return state;
    }

    // 检查是否可以连接到相邻的箱子
    private boolean canConnectTo(@NotNull BlockState currentState, @NotNull BlockState neighborState,
                                 @NotNull net.minecraft.world.level.LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        // 必须是同一类型的私人箱子
        if (neighborState.getBlock() != currentState.getBlock()) {
            return false;
        }

        // 必须面向相同方向
        if (neighborState.getValue(FACING) != currentState.getValue(FACING)) {
            return false;
        }

        // 检查所有者是否相同
        BlockEntity currentEntity = level.getBlockEntity(currentPos);
        BlockEntity neighborEntity = level.getBlockEntity(neighborPos);

        if (currentEntity instanceof PrivateChestBlockEntity currentChest &&
                neighborEntity instanceof PrivateChestBlockEntity neighborChest) {
            String currentOwner = currentChest.owner_name();
            String neighborOwner = neighborChest.owner_name();

            // 只有相同所有者的箱子才能合并
            if (currentOwner != null && neighborOwner != null) {
                return currentOwner.equals(neighborOwner);
            }
        }

        return false;
    }
}