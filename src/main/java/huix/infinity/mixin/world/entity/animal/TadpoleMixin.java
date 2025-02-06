package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.func_extension.BucketableExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( Tadpole.class )
public abstract class TadpoleMixin extends AbstractFish implements BucketableExtension {

    @Shadow protected abstract boolean isFood(ItemStack stack);

    @Shadow protected abstract void feed(Player player, ItemStack stack);

    @Override
    public ItemStack ifw_getBucketItemStack(IFWTiers tier) {
        return BucketHelper.tadpoleBucket(tier);
    }

    @Overwrite
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack)) {
            this.feed(player, itemstack);
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        } else {
            return BucketableExtension.ifw_bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
    }

    public TadpoleMixin(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
    }
}
