package huix.infinity.common.world.item;

import huix.infinity.common.world.entity.projectile.ThrownSlimeBall;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SlimeBallItem extends Item {
    private final float throwDamage;
    private final float corrosionDamage;
    private final boolean isAcidic;

    public SlimeBallItem(Properties properties, float throwDamage, float corrosionDamage, boolean isAcidic) {
        super(properties);
        this.throwDamage = throwDamage;
        this.corrosionDamage = corrosionDamage;
        this.isAcidic = isAcidic;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide) {

            ThrownSlimeBall thrownSlimeBall = new ThrownSlimeBall(level, player);
            thrownSlimeBall.setItem(itemstack);

            thrownSlimeBall.setDamageValues(throwDamage, corrosionDamage, isAcidic);

            thrownSlimeBall.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownSlimeBall);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    // Getter 方法
    public float getThrowDamage() {
        return throwDamage;
    }

    public float getCorrosionDamage() {
        return corrosionDamage;
    }

    public boolean isAcidic() {
        return isAcidic;
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        tooltipComponents.add(Component.translatable("tooltip.ifw.damage_level", this.throwDamage)
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.ifw.corrosion_level", this.corrosionDamage)
                .withStyle(ChatFormatting.RED));
    }
}