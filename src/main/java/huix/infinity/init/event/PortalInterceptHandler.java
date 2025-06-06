package huix.infinity.init.event;

import huix.infinity.init.InfinityWay;
import huix.infinity.common.world.dimension.IFWDimensionTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.TickTask;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalShape;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PortalInterceptHandler {

    private static final Map<String, BlockPos> PORTAL_CACHE = new HashMap<>();
    private static final Set<String> PROCESSING_POSITIONS = ConcurrentHashMap.newKeySet();

    private static final Map<String, Long> FRAME_CHANGE_COOLDOWN = new ConcurrentHashMap<>();
    private static final int FRAME_CHECK_DELAY_TICKS = 5;
    private static final int FRAME_CHANGE_COOLDOWN_TICKS = 10;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPortalSpawn(BlockEvent.PortalSpawnEvent event) {
        Level level = (Level) event.getLevel();
        if (level.isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) level;
        BlockPos pos = event.getPos();
        event.setCanceled(true);

        Optional<PortalShape> shapeX = PortalShape.findEmptyPortalShape(serverLevel, pos, Direction.Axis.X);
        Optional<PortalShape> shapeZ = PortalShape.findEmptyPortalShape(serverLevel, pos, Direction.Axis.Z);

        Optional<PortalShape> portalShape = shapeX.isPresent() ? shapeX : shapeZ;

        if (portalShape.isPresent()) {
            if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
                if (hasRunestoneCornersUnderworld(serverLevel, portalShape.get(), pos)) {
                    createRunePortal(serverLevel, pos);
                } else {
                    createCrossDimensionPortal(serverLevel, pos);
                }
            } else {
                if (hasRunestoneCorners(serverLevel, portalShape.get())) {
                    createRunePortal(serverLevel, pos);
                } else {
                    determinePortalType(serverLevel, pos, portalShape.get());
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide()) return;

        BlockState placedState = event.getPlacedBlock();
        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();

        if (isRunestoneBlock(placedState) || placedState.is(Blocks.OBSIDIAN)) {
            level.getServer().tell(new TickTask(level.getServer().getTickCount() + FRAME_CHECK_DELAY_TICKS, () -> {
                checkAndUpdateNearbyPortalsExtensive(level, pos, "PLACE");
                performIntelligentFrameDetection(level, pos, "PLACE");
            }));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) return;

        BlockState brokenState = event.getState();
        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos pos = event.getPos();

        if (isRunestoneBlock(brokenState) || brokenState.is(Blocks.OBSIDIAN)) {
            level.getServer().tell(new TickTask(level.getServer().getTickCount() + FRAME_CHECK_DELAY_TICKS, () -> {
                checkAndUpdateNearbyPortalsExtensive(level, pos, "BREAK");
                performIntelligentFrameDetection(level, pos, "BREAK");
            }));
        }
    }

    private static void performIntelligentFrameDetection(ServerLevel level, BlockPos changedPos, String action) {
        if (level.dimension() != IFWDimensionTypes.UNDERWORLD_LEVEL &&
                level.dimension() != Level.NETHER) {
            return;
        }

        String posKey = level.dimension().location() + ":" + changedPos.toShortString();
        long currentTime = level.getGameTime();

        if (FRAME_CHANGE_COOLDOWN.containsKey(posKey)) {
            long lastCheck = FRAME_CHANGE_COOLDOWN.get(posKey);
            if (currentTime - lastCheck < FRAME_CHANGE_COOLDOWN_TICKS) {
                return;
            }
        }

        FRAME_CHANGE_COOLDOWN.put(posKey, currentTime);

        try {
            Set<BlockPos> nearbyPortals = findNearbyActivePortals(level, changedPos);

            for (BlockPos portalPos : nearbyPortals) {
                processPortalFrameChange(level, portalPos, changedPos, action);
            }

        } catch (Exception e) {
        }
    }

    private static Set<BlockPos> findNearbyActivePortals(ServerLevel level, BlockPos centerPos) {
        Set<BlockPos> portalPositions = new HashSet<>();

        for (int dx = -8; dx <= 8; dx++) {
            for (int dy = -8; dy <= 8; dy++) {
                for (int dz = -8; dz <= 8; dz++) {
                    BlockPos checkPos = centerPos.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(checkPos);

                    if (isPortalBlock(state)) {
                        portalPositions.add(checkPos);
                    }
                }
            }
        }

        return portalPositions;
    }

    private static void processPortalFrameChange(ServerLevel level, BlockPos portalPos, BlockPos changedPos, String action) {
        BlockState portalState = level.getBlockState(portalPos);
        if (!isPortalBlock(portalState)) {
            return;
        }

        Optional<PortalShape> shapeOpt = findPortalShapeAt(level, portalPos);
        if (!shapeOpt.isPresent()) {
            return;
        }

        PortalShape shape = shapeOpt.get();
        Block currentPortalType = portalState.getBlock();

        FrameAnalysisResult frameResult = analyzePortalFrame(level, shape, portalPos);

        Block targetPortalType = determineTargetPortalType(level, frameResult);

        if (currentPortalType != targetPortalType && targetPortalType != null) {
            performPortalTypeSwitch(level, shape, portalPos, currentPortalType, targetPortalType, frameResult);
        }
    }

    private static class FrameAnalysisResult {
        final boolean hasCompleteRunestoneCorners;
        final boolean hasPartialRunestoneCorners;
        final boolean hasObsidianFrame;
        final boolean isFrameComplete;
        final int runestoneCornerCount;

        FrameAnalysisResult(boolean hasCompleteRunestoneCorners, boolean hasPartialRunestoneCorners,
                            boolean hasObsidianFrame, boolean isFrameComplete, int runestoneCornerCount) {
            this.hasCompleteRunestoneCorners = hasCompleteRunestoneCorners;
            this.hasPartialRunestoneCorners = hasPartialRunestoneCorners;
            this.hasObsidianFrame = hasObsidianFrame;
            this.isFrameComplete = isFrameComplete;
            this.runestoneCornerCount = runestoneCornerCount;
        }
    }

    private static FrameAnalysisResult analyzePortalFrame(ServerLevel level, PortalShape shape, BlockPos referencePos) {
        try {
            FrameAnalysisResult reflectionResult = analyzeFrameByReflection(level, shape);
            if (reflectionResult != null) {
                return reflectionResult;
            }
        } catch (Exception e) {
        }

        return analyzeFrameByExploration(level, referencePos);
    }

    private static FrameAnalysisResult analyzeFrameByReflection(ServerLevel level, PortalShape shape) {
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

            BlockPos[] corners = new BlockPos[4];
            if (axis == Direction.Axis.X) {
                corners[0] = bottomLeft.offset(-1, -1, 0);
                corners[1] = bottomLeft.offset(width, -1, 0);
                corners[2] = bottomLeft.offset(-1, height, 0);
                corners[3] = bottomLeft.offset(width, height, 0);
            } else {
                corners[0] = bottomLeft.offset(0, -1, -1);
                corners[1] = bottomLeft.offset(0, -1, width);
                corners[2] = bottomLeft.offset(0, height, -1);
                corners[3] = bottomLeft.offset(0, height, width);
            }

            int runestoneCorners = 0;
            int obsidianCorners = 0;
            int validCorners = 0;

            for (BlockPos corner : corners) {
                BlockState cornerState = level.getBlockState(corner);
                if (isRunestoneBlock(cornerState)) {
                    runestoneCorners++;
                    validCorners++;
                } else if (cornerState.is(Blocks.OBSIDIAN)) {
                    obsidianCorners++;
                    validCorners++;
                }
            }

            boolean frameComplete = isFrameStructureComplete(level, bottomLeft, width, height, axis);
            boolean hasCompleteRunestoneCorners = (runestoneCorners == 4);
            boolean hasPartialRunestoneCorners = (runestoneCorners > 0 && runestoneCorners < 4);
            boolean hasObsidianFrame = (obsidianCorners > 0 || validCorners == 4);

            return new FrameAnalysisResult(
                    hasCompleteRunestoneCorners,
                    hasPartialRunestoneCorners,
                    hasObsidianFrame,
                    frameComplete,
                    runestoneCorners
            );

        } catch (Exception e) {
            return null;
        }
    }

    private static FrameAnalysisResult analyzeFrameByExploration(ServerLevel level, BlockPos referencePos) {
        Set<BlockPos> frameBlocks = new HashSet<>();
        Set<BlockPos> runestoneCorners = new HashSet<>();
        Set<BlockPos> obsidianBlocks = new HashSet<>();

        for (int dx = -5; dx <= 5; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                for (int dz = -5; dz <= 5; dz++) {
                    BlockPos checkPos = referencePos.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(checkPos);

                    if (isRunestoneBlock(state) || state.is(Blocks.OBSIDIAN)) {
                        frameBlocks.add(checkPos);

                        if (isRunestoneBlock(state)) {
                            if (isLikelyCornerBlock(level, checkPos)) {
                                runestoneCorners.add(checkPos);
                            }
                        } else if (state.is(Blocks.OBSIDIAN)) {
                            obsidianBlocks.add(checkPos);
                        }
                    }
                }
            }
        }

        boolean hasCompleteRunestoneCorners = runestoneCorners.size() >= 4;
        boolean hasPartialRunestoneCorners = runestoneCorners.size() > 0 && runestoneCorners.size() < 4;
        boolean hasObsidianFrame = !obsidianBlocks.isEmpty() || frameBlocks.size() >= 14;
        boolean frameComplete = frameBlocks.size() >= 14;

        return new FrameAnalysisResult(
                hasCompleteRunestoneCorners,
                hasPartialRunestoneCorners,
                hasObsidianFrame,
                frameComplete,
                runestoneCorners.size()
        );
    }

    private static boolean isLikelyCornerBlock(ServerLevel level, BlockPos pos) {
        int adjacentFrameBlocks = 0;

        for (Direction dir : Direction.values()) {
            BlockPos adjacent = pos.relative(dir);
            BlockState adjState = level.getBlockState(adjacent);

            if (adjState.is(Blocks.OBSIDIAN) || isRunestoneBlock(adjState)) {
                adjacentFrameBlocks++;
            }
        }

        return adjacentFrameBlocks >= 2 && adjacentFrameBlocks <= 3;
    }

    private static boolean isFrameStructureComplete(ServerLevel level, BlockPos bottomLeft, int width, int height, Direction.Axis axis) {
        try {
            return checkFrameEdgeComplete(level, bottomLeft, width, height, axis);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean checkFrameEdgeComplete(ServerLevel level, BlockPos bottomLeft, int width, int height, Direction.Axis axis) {
        int validFrameBlocks = 0;
        int totalFrameBlocks = 2 * (width + height + 2);

        for (int i = -1; i <= width; i++) {
            BlockPos framePos = axis == Direction.Axis.X ?
                    bottomLeft.offset(i, -1, 0) : bottomLeft.offset(0, -1, i);
            if (isValidFrameBlock(level, framePos)) {
                validFrameBlocks++;
            }
        }

        for (int i = -1; i <= width; i++) {
            BlockPos framePos = axis == Direction.Axis.X ?
                    bottomLeft.offset(i, height, 0) : bottomLeft.offset(0, height, i);
            if (isValidFrameBlock(level, framePos)) {
                validFrameBlocks++;
            }
        }

        for (int i = 0; i < height; i++) {
            BlockPos framePos = axis == Direction.Axis.X ?
                    bottomLeft.offset(-1, i, 0) : bottomLeft.offset(0, i, -1);
            if (isValidFrameBlock(level, framePos)) {
                validFrameBlocks++;
            }
        }

        for (int i = 0; i < height; i++) {
            BlockPos framePos = axis == Direction.Axis.X ?
                    bottomLeft.offset(width, i, 0) : bottomLeft.offset(0, i, width);
            if (isValidFrameBlock(level, framePos)) {
                validFrameBlocks++;
            }
        }

        return validFrameBlocks >= (totalFrameBlocks * 0.8);
    }

    private static boolean isValidFrameBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(Blocks.OBSIDIAN) || isRunestoneBlock(state);
    }

    private static Block determineTargetPortalType(ServerLevel level, FrameAnalysisResult frameResult) {
        if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL ||
                level.dimension() == Level.NETHER) {

            if (frameResult.hasCompleteRunestoneCorners && frameResult.isFrameComplete) {
                return huix.infinity.common.world.block.IFWBlocks.rune_portal.get();
            } else if (frameResult.hasObsidianFrame && frameResult.isFrameComplete) {
                return huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get();
            }
        }

        return null;
    }

    private static void performPortalTypeSwitch(ServerLevel level, PortalShape shape, BlockPos portalPos,
                                                Block currentType, Block targetType, FrameAnalysisResult frameResult) {
        try {
            level.playSound(null, portalPos.getX(), portalPos.getY(), portalPos.getZ(),
                    SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 0.8F,
                    targetType == huix.infinity.common.world.block.IFWBlocks.rune_portal.get() ? 1.2F : 0.8F);

            clearPortalBlocks(level, shape);

            level.getServer().tell(new TickTask(level.getServer().getTickCount() + 2, () -> {
                createPortalWithBlock(level, portalPos, targetType);
            }));

        } catch (Exception e) {
        }
    }

    private static boolean isPortalBlock(BlockState state) {
        return state.getBlock() == huix.infinity.common.world.block.IFWBlocks.rune_portal.get() ||
                state.getBlock() == huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onFlintAndSteelUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;

        ItemStack heldItem = event.getItemStack();
        if (!heldItem.is(Items.FLINT_AND_STEEL)) return;

        ServerLevel level = (ServerLevel) event.getLevel();
        BlockPos clickedPos = event.getPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Direction face = event.getFace();

        if (face == null) {
            return;
        }

        BlockPos firePos = clickedPos.relative(face);

        String posKey = level.dimension().location() + ":" + firePos.toShortString();
        if (PROCESSING_POSITIONS.contains(posKey)) {
            return;
        }

        try {
            PROCESSING_POSITIONS.add(posKey);

            if (canLightSpecialBlock(level, clickedPos, clickedState) &&
                    !isNearPortalFrame(level, clickedPos) && !isNearPortalFrame(level, firePos)) {
                return;
            }

            Optional<PortalShape> shapeX = PortalShape.findEmptyPortalShape(level, firePos, Direction.Axis.X);
            Optional<PortalShape> shapeZ = PortalShape.findEmptyPortalShape(level, firePos, Direction.Axis.Z);

            if (!shapeX.isPresent() && !shapeZ.isPresent()) {
                if (needsSpecialFireHandling(level) && canPlaceFireAt(level, firePos)) {
                    event.setCanceled(true);
                    BlockState fireState = BaseFireBlock.getState(level, firePos);
                    level.setBlock(firePos, fireState, 11);
                    level.playSound(null, firePos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f,
                            level.random.nextFloat() * 0.4f + 0.8f);

                    if (event.getEntity() instanceof LivingEntity livingEntity) {
                        heldItem.hurtAndBreak(1, livingEntity, LivingEntity.getSlotForHand(event.getHand()));
                    }
                }
                return;
            }

            event.setCanceled(true);
            Optional<PortalShape> portalShape = shapeX.isPresent() ? shapeX : shapeZ;

            if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
                if (hasRunestoneCornersUnderworld(level, portalShape.get(), firePos)) {
                    createRunePortal(level, firePos);
                } else {
                    createCrossDimensionPortal(level, firePos);
                }
            } else {
                if (hasRunestoneCorners(level, portalShape.get())) {
                    createRunePortal(level, firePos);
                } else {
                    determinePortalType(level, firePos, portalShape.get());
                }
            }

            level.playSound(null, firePos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f,
                    level.random.nextFloat() * 0.4f + 0.8f);

            if (event.getEntity() instanceof LivingEntity livingEntity) {
                heldItem.hurtAndBreak(1, livingEntity, LivingEntity.getSlotForHand(event.getHand()));
            }

        } finally {
            level.getServer().tell(new TickTask(level.getServer().getTickCount() + 1, () -> {
                PROCESSING_POSITIONS.remove(posKey);
            }));
        }
    }

    private static boolean hasRunestoneCornersUnderworld(ServerLevel level, PortalShape portalShape, BlockPos referencePos) {
        boolean standardResult = hasRunestoneCorners(level, portalShape);
        if (standardResult) {
            return true;
        }

        return hasRunestoneCornersDirectSearch(level, referencePos);
    }

    private static boolean hasRunestoneCornersDirectSearch(ServerLevel level, BlockPos centerPos) {
        for (int dx = -5; dx <= 5; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                for (int dz = -5; dz <= 5; dz++) {
                    BlockPos searchPos = centerPos.offset(dx, dy, dz);
                    if (isStandardRunestonePortalFrame(level, searchPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isStandardRunestonePortalFrame(ServerLevel level, BlockPos bottomLeft) {
        return checkRunestoneFramePattern(level, bottomLeft, Direction.Axis.X) ||
                checkRunestoneFramePattern(level, bottomLeft, Direction.Axis.Z);
    }

    private static boolean checkRunestoneFramePattern(ServerLevel level, BlockPos bottomLeft, Direction.Axis axis) {
        try {
            BlockPos corner1, corner2, corner3, corner4;

            if (axis == Direction.Axis.X) {
                corner1 = bottomLeft;
                corner2 = bottomLeft.offset(3, 0, 0);
                corner3 = bottomLeft.offset(0, 4, 0);
                corner4 = bottomLeft.offset(3, 4, 0);
            } else {
                corner1 = bottomLeft;
                corner2 = bottomLeft.offset(0, 0, 3);
                corner3 = bottomLeft.offset(0, 4, 0);
                corner4 = bottomLeft.offset(0, 4, 3);
            }

            boolean hasRunestoneCorners =
                    isRunestoneBlockUnderworld(level, corner1) &&
                            isRunestoneBlockUnderworld(level, corner2) &&
                            isRunestoneBlockUnderworld(level, corner3) &&
                            isRunestoneBlockUnderworld(level, corner4);

            if (!hasRunestoneCorners) {
                return false;
            }

            return validatePortalFrameStructure(level, bottomLeft, axis);

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean validatePortalFrameStructure(ServerLevel level, BlockPos bottomLeft, Direction.Axis axis) {
        try {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 5; y++) {
                    BlockPos checkPos;
                    if (axis == Direction.Axis.X) {
                        checkPos = bottomLeft.offset(x, y, 0);
                    } else {
                        checkPos = bottomLeft.offset(0, y, x);
                    }

                    BlockState state = level.getBlockState(checkPos);

                    boolean isEdge = (x == 0 || x == 3 || y == 0 || y == 4);
                    boolean isInside = (x >= 1 && x <= 2 && y >= 1 && y <= 3);

                    if (isEdge) {
                        if (!state.is(Blocks.OBSIDIAN) && !isRunestoneBlockUnderworld(level, checkPos)) {
                            return false;
                        }
                    } else if (isInside) {
                        if (!state.isAir()) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isRunestoneBlockUnderworld(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return isRunestoneBlock(state);
    }

    private static void determinePortalType(ServerLevel level, BlockPos pos, PortalShape portalShape) {
        if (level.dimension() == Level.OVERWORLD) {
            if (hasFrameAdjacentToBedrock(level, portalShape)) {
                createCrossDimensionPortal(level, pos);
            } else {
                createRunePortal(level, pos);
            }
        } else if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
            createCrossDimensionPortal(level, pos);
        } else if (level.dimension() == Level.NETHER) {
            createCrossDimensionPortal(level, pos);
        } else {
            createRunePortal(level, pos);
        }
    }

    private static boolean hasRunestoneCorners(ServerLevel level, PortalShape portalShape) {
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

            BlockPos corner1, corner2, corner3, corner4;
            if (axis == Direction.Axis.X) {
                corner1 = bottomLeft.offset(-1, -1, 0);
                corner2 = bottomLeft.offset(width, -1, 0);
                corner3 = bottomLeft.offset(-1, height, 0);
                corner4 = bottomLeft.offset(width, height, 0);
            } else {
                corner1 = bottomLeft.offset(0, -1, -1);
                corner2 = bottomLeft.offset(0, -1, width);
                corner3 = bottomLeft.offset(0, height, -1);
                corner4 = bottomLeft.offset(0, height, width);
            }

            return isRunestoneBlock(level.getBlockState(corner1)) &&
                    isRunestoneBlock(level.getBlockState(corner2)) &&
                    isRunestoneBlock(level.getBlockState(corner3)) &&
                    isRunestoneBlock(level.getBlockState(corner4));

        } catch (Exception e) {
            return checkRunestoneByExploration(level, portalShape);
        }
    }

    private static boolean checkRunestoneByExploration(ServerLevel level, PortalShape shape) {
        try {
            java.lang.reflect.Field bottomLeftField = PortalShape.class.getDeclaredField("bottomLeft");
            bottomLeftField.setAccessible(true);
            BlockPos bottomLeft = (BlockPos) bottomLeftField.get(shape);

            Set<BlockPos> cornerCandidates = new HashSet<>();

            for (int dx = -3; dx <= 3; dx++) {
                for (int dy = -3; dy <= 3; dy++) {
                    for (int dz = -3; dz <= 3; dz++) {
                        BlockPos checkPos = bottomLeft.offset(dx, dy, dz);
                        BlockState state = level.getBlockState(checkPos);

                        if (state.is(Blocks.OBSIDIAN) || isRunestoneBlock(state)) {
                            int adjacentFrameBlocks = 0;
                            for (Direction dir : Direction.values()) {
                                BlockPos adjacent = checkPos.relative(dir);
                                BlockState adjState = level.getBlockState(adjacent);
                                if (adjState.is(Blocks.OBSIDIAN) || isRunestoneBlock(adjState)) {
                                    adjacentFrameBlocks++;
                                }
                            }

                            if (adjacentFrameBlocks == 2) {
                                cornerCandidates.add(checkPos);
                            }
                        }
                    }
                }
            }

            if (cornerCandidates.size() != 4) {
                return false;
            }

            int runestoneCorners = 0;
            for (BlockPos corner : cornerCandidates) {
                if (isRunestoneBlock(level.getBlockState(corner))) {
                    runestoneCorners++;
                }
            }

            return runestoneCorners == 4;

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isRunestoneBlock(BlockState state) {
        try {
            String blockName = state.getBlock().getDescriptionId().toLowerCase();
            if (blockName.contains("portal")) {
                return false;
            }

            boolean isRunestone = false;
            try {
                isRunestone = state.is(huix.infinity.common.core.tag.IFWBlockTags.RUNESTONE);
            } catch (Exception e) {
            }

            if (!isRunestone) {
                isRunestone = blockName.contains("runestone") ||
                        blockName.contains("mithril_runestone_") ||
                        blockName.contains("adamantium_runestone_") ||
                        blockName.contains("_nul") || blockName.contains("_quas") ||
                        blockName.contains("_por") || blockName.contains("_an") ||
                        blockName.contains("_nox") || blockName.contains("_flam") ||
                        blockName.contains("_vas") || blockName.contains("_des") ||
                        blockName.contains("_ort") || blockName.contains("_tym") ||
                        blockName.contains("_corp") || blockName.contains("_lor") ||
                        blockName.contains("_mani") || blockName.contains("_jux") ||
                        blockName.contains("_ylem") || blockName.contains("_sanct");
            }

            return isRunestone;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean hasFrameAdjacentToBedrock(ServerLevel level, PortalShape portalShape) {
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

            return checkFrameObsidian(level, bottomLeft, width, height, axis);

        } catch (Exception e) {
            try {
                java.lang.reflect.Field bottomLeftField = PortalShape.class.getDeclaredField("bottomLeft");
                bottomLeftField.setAccessible(true);
                BlockPos bottomLeft = (BlockPos) bottomLeftField.get(portalShape);
                return checkNearbyObsidian(level, bottomLeft);
            } catch (Exception ex) {
                return false;
            }
        }
    }

    private static boolean checkFrameObsidian(ServerLevel level, BlockPos bottomLeft, int width, int height, Direction.Axis axis) {
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

    private static boolean checkNearbyObsidian(ServerLevel level, BlockPos pos) {
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

    private static boolean isAdjacentToBedrock(ServerLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = pos.relative(direction);
            if (level.getBlockState(adjacentPos).is(Blocks.BEDROCK)) {
                return true;
            }
        }
        return false;
    }

    private static void createRunePortal(ServerLevel level, BlockPos pos) {
        Block runePortal = huix.infinity.common.world.block.IFWBlocks.rune_portal.get();
        createPortalWithBlock(level, pos, runePortal);
    }

    private static void createCrossDimensionPortal(ServerLevel level, BlockPos pos) {
        Block crossDimensionPortal = huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get();
        createPortalWithBlock(level, pos, crossDimensionPortal);
    }

    private static void createPortalWithBlock(ServerLevel level, BlockPos pos, Block portalBlock) {
        Optional<PortalShape> portalShape = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);
        if (!portalShape.isPresent()) {
            portalShape = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.Z);
        }

        if (portalShape.isPresent()) {
            PortalShape shape = portalShape.get();
            if (shape.isValid()) {
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

                    BlockState portalState = portalBlock.defaultBlockState();
                    if (portalState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                        portalState = portalState.setValue(BlockStateProperties.HORIZONTAL_AXIS, axis);
                    }

                    if (portalBlock == huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get()) {
                        int colorType = determineCrossDimensionPortalColorType(level, bottomLeft);
                        portalState = portalState.setValue(
                                huix.infinity.common.world.block.CrossDimensionPortalBlock.COLOR_TYPE,
                                colorType
                        );
                    }

                    BlockState finalPortalState = portalState;
                    BlockPos.betweenClosed(
                            bottomLeft,
                            bottomLeft.relative(Direction.UP, height - 1)
                                    .relative(axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH, width - 1)
                    ).forEach(blockPos -> level.setBlock(blockPos, finalPortalState, 18));

                } catch (Exception e) {
                    createFallbackPortal(level, pos, portalBlock);
                }
            }
        }
    }

    private static void createFallbackPortal(ServerLevel level, BlockPos pos, Block portalBlock) {
        BlockState portalState = portalBlock.defaultBlockState();
        if (portalState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            portalState = portalState.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X);
        }

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 3; y++) {
                level.setBlock(pos.offset(x, y, 0), portalState, 18);
            }
        }
    }

    private static void checkAndUpdateNearbyPortalsExtensive(ServerLevel level, BlockPos changedPos, String action) {
        for (int dx = -5; dx <= 5; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                for (int dz = -5; dz <= 5; dz++) {
                    BlockPos checkPos = changedPos.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(checkPos);

                    if (state.getBlock() == huix.infinity.common.world.block.IFWBlocks.rune_portal.get() ||
                            state.getBlock() == huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get()) {

                        Optional<PortalShape> shape = findPortalShapeAt(level, checkPos);
                        if (shape.isPresent()) {
                            updatePortalType(level, checkPos, shape.get());
                        }
                    }
                }
            }
        }
    }

    private static Optional<PortalShape> findPortalShapeAt(ServerLevel level, BlockPos pos) {
        Optional<PortalShape> shapeX = PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.X);
        if (shapeX.isPresent()) return shapeX;

        return PortalShape.findEmptyPortalShape(level, pos, Direction.Axis.Z);
    }

    private static void updatePortalType(ServerLevel level, BlockPos pos, PortalShape shape) {
        Block currentPortalType = null;
        Block targetPortalType = null;

        if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
            if (hasRunestoneCornersUnderworld(level, shape, pos)) {
                targetPortalType = huix.infinity.common.world.block.IFWBlocks.rune_portal.get();
            } else {
                targetPortalType = huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get();
            }
        } else {
            if (hasRunestoneCorners(level, shape)) {
                targetPortalType = huix.infinity.common.world.block.IFWBlocks.rune_portal.get();
            } else {
                if (level.dimension() == Level.OVERWORLD) {
                    if (hasFrameAdjacentToBedrock(level, shape)) {
                        targetPortalType = huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get();
                    } else {
                        targetPortalType = huix.infinity.common.world.block.IFWBlocks.rune_portal.get();
                    }
                } else if (level.dimension() == Level.NETHER) {
                    targetPortalType = huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get();
                } else {
                    targetPortalType = huix.infinity.common.world.block.IFWBlocks.rune_portal.get();
                }
            }
        }

        BlockState currentState = level.getBlockState(pos);
        currentPortalType = currentState.getBlock();

        if (currentPortalType != targetPortalType) {
            clearPortalBlocks(level, shape);
            createPortalWithBlock(level, pos, targetPortalType);
        }
    }

    private static void clearPortalBlocks(ServerLevel level, PortalShape shape) {
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

            BlockPos.betweenClosed(
                    bottomLeft,
                    bottomLeft.relative(Direction.UP, height - 1)
                            .relative(axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH, width - 1)
            ).forEach(blockPos -> {
                BlockState state = level.getBlockState(blockPos);
                if (state.getBlock() == huix.infinity.common.world.block.IFWBlocks.rune_portal.get() ||
                        state.getBlock() == huix.infinity.common.world.block.IFWBlocks.cross_dimension_portal.get()) {
                    level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 18);
                }
            });

        } catch (Exception e) {
        }
    }

    private static boolean canLightSpecialBlock(ServerLevel level, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof CampfireBlock) {
            return CampfireBlock.canLight(state);
        }
        if (state.hasProperty(BlockStateProperties.LIT) && !state.getValue(BlockStateProperties.LIT)) {
            return true;
        }
        if (state.is(Blocks.TNT)) {
            return true;
        }
        return false;
    }

    private static boolean isNearPortalFrame(ServerLevel level, BlockPos pos) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos checkPos = pos.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(checkPos);
                    if (state.is(Blocks.OBSIDIAN) || isRunestoneBlock(state)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean needsSpecialFireHandling(ServerLevel level) {
        return level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL;
    }

    private static boolean canPlaceFireAt(ServerLevel level, BlockPos pos) {
        BlockState stateAtPos = level.getBlockState(pos);
        if (!stateAtPos.isAir()) {
            return false;
        }
        return BaseFireBlock.canBePlacedAt(level, pos, Direction.UP);
    }

    private static int determineCrossDimensionPortalColorType(ServerLevel level, BlockPos pos) {
        if (level.dimension() == Level.OVERWORLD) {
            return 0;
        } else if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
            boolean mantleAdjacent = isAdjacentToMantle(level, pos);
            return mantleAdjacent ? 1 : 0;
        } else if (level.dimension() == Level.NETHER) {
            return 0;
        }
        return 2;
    }

    private static boolean isAdjacentToMantle(ServerLevel level, BlockPos pos) {
        try {
            Block mantleBlock = huix.infinity.common.world.block.IFWBlocks.mantle.get();
            return checkPortalFrameAdjacentToBlock(level, pos, mantleBlock);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean checkPortalFrameAdjacentToBlock(ServerLevel level, BlockPos pos, Block targetBlock) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos checkPos = pos.offset(dx, dy, dz);
                    BlockState checkState = level.getBlockState(checkPos);

                    if (checkState.is(Blocks.OBSIDIAN) || isRunestoneBlock(checkState)) {
                        for (Direction direction : Direction.values()) {
                            BlockPos adjacentPos = checkPos.relative(direction);
                            BlockState adjacentState = level.getBlockState(adjacentPos);

                            if (adjacentState.is(targetBlock)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}