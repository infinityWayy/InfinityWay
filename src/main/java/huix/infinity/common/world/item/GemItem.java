package huix.infinity.common.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GemItem extends Item {
    private final int xpRewards;

    public GemItem(Properties properties, int xpRewards) {
        super(properties);
        this.xpRewards = xpRewards;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (itemstack.isEmpty() || this.xpRewards <= 0) {
            return InteractionResultHolder.pass(itemstack);
        }
        int count = itemstack.getCount();
        boolean isShiftKeyDown = player.isShiftKeyDown();
        int totalXp;
        if (isShiftKeyDown) {
            totalXp = this.xpRewards * count;
            itemstack.shrink(count);
        } else {
            totalXp = this.xpRewards;
            itemstack.shrink(1);
        }
        player.giveExperiencePoints(totalXp);
        player.playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.0F);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (this.xpRewards > 0) {
            tooltipComponents.add(Component.translatable("tooltip.infinity.gem.xp", this.xpRewards).withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
