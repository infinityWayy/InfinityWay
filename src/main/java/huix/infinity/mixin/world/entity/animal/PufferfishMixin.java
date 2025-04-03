package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.extension.func.BucketableExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( Pufferfish.class )
public abstract class PufferfishMixin implements BucketableExtension {
    @Override
    public ItemStack ifw_getBucketItemStack(IFWTier tier) {
        return BucketHelper.pufferFishBucket(tier);
    }
}
