package huix.infinity.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.block.entity.PrivateChestBlockEntity;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;
import java.util.Map;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRendererMixin {

    @Shadow @Final
    private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    @Unique
    private final Map<Block, ChestBlockEntity> privateChestEntities = new HashMap<>();

    @Unique
    private void initPrivateChestEntities() {
        if (privateChestEntities.isEmpty()) {
            privateChestEntities.put(IFWBlocks.copper_private_chest.get(),
                    new PrivateChestBlockEntity(BlockPos.ZERO, IFWBlocks.copper_private_chest.get().defaultBlockState()));
            privateChestEntities.put(IFWBlocks.silver_private_chest.get(),
                    new PrivateChestBlockEntity(BlockPos.ZERO, IFWBlocks.silver_private_chest.get().defaultBlockState()));
            privateChestEntities.put(IFWBlocks.gold_private_chest.get(),
                    new PrivateChestBlockEntity(BlockPos.ZERO, IFWBlocks.gold_private_chest.get().defaultBlockState()));
            privateChestEntities.put(IFWBlocks.iron_private_chest.get(),
                    new PrivateChestBlockEntity(BlockPos.ZERO, IFWBlocks.iron_private_chest.get().defaultBlockState()));
            privateChestEntities.put(IFWBlocks.ancient_metal_private_chest.get(),
                    new PrivateChestBlockEntity(BlockPos.ZERO, IFWBlocks.ancient_metal_private_chest.get().defaultBlockState()));
            privateChestEntities.put(IFWBlocks.mithril_private_chest.get(),
                    new PrivateChestBlockEntity(BlockPos.ZERO, IFWBlocks.mithril_private_chest.get().defaultBlockState()));
            privateChestEntities.put(IFWBlocks.adamantium_private_chest.get(),
                    new PrivateChestBlockEntity(BlockPos.ZERO, IFWBlocks.adamantium_private_chest.get().defaultBlockState()));
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher;renderItem(Lnet/minecraft/world/level/block/entity/BlockEntity;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)Z")
            , method = "renderByItem")
    private boolean injectChest(BlockEntityRenderDispatcher instance, BlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay,
                                @Local Item item) {
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();

            initPrivateChestEntities();

            ChestBlockEntity privateChestEntity = privateChestEntities.get(block);
            if (privateChestEntity != null) {
                blockEntity = privateChestEntity;
            }
        }

        return instance.renderItem(blockEntity, poseStack, bufferSource, packedLight, packedOverlay);
    }
}