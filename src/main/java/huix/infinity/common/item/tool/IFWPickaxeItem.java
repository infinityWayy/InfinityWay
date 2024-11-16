package huix.infinity.common.item.tool;

import huix.infinity.common.item.tier.IIFWTier;
import huix.infinity.common.item.tool.impl.IFWDiggerItem;
import net.minecraft.tags.BlockTags;

public class IFWPickaxeItem extends IFWDiggerItem {
    public IFWPickaxeItem(IIFWTier tier, Properties properties) {
        super(tier, 3, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

}
