package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IIFWTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;

public class KnifeWeapon extends DaggerWeapon {
    public KnifeWeapon(IIFWTier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public float getReachBonus() {
        return 0.25F;
    }


    @Override
    public float getDecayRateForBreakingBlock(BlockState state) {
        if (state.is(BlockTags.PLANKS) || state.is(BlockTags.CAVE_VINES) || state.is(BlockTags.WOOL) || state.is(BlockTags.WOOL_CARPETS))
            return super.getDecayRateForBreakingBlock(state) / 4.0F;
        return super.getDecayRateForBreakingBlock(state) / 2.0F;
    }

}
