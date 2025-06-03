package huix.infinity.common.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class GoldPanItem extends Item {

    public GoldPanItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            player.displayClientMessage(Component.translatable("tooltip.infinity.goldpan.empty"), true);
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    @Override
    public @NotNull InteractionResult useOn(net.minecraft.world.item.context.UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (player == null) return InteractionResult.FAIL;

        if (level.getBlockState(pos).getBlock() == Blocks.GRAVEL) {
            if (!level.isClientSide) {
                level.destroyBlock(pos, false);
                level.playSound(null, pos, SoundEvents.GRAVEL_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);

                ItemStack gravelPan = new ItemStack(IFWItems.gold_pan_gravel.get());
                if (stack.isDamaged()) {
                    gravelPan.setDamageValue(stack.getDamageValue());
                }

                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setItemInHand(context.getHand(), gravelPan);
                } else {
                    player.getInventory().add(gravelPan);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText( ItemStack stack,  TooltipContext context,  List<Component> tooltipComponents,  TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.infinity.goldpan.usage").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean isDamageable( ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage( ItemStack stack) {
        return 16;
    }

    @Override
    public boolean isEnchantable( ItemStack stack) {
        return false;
    }
}