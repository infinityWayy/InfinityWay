package huix.infinity.mixin.world.entity.projectile;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EggItem.class)
public class EggItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void ifw$interceptEggUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        FoodProperties foodProps = stack.getFoodProperties(player);
        if (!player.isCrouching()) {
            if (foodProps != null && player.canEat(foodProps.canAlwaysEat())) {
                player.startUsingItem(hand);
                cir.setReturnValue(InteractionResultHolder.consume(stack));
            } else {
                cir.setReturnValue(InteractionResultHolder.fail(stack));
            }
        }
    }
}