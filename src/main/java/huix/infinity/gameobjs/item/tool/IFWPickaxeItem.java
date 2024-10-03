package huix.infinity.gameobjs.item.tool;

import huix.infinity.gameobjs.item.tier.IIFWTier;
import huix.infinity.gameobjs.item.tool.interfaces.IFWDiggerItem;
import huix.infinity.util.DamageableItemHelper;
import net.minecraft.tags.BlockTags;

public class IFWPickaxeItem extends IFWDiggerItem {
    public IFWPickaxeItem(IIFWTier tier, Properties properties) {
        super(tier, 3, BlockTags.MINEABLE_WITH_PICKAXE, properties);
    }

}
