package huix.infinity.mixin.world.item;

import huix.infinity.util.BowlHelper;
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
public abstract class ItemBowlMixin {

    @Inject(
            method = "use(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResultHolder;",
            at = @At("HEAD")
    )
    private void scoopWater(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack handItem = player.getItemInHand(hand);
        if (!handItem.is(Items.BOWL)) return;

        ItemStack result = BowlHelper.tryPickupWater(level, player, hand);

        if (!result.isEmpty()) {
            if (!level.isClientSide()) {
                if (!player.getInventory().add(result)) {
                    player.drop(result, false);
                }
            }
        }
    }
}