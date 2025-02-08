package huix.infinity.func_extension;

import huix.infinity.common.world.item.IFWBucketItem;
import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

import java.util.Optional;

public interface BucketableExtension extends Bucketable{

    default ItemStack ifw_getBucketItemStack(IFWTier tier) {
        return ItemStack.EMPTY;
    }

    static <T extends LivingEntity & BucketableExtension> Optional<InteractionResult> ifw_bucketMobPickup(Player player, InteractionHand hand, T entity) {
        ItemStack itemstack = player.getItemInHand(hand);
        IFWBucketItem item = (IFWBucketItem) itemstack.getItem();
        if (itemstack.is(Tags.Items.BUCKETS_WATER)  && entity.isAlive()) {
            entity.playSound(entity.getPickupSound(), 1.0F, 1.0F);
            ItemStack itemstack1 = entity.ifw_getBucketItemStack(item.tier());
            entity.saveToBucketTag(itemstack1);
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, itemstack1, false);
            player.setItemInHand(hand, itemstack2);
            Level level = entity.level();
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, itemstack1);
            }

            entity.discard();
            return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
        } else {
            return Optional.empty();
        }
    }
}
