package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.IFWBucketItem;
import huix.infinity.common.world.item.tier.IFWTier;
import huix.infinity.common.world.item.tier.IFWTiers;
import huix.infinity.func_extension.BucketableExtension;
import huix.infinity.util.BucketHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin( Axolotl.class )
public abstract class AxolotlMixin extends Animal implements BucketableExtension {

    @Override
    public ItemStack ifw_getBucketItemStack(IFWTier tier) {
        return BucketHelper.axolotlBucket(tier);
    }

    @Overwrite
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return BucketableExtension.ifw_bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Overwrite
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (stack.is(IFWItemTags.BUCKETS_TROPICAL_FISH)) {
            IFWBucketItem inHand = (IFWBucketItem) player.getItemInHand(hand).getItem();
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, BucketHelper.waterBucket(inHand.tier())));
        } else {
            super.usePlayerItem(player, hand, stack);
        }

    }

    protected AxolotlMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }
}
