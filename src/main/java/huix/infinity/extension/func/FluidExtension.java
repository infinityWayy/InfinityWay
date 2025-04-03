package huix.infinity.extension.func;

import huix.infinity.common.world.item.tier.IFWTier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;

public interface FluidExtension {

    default Item getBucket(Player player, LevelAccessor level, IFWTier tier){
        return Items.AIR;
    }
}
