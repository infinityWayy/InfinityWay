package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.func_extension.BucketableExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( Pufferfish.class )
public abstract class PufferfishMixin implements BucketableExtension {
    @Override
    public ItemStack ifw_getBucketItemStack(IFWTiers tier) {
        return BucketHelper.pufferFishBucket(tier);
    }
}
