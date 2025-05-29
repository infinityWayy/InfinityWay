package huix.infinity.init.event;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.Optional;

@EventBusSubscriber(modid = InfinityWay.MOD_ID)
public class PortalInterceptHandler {

    @SubscribeEvent
    public static void onPortalSpawn(BlockEvent.PortalSpawnEvent event) {
        Level level = (Level) event.getLevel();

        if (level.isClientSide || level.dimension() != Level.OVERWORLD) {
            return;
        }

        ServerLevel serverLevel = (ServerLevel) level;
        BlockPos pos = event.getPos();

        // 检查传送门框架是否有黑曜石紧贴基岩
        if (hasFrameAdjacentToBedrock(serverLevel, pos)) {
            return; // 允许正常的地狱传送门生成
        }

        // 取消原始传送门生成
        event.setCanceled(true);

        createRunePortal(serverLevel, pos);
    }

    /**
     * 检查传送门框架是否有黑曜石紧贴基岩
     */
    private static boolean hasFrameAdjacentToBedrock(ServerLevel level, BlockPos pos) {
        // 尝试找到传送门框架
        Optional<PortalShape> portalShapeX = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);
        Optional<PortalShape> portalShapeZ = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.Z);

        PortalShape portalShape = null;
        if (portalShapeX.isPresent()) {
            portalShape = portalShapeX.get();
        } else if (portalShapeZ.isPresent()) {
            portalShape = portalShapeZ.get();
        }

        if (portalShape == null) {
            return false; // 没有找到有效的传送门形状
        }

        // 使用反射获取传送门框架信息
        try {
            java.lang.reflect.Field bottomLeftField = PortalShape.class.getDeclaredField("bottomLeft");
            java.lang.reflect.Field widthField = PortalShape.class.getDeclaredField("width");
            java.lang.reflect.Field heightField = PortalShape.class.getDeclaredField("height");
            java.lang.reflect.Field axisField = PortalShape.class.getDeclaredField("axis");

            bottomLeftField.setAccessible(true);
            widthField.setAccessible(true);
            heightField.setAccessible(true);
            axisField.setAccessible(true);

            BlockPos bottomLeft = (BlockPos) bottomLeftField.get(portalShape);
            int width = widthField.getInt(portalShape);
            int height = heightField.getInt(portalShape);
            Direction.Axis axis = (Direction.Axis) axisField.get(portalShape);

            // 检查传送门框架的黑曜石
            return checkFrameObsidian(level, bottomLeft, width, height, axis);

        } catch (Exception e) {
            // 反射失败，使用备用检测方法
            return checkNearbyObsidian(level, pos);
        }
    }

    /**
     * 检查传送门框架的黑曜石是否紧贴基岩
     */
    private static boolean checkFrameObsidian(ServerLevel level, BlockPos bottomLeft, int width, int height, Direction.Axis axis) {
        // 检查底部框架
        for (int i = -1; i <= width; i++) {
            BlockPos framePos = bottomLeft.offset(
                    axis == Direction.Axis.X ? i : 0,
                    -1,
                    axis == Direction.Axis.Z ? i : 0
            );
            if (level.getBlockState(framePos).is(Blocks.OBSIDIAN) && isAdjacentToBedrock(level, framePos)) {
                return true;
            }
        }

        // 检查顶部框架
        for (int i = -1; i <= width; i++) {
            BlockPos framePos = bottomLeft.offset(
                    axis == Direction.Axis.X ? i : 0,
                    height,
                    axis == Direction.Axis.Z ? i : 0
            );
            if (level.getBlockState(framePos).is(Blocks.OBSIDIAN) && isAdjacentToBedrock(level, framePos)) {
                return true;
            }
        }

        // 检查左侧框架
        for (int i = 0; i < height; i++) {
            BlockPos framePos = bottomLeft.offset(
                    axis == Direction.Axis.X ? -1 : 0,
                    i,
                    axis == Direction.Axis.Z ? -1 : 0
            );
            if (level.getBlockState(framePos).is(Blocks.OBSIDIAN) && isAdjacentToBedrock(level, framePos)) {
                return true;
            }
        }

        // 检查右侧框架
        for (int i = 0; i < height; i++) {
            BlockPos framePos = bottomLeft.offset(
                    axis == Direction.Axis.X ? width : 0,
                    i,
                    axis == Direction.Axis.Z ? width : 0
            );
            if (level.getBlockState(framePos).is(Blocks.OBSIDIAN) && isAdjacentToBedrock(level, framePos)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 备用检测方法：检查附近的黑曜石
     */
    private static boolean checkNearbyObsidian(ServerLevel level, BlockPos pos) {
        // 只检查传送门位置附近的小范围
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos checkPos = pos.offset(dx, dy, dz);
                    if (level.getBlockState(checkPos).is(Blocks.OBSIDIAN)) {
                        if (isAdjacentToBedrock(level, checkPos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查单个方块是否紧贴基岩
     */
    private static boolean isAdjacentToBedrock(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            if (level.getBlockState(adjacentPos).is(Blocks.BEDROCK)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建传送门
     */
    private static void createRunePortal(ServerLevel level, BlockPos pos) {
        Block runePortal = huix.infinity.common.world.block.IFWBlocks.rune_portal.get();

        // 尝试X轴方向
        Optional<PortalShape> portalShape = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);
        if (!portalShape.isPresent()) {
            // 尝试Z轴方向
            portalShape = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.Z);
        }

        if (portalShape.isPresent()) {
            PortalShape shape = portalShape.get();
            if (shape.isValid()) {
                // 使用反射获取PortalShape的私有字段
                try {
                    java.lang.reflect.Field bottomLeftField = PortalShape.class.getDeclaredField("bottomLeft");
                    java.lang.reflect.Field widthField = PortalShape.class.getDeclaredField("width");
                    java.lang.reflect.Field heightField = PortalShape.class.getDeclaredField("height");
                    java.lang.reflect.Field axisField = PortalShape.class.getDeclaredField("axis");

                    bottomLeftField.setAccessible(true);
                    widthField.setAccessible(true);
                    heightField.setAccessible(true);
                    axisField.setAccessible(true);

                    BlockPos bottomLeft = (BlockPos) bottomLeftField.get(shape);
                    int width = widthField.getInt(shape);
                    int height = heightField.getInt(shape);
                    Direction.Axis axis = (Direction.Axis) axisField.get(shape);

                    BlockState portalState = runePortal.defaultBlockState()
                            .setValue(BlockStateProperties.HORIZONTAL_AXIS, axis);

                    BlockPos.betweenClosed(
                            bottomLeft,
                            bottomLeft.relative(Direction.UP, height - 1)
                                    .relative(axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH, width - 1)
                    ).forEach(blockPos -> level.setBlock(blockPos, portalState, 18));

                } catch (Exception e) {
                    // 反射失败，使用备用方案
                    createFallbackPortal(level, pos, runePortal);
                }
            }
        }
    }

    /**
     * 备用传送门创建方法
     */
    private static void createFallbackPortal(ServerLevel level, BlockPos pos, Block runePortal) {
        BlockState portalState = runePortal.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X);

        // 创建标准2x3传送门
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                level.setBlock(pos.offset(x, y, 0), portalState, 18);
            }
        }
    }
}