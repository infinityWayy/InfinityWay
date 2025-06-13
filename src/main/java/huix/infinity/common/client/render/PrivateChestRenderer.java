package huix.infinity.common.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import huix.infinity.common.world.block.entity.PrivateChestBlockEntity;
import huix.infinity.util.RenderHelper;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class PrivateChestRenderer implements BlockEntityRenderer<PrivateChestBlockEntity> {
    private static final String BOTTOM = "bottom";
    private static final String LID = "lid";
    private static final String LOCK = "lock";
    private final ModelPart lid;
    private final ModelPart bottom;
    private final ModelPart lock;
    private final ModelPart doubleLeftLid;
    private final ModelPart doubleLeftBottom;
    private final ModelPart doubleLeftLock;
    private final ModelPart doubleRightLid;
    private final ModelPart doubleRightBottom;
    private final ModelPart doubleRightLock;
    private final boolean xmasTextures;

    public PrivateChestRenderer(BlockEntityRendererProvider.Context context) {
        // 检查是否是圣诞节期间
        Calendar calendar = Calendar.getInstance();
        this.xmasTextures = calendar.get(Calendar.MONTH) + 1 == 12 &&
                calendar.get(Calendar.DAY_OF_MONTH) >= 24 &&
                calendar.get(Calendar.DAY_OF_MONTH) <= 26;

        ModelPart modelpart = context.bakeLayer(ModelLayers.CHEST);
        this.bottom = modelpart.getChild(BOTTOM);
        this.lid = modelpart.getChild(LID);
        this.lock = modelpart.getChild(LOCK);

        ModelPart modelpart1 = context.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
        this.doubleLeftBottom = modelpart1.getChild(BOTTOM);
        this.doubleLeftLid = modelpart1.getChild(LID);
        this.doubleLeftLock = modelpart1.getChild(LOCK);

        ModelPart modelpart2 = context.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
        this.doubleRightBottom = modelpart2.getChild(BOTTOM);
        this.doubleRightLid = modelpart2.getChild(LID);
        this.doubleRightLock = modelpart2.getChild(LOCK);
    }

    @Override
    public void render(@NotNull PrivateChestBlockEntity blockEntity, float partialTick,
                       @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        boolean flag = level != null;
        BlockState blockstate = flag ? blockEntity.getBlockState() :
                Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ?
                blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();

        if (block instanceof AbstractChestBlock<?> abstractchestblock) {
            boolean flag1 = chesttype != ChestType.SINGLE;
            poseStack.pushPose();
            float f = blockstate.getValue(ChestBlock.FACING).toYRot();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(-f));
            poseStack.translate(-0.5F, -0.5F, -0.5F);

            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> neighborcombineresult;
            if (flag) {
                neighborcombineresult = abstractchestblock.combine(blockstate, level, blockEntity.getBlockPos(), true);
            } else {
                neighborcombineresult = DoubleBlockCombiner.Combiner::acceptNone;
            }

            float f1 = ((Float2FloatFunction)neighborcombineresult.apply(ChestBlock.opennessCombiner(blockEntity))).get(partialTick);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = ((Int2IntFunction)neighborcombineresult.apply(new BrightnessCombiner())).applyAsInt(packedLight);

            Material material = this.getMaterial(blockEntity, chesttype);
            VertexConsumer vertexconsumer = material.buffer(bufferSource, RenderType::entityCutout);

            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.render(poseStack, vertexconsumer, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, f1, i, packedOverlay);
                } else {
                    this.render(poseStack, vertexconsumer, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, f1, i, packedOverlay);
                }
            } else {
                this.render(poseStack, vertexconsumer, this.lid, this.lock, this.bottom, f1, i, packedOverlay);
            }

            poseStack.popPose();
        }
    }

    private void render(PoseStack poseStack, VertexConsumer consumer, ModelPart lidPart,
                        ModelPart lockPart, ModelPart bottomPart, float lidAngle,
                        int packedLight, int packedOverlay) {
        lidPart.xRot = -(lidAngle * ((float)Math.PI / 2F));
        lockPart.xRot = lidPart.xRot;
        lidPart.render(poseStack, consumer, packedLight, packedOverlay);
        lockPart.render(poseStack, consumer, packedLight, packedOverlay);
        bottomPart.render(poseStack, consumer, packedLight, packedOverlay);
    }

    protected Material getMaterial(PrivateChestBlockEntity blockEntity, ChestType chestType) {
        return RenderHelper.chooseMaterial(blockEntity, chestType, this.xmasTextures);
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(@NotNull PrivateChestBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return AABB.encapsulatingFullBlocks(pos.offset(-1, 0, -1), pos.offset(1, 1, 1));
    }
}