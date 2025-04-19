package huix.infinity.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.util.RenderHelper;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChestRenderer.class)
public class ChestRendererMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Sheets;chooseMaterial(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/level/block/state/properties/ChestType;Z)Lnet/minecraft/client/resources/model/Material;")
            , method = "getMaterial")
    private Material injectChest(BlockEntity blockEntity, ChestType chestType, boolean holiday) {
        return RenderHelper.chooseMaterial(blockEntity, chestType, holiday);
    }
}
