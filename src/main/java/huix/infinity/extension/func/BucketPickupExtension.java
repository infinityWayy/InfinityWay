package huix.infinity.extension.func;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface BucketPickupExtension extends BucketPickup {

    default ItemStack ifw_pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state, IFWTier tier) {
        return ItemStack.EMPTY;
    }
}
