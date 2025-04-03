package huix.infinity.mixin.world.material;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.extension.func.FluidExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin extends FlowingFluid implements FluidExtension {

    @Override
    public Item getBucket(Player player, LevelAccessor level, IFWTier tier) {
        if (level.getRandom().nextFloat() <= BucketHelper.chanceOfMeltingWhenFilledWithLava(tier)) {
            level.playSound(null, player.getOnPos(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            return ItemStack.EMPTY.getItem();
        }
        return BucketHelper.lavaBucket(tier).getItem();
    }
}
