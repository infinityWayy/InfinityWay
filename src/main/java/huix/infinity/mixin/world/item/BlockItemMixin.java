package huix.infinity.mixin.world.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin( BlockItem.class )
public abstract class BlockItemMixin extends Item {
    @Shadow public abstract Block getBlock();

    public BlockItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/ItemUsedOnLocationTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), method = "place")
    private void playerHungerOnPlace(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir, @Local(ordinal = 1) BlockState state, @Local Player player) {
        player.causeFoodExhaustion(Math.min(state.getBlock().defaultDestroyTime(), 20.0F));
    }


}
