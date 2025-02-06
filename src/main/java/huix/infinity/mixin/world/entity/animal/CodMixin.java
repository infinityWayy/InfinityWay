package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.func_extension.BucketableExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin( Cod.class )
public abstract class CodMixin implements BucketableExtension {

    @Override
    public ItemStack ifw_getBucketItemStack(IFWTiers tier) {
        return BucketHelper.codBucket(tier);
    }
}
