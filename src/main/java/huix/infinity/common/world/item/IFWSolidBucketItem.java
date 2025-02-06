package huix.infinity.common.world.item;

import huix.infinity.common.world.item.tier.IFWTiers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class IFWSolidBucketItem extends SolidBucketItem {
    private final IFWTiers tier;
    public IFWSolidBucketItem(Block block, SoundEvent placeSound, IFWTiers tier, Properties properties) {
        super(block, placeSound, properties);
        this.tier = tier;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult interactionresult = super.useOn(context);
        Player player = context.getPlayer();
        if (interactionresult.consumesAction() && player != null) {
            player.setItemInHand(context.getHand(), IFWBucketItem.ifw_emptySuccessItem(context.getItemInHand(), player, this.tier));
        }

        return interactionresult;
    }
}
