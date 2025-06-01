package huix.infinity.mixin.world.item;

import huix.infinity.common.world.entity.projectile.ThrownSlimeBall;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class SlimeBallMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onSlimeBallUse(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemstack = player.getItemInHand(usedHand);

        if (itemstack.getItem() == Items.SLIME_BALL) {

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!level.isClientSide) {

                ThrownSlimeBall thrownSlimeBall = new ThrownSlimeBall(level, player);
                thrownSlimeBall.setItem(itemstack);

                thrownSlimeBall.setDamageValues(1.0F, 1.0F, false);

                thrownSlimeBall.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(thrownSlimeBall);
            }

            player.awardStat(Stats.ITEM_USED.get(Items.SLIME_BALL));

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide()));
        }
    }
}