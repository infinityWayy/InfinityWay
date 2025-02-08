package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.func_extension.BucketableExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( TropicalFish.class )
public abstract class TropicalMixin implements BucketableExtension {
    @Override
    public ItemStack ifw_getBucketItemStack(IFWTier tier) {
        return BucketHelper.tropicalBucket(tier);
    }
}
