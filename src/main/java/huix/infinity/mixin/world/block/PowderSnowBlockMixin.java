package huix.infinity.mixin.world.block;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.extension.func.BucketPickupExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin implements BucketPickupExtension {

    @Override
    public ItemStack ifw_pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state, IFWTier tier) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        if (!level.isClientSide()) {
            level.levelEvent(2001, pos, Block.getId(state));
        }

        return BucketHelper.powderSnowBucket(tier);
    }
}
