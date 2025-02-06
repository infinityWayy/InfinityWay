package huix.infinity.mixin.world.material;

import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.func_extension.FluidExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WaterFluid.class)
public class WaterFluidMixin implements FluidExtension {

    @Override
    public Item getBucket(Player player, LevelAccessor level, IFWTiers tier) {
        return BucketHelper.waterBucket(tier).getItem();
    }
}
