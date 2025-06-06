package huix.infinity.util;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.world.block.RunePortalBlock;
import huix.infinity.common.world.dimension.IFWDimensionTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class RunePortalUtil {

    public static final int MITHRIL_DOMAIN_RADIUS = 5000;
    public static final int ADAMANTIUM_DOMAIN_RADIUS = 40000;

    private static final Map<String, Integer> RUNESTONE_METADATA = new HashMap<>();

    static {
        RUNESTONE_METADATA.put("nul", 0);        RUNESTONE_METADATA.put("quas", 1);        RUNESTONE_METADATA.put("por", 2);        RUNESTONE_METADATA.put("an", 3);
        RUNESTONE_METADATA.put("nox", 4);        RUNESTONE_METADATA.put("flam", 5);        RUNESTONE_METADATA.put("vas", 6);        RUNESTONE_METADATA.put("des", 7);
        RUNESTONE_METADATA.put("ort", 8);        RUNESTONE_METADATA.put("tym", 9);        RUNESTONE_METADATA.put("corp", 10);        RUNESTONE_METADATA.put("lor", 11);
        RUNESTONE_METADATA.put("mani", 12);        RUNESTONE_METADATA.put("jux", 13);        RUNESTONE_METADATA.put("ylem", 14);        RUNESTONE_METADATA.put("sanct", 15);
    }

    public static RunestoneType detectPortalRunestones(ServerLevel level, BlockPos portalPos) {
        BlockState portalState = level.getBlockState(portalPos);
        if (!(portalState.getBlock() instanceof RunePortalBlock)) {
            return RunestoneType.INVALID;
        }

        Direction.Axis axis = portalState.getValue(RunePortalBlock.AXIS);

        FrameBounds bounds = getFrameBounds(level, portalPos, axis);
        if (bounds == null) {
            return RunestoneType.INVALID;
        }

        return getRunegateType(level, bounds, axis);
    }

    private static FrameBounds getFrameBounds(ServerLevel level, BlockPos portalPos, Direction.Axis axis) {
        int minX, maxX, minY, maxY, minZ, maxZ;

        if (axis == Direction.Axis.X) {
            minX = getFrameMinX(level, portalPos);
            maxX = getFrameMaxX(level, portalPos);
            minY = getFrameMinY(level, portalPos);
            maxY = getFrameMaxY(level, portalPos);
            minZ = maxZ = portalPos.getZ();
        } else {
            minZ = getFrameMinZ(level, portalPos);
            maxZ = getFrameMaxZ(level, portalPos);
            minY = getFrameMinY(level, portalPos);
            maxY = getFrameMaxY(level, portalPos);
            minX = maxX = portalPos.getX();
        }

        if ((axis == Direction.Axis.X && (maxX - minX < 3 || maxY - minY < 4)) ||
                (axis == Direction.Axis.Z && (maxZ - minZ < 3 || maxY - minY < 4))) {
            return null;
        }

        return new FrameBounds(minX, maxX, minY, maxY, minZ, maxZ);
    }

    private static int getFrameMinX(ServerLevel level, BlockPos pos) {
        int minX = pos.getX();

        while (level.getBlockState(new BlockPos(minX - 1, pos.getY(), pos.getZ())).getBlock() instanceof RunePortalBlock) {
            minX--;
        }

        return minX - 1;
    }

    private static int getFrameMaxX(ServerLevel level, BlockPos pos) {
        int maxX = pos.getX();

        while (level.getBlockState(new BlockPos(maxX + 1, pos.getY(), pos.getZ())).getBlock() instanceof RunePortalBlock) {
            maxX++;
        }

        return maxX + 1;
    }

    private static int getFrameMinY(ServerLevel level, BlockPos pos) {
        int minY = pos.getY();

        while (level.getBlockState(new BlockPos(pos.getX(), minY - 1, pos.getZ())).getBlock() instanceof RunePortalBlock) {
            minY--;
        }

        return minY - 1;
    }

    private static int getFrameMaxY(ServerLevel level, BlockPos pos) {
        int maxY = pos.getY();

        while (level.getBlockState(new BlockPos(pos.getX(), maxY + 1, pos.getZ())).getBlock() instanceof RunePortalBlock) {
            maxY++;
        }

        return maxY + 1;
    }

    private static int getFrameMinZ(ServerLevel level, BlockPos pos) {
        int minZ = pos.getZ();

        while (level.getBlockState(new BlockPos(pos.getX(), pos.getY(), minZ - 1)).getBlock() instanceof RunePortalBlock) {
            minZ--;
        }

        return minZ - 1;
    }

    private static int getFrameMaxZ(ServerLevel level, BlockPos pos) {
        int maxZ = pos.getZ();

        while (level.getBlockState(new BlockPos(pos.getX(), pos.getY(), maxZ + 1)).getBlock() instanceof RunePortalBlock) {
            maxZ++;
        }

        return maxZ + 1;
    }

    private static RunestoneType getRunegateType(ServerLevel level, FrameBounds bounds, Direction.Axis axis) {
        BlockPos[] cornerPositions;

        if (axis == Direction.Axis.X) {
            cornerPositions = new BlockPos[]{
                    new BlockPos(bounds.minX, bounds.minY, bounds.minZ),
                    new BlockPos(bounds.maxX, bounds.minY, bounds.minZ),
                    new BlockPos(bounds.minX, bounds.maxY, bounds.minZ),
                    new BlockPos(bounds.maxX, bounds.maxY, bounds.minZ)
            };
        } else {
            cornerPositions = new BlockPos[]{
                    new BlockPos(bounds.minX, bounds.minY, bounds.minZ),
                    new BlockPos(bounds.minX, bounds.minY, bounds.maxZ),
                    new BlockPos(bounds.minX, bounds.maxY, bounds.minZ),
                    new BlockPos(bounds.minX, bounds.maxY, bounds.maxZ)
            };
        }

        boolean allMithril = true;
        boolean allAdamantium = true;
        boolean hasValidRunestone = false;

        for (BlockPos corner : cornerPositions) {
            BlockState state = level.getBlockState(corner);
            if (!state.is(IFWBlockTags.RUNESTONE)) {
                return RunestoneType.INVALID;
            }

            hasValidRunestone = true;
            String blockName = state.getBlock().getDescriptionId().toLowerCase();

            if (!blockName.contains("mithril")) {
                allMithril = false;
            }
            if (!blockName.contains("adamantium")) {
                allAdamantium = false;
            }
        }

        if (!hasValidRunestone) {
            return RunestoneType.INVALID;
        }

        if (allMithril) {
            return RunestoneType.MITHRIL_PURE;
        } else if (allAdamantium) {
            return RunestoneType.ADAMANTIUM_PURE;
        } else {
            return RunestoneType.MIXED_OR_INCOMPLETE;
        }
    }

    private static int getRunegateSeed(ServerLevel level, BlockPos portalPos, Direction.Axis axis) {
        FrameBounds bounds = getFrameBounds(level, portalPos, axis);
        if (bounds == null) return 0;

        if (axis == Direction.Axis.X) {
            int leftBottom = getRunestoneMetadata(level, new BlockPos(bounds.minX, bounds.minY, bounds.minZ));
            int rightBottom = getRunestoneMetadata(level, new BlockPos(bounds.maxX, bounds.minY, bounds.minZ));
            int leftTop = getRunestoneMetadata(level, new BlockPos(bounds.minX, bounds.maxY, bounds.minZ));
            int rightTop = getRunestoneMetadata(level, new BlockPos(bounds.maxX, bounds.maxY, bounds.minZ));

            int seed = leftBottom + (rightBottom << 4) + (leftTop << 8) + (rightTop << 12);
            return seed;
        } else {
            int leftBottom = getRunestoneMetadata(level, new BlockPos(bounds.minX, bounds.minY, bounds.minZ));
            int rightBottom = getRunestoneMetadata(level, new BlockPos(bounds.minX, bounds.minY, bounds.maxZ));
            int leftTop = getRunestoneMetadata(level, new BlockPos(bounds.minX, bounds.maxY, bounds.minZ));
            int rightTop = getRunestoneMetadata(level, new BlockPos(bounds.minX, bounds.maxY, bounds.maxZ));

            int seed = leftBottom + (rightBottom << 4) + (leftTop << 8) + (rightTop << 12);
            return seed;
        }
    }

    private static String getRunestoneTypeName(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(IFWBlockTags.RUNESTONE)) {
            return "非符文石";
        }

        String blockName = state.getBlock().getDescriptionId();
        String runestoneType = extractRunestoneType(blockName);
        return runestoneType.toUpperCase();
    }

    private static int getRunestoneMetadata(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(IFWBlockTags.RUNESTONE)) {
            return 0;
        }

        String blockName = state.getBlock().getDescriptionId();
        String runestoneType = extractRunestoneType(blockName);

        return RUNESTONE_METADATA.getOrDefault(runestoneType.toLowerCase(), 0);
    }

    private static String extractRunestoneType(String blockName) {
        String name = blockName.toLowerCase()
                .replace("block.ifw.", "")
                .replace("block.", "")
                .replace("ifw.", "")
                .replace("_runestone", "")
                .replace("runestone", "")
                .replace("_por", "")
                .replace("_block", "");

        if (name.contains("mithril_")) {
            name = name.replace("mithril_", "");
        } else if (name.contains("adamantium_")) {
            name = name.replace("adamantium_", "");
        }

        for (String type : RUNESTONE_METADATA.keySet()) {
            if (name.equals(type) || name.contains(type)) {
                return type;
            }
        }

        return "nul";
    }

    public static BlockPos calculateTeleportDestination(ServerLevel level, BlockPos portalPos, RunestoneType type) {
        switch (type) {
            case MITHRIL_PURE:
                return getRunegateDestinationCoords(level, portalPos, MITHRIL_DOMAIN_RADIUS);
            case ADAMANTIUM_PURE:
                return getRunegateDestinationCoords(level, portalPos, ADAMANTIUM_DOMAIN_RADIUS);
            default:
                return getSafeSpawnLocationInCurrentDimension(level);
        }
    }

    private static BlockPos getSafeSpawnLocationInCurrentDimension(ServerLevel level) {
        if (level.dimension() == Level.OVERWORLD) {
            return level.getSharedSpawnPos();
        } else if (level.dimension() == Level.NETHER) {
            return findSafeLocationInNether(level);
        } else if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
            return findSafeLocationInUnderworld(level);
        } else {
            return findSafeLocationInDimension(level);
        }
    }

    private static BlockPos findSafeLocationInNether(ServerLevel level) {
        BlockPos center = new BlockPos(0, 64, 0);

        for (int radius = 10; radius <= 100; radius += 10) {
            for (int attempts = 0; attempts < 20; attempts++) {
                int x = level.random.nextInt(radius * 2) - radius;
                int z = level.random.nextInt(radius * 2) - radius;

                for (int y = 32; y <= 100; y++) {
                    BlockPos testPos = new BlockPos(x, y, z);
                    if (isSafeForTeleportMITE(level, testPos)) {
                        return testPos;
                    }
                }
            }
        }

        return createSafeLocation(level, center);
    }

    private static BlockPos findSafeLocationInUnderworld(ServerLevel level) {
        BlockPos center = new BlockPos(0, 64, 0);

        for (int radius = 10; radius <= 100; radius += 10) {
            for (int attempts = 0; attempts < 20; attempts++) {
                int x = level.random.nextInt(radius * 2) - radius;
                int z = level.random.nextInt(radius * 2) - radius;

                for (int y = level.getMinBuildHeight() + 10; y <= level.getMaxBuildHeight() - 10; y++) {
                    BlockPos testPos = new BlockPos(x, y, z);
                    if (isSafeForTeleportMITE(level, testPos)) {
                        return testPos;
                    }
                }
            }
        }

        return createSafeLocation(level, center);
    }

    private static BlockPos findSafeLocationInDimension(ServerLevel level) {
        BlockPos center = new BlockPos(0, 64, 0);

        for (int radius = 10; radius <= 100; radius += 10) {
            for (int attempts = 0; attempts < 20; attempts++) {
                int x = level.random.nextInt(radius * 2) - radius;
                int z = level.random.nextInt(radius * 2) - radius;

                for (int y = level.getMinBuildHeight() + 10; y <= level.getMaxBuildHeight() - 10; y++) {
                    BlockPos testPos = new BlockPos(x, y, z);
                    if (isSafeForTeleportMITE(level, testPos)) {
                        return testPos;
                    }
                }
            }
        }

        return createSafeLocation(level, center);
    }

    private static BlockPos createSafeLocation(ServerLevel level, BlockPos center) {
        BlockPos safePos = new BlockPos(center.getX(), 64, center.getZ());

        level.setBlock(safePos, Blocks.AIR.defaultBlockState(), 2);
        level.setBlock(safePos.above(), Blocks.AIR.defaultBlockState(), 2);

        if (!level.getBlockState(safePos.below()).isSolid()) {
            level.setBlock(safePos.below(), Blocks.STONE.defaultBlockState(), 2);
        }

        return safePos;
    }

    private static BlockPos getRunegateDestinationCoords(ServerLevel level, BlockPos portalPos, int domainRadius) {
        Direction.Axis axis = level.getBlockState(portalPos).getValue(RunePortalBlock.AXIS);
        int seed = getRunegateSeed(level, portalPos, axis);

        int x, z;

        if (seed == 0) {
            x = 0;
            z = 0;
        } else {
            LegacyRandomGen random = new LegacyRandomGen(seed);

            x = 0; z = 0;
            for (int attempts = 0; attempts < 4; attempts++) {
                x = random.nextInt(domainRadius * 2) - domainRadius;
                z = random.nextInt(domainRadius * 2) - domainRadius;

                if (domainRadius == ADAMANTIUM_DOMAIN_RADIUS) {
                    double distanceFromCenter = Math.sqrt((double)x * x + (double)z * z);
                    if (distanceFromCenter >= domainRadius / 2.0) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        preloadChunks(level, x, z);

        BlockPos destination = findSafeLandingSpotMITE(level, x, z);

        return destination;
    }

    private static void preloadChunks(ServerLevel level, int x, int z) {
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                level.getChunk(chunkX + dx, chunkZ + dz);
            }
        }
    }

    private static BlockPos findSafeLandingSpotMITE(ServerLevel level, int x, int z) {
        for (int y = 50; y <= 80; y++) {
            BlockPos testPos = new BlockPos(x, y, z);
            if (isSafeForTeleportMITE(level, testPos)) {
                return testPos;
            }
        }

        for (int y = level.getMaxBuildHeight() - 1; y >= level.getMinBuildHeight() + 1; y--) {
            BlockPos testPos = new BlockPos(x, y, z);
            if (isSafeForTeleportMITE(level, testPos)) {
                return testPos;
            }
        }

        BlockPos fallback = new BlockPos(x, 64, z);
        level.setBlock(fallback, Blocks.AIR.defaultBlockState(), 2);
        level.setBlock(fallback.above(), Blocks.AIR.defaultBlockState(), 2);

        return fallback;
    }

    private static boolean isSafeForTeleportMITE(ServerLevel level, BlockPos pos) {
        BlockState feetState = level.getBlockState(pos);
        BlockState headState = level.getBlockState(pos.above());
        BlockState groundState = level.getBlockState(pos.below());

        boolean feetSafe = feetState.isAir() || feetState.is(Blocks.WATER);
        boolean headSafe = headState.isAir() || headState.is(Blocks.WATER);

        boolean groundSafe = groundState.isSolid() ||
                (groundState.is(Blocks.WATER) && isDeepWater(level, pos.below())) ||
                groundState.is(Blocks.WATER);

        if (groundState.is(Blocks.LAVA) || feetState.is(Blocks.LAVA) || headState.is(Blocks.LAVA)) {
            return false;
        }

        if (feetState.is(Blocks.CACTUS) || headState.is(Blocks.CACTUS) ||
                feetState.is(Blocks.FIRE) || headState.is(Blocks.FIRE)) {
            return false;
        }

        return feetSafe && headSafe && groundSafe;
    }

    private static boolean isDeepWater(ServerLevel level, BlockPos pos) {
        int waterDepth = 0;
        for (int i = 0; i < 5; i++) {
            if (level.getBlockState(pos.below(i)).is(Blocks.WATER)) {
                waterDepth++;
            } else {
                break;
            }
        }
        return waterDepth >= 2;
    }

    private static class FrameBounds {
        final int minX, maxX, minY, maxY, minZ, maxZ;

        FrameBounds(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }
    }

    public enum RunestoneType {
        MITHRIL_PURE,
        ADAMANTIUM_PURE,
        MIXED_OR_INCOMPLETE,
        INVALID
    }
}