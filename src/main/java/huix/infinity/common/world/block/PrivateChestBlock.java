package huix.infinity.common.world.block;

import huix.infinity.common.world.block.entity.PrivateChestBlockEntity;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.Nullable;

public class PrivateChestBlock extends ChestBlock {

    protected PrivateChestBlock(Properties properties) {
        super(properties, IFWBlockEntityTypes.private_chest::get);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            MenuProvider menuprovider = this.getMenuProvider(state, level, pos);
            if (menuprovider != null && blockentity instanceof PrivateChestBlockEntity privateChestBlock) {
                if (privateChestBlock.owner_name().equals(player.getDisplayName().getString())) {
                    player.openMenu(menuprovider);
                    player.awardStat(this.getOpenChestStat());
                    PiglinAi.angerNearbyPiglins(player, true);
                } else {
                    // play sound
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        PrivateChestBlockEntity entity = (PrivateChestBlockEntity) level.getBlockEntity(pos);
        if (placer instanceof Player player) entity.setOwner(player);
        else entity.emptyOwner();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        return this.defaultBlockState().setValue(FACING, direction).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PrivateChestBlockEntity(blockPos, blockState);
    }
}
