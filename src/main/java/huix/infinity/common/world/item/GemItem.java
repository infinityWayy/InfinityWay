package huix.infinity.common.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GemItem extends Item {
    private final int xpRewards;

    public GemItem(Properties properties, int xpRewards) {
        super(properties);
        this.xpRewards = xpRewards;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (itemstack != ItemStack.EMPTY && this.xpRewards > 0) {
            player.giveExperiencePoints(this.xpRewards);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.pass(player.getItemInHand(usedHand));
    }
}
