//package huix.infinity.common.world.block;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.core.particles.DustParticleOptions;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.server.TickTask;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.StateDefinition;
//import net.minecraft.world.level.block.state.properties.BlockStateProperties;
//import net.minecraft.world.level.block.state.properties.EnumProperty;
//import net.minecraft.world.level.block.state.properties.IntegerProperty;
//import net.minecraft.world.level.material.MapColor;
//import net.minecraft.world.level.material.PushReaction;
//import net.minecraft.world.level.portal.DimensionTransition;
//import net.minecraft.world.level.portal.PortalShape;
//import net.minecraft.world.phys.Vec3;
//import net.minecraft.world.phys.shapes.CollisionContext;
//import net.minecraft.world.phys.shapes.VoxelShape;
//import org.joml.Vector3f;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//public class CrossDimensionPortalBlock extends Block {
//    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
//    public static final IntegerProperty COLOR_TYPE = IntegerProperty.create("color_type", 0, 2);
//
//    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
//    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
//
//    private static final DustParticleOptions DEEP_BLUE_PARTICLE = new DustParticleOptions(
//            new Vector3f(0.1F, 0.4F, 0.9F), 1.2F);
//    private static final DustParticleOptions RED_PARTICLE = new DustParticleOptions(
//            new Vector3f(0.9F, 0.1F, 0.1F), 1.2F);
//    private static final DustParticleOptions DEFAULT_PARTICLE = new DustParticleOptions(
//            new Vector3f(0.8F, 0.2F, 1.0F), 1.2F);
//
//    private static final Map<UUID, Long> TELEPORT_START_TIME = new HashMap<>();
//    private static final Map<UUID, Long> TELEPORT_COOLDOWN = new HashMap<>();
//    private static final int TELEPORT_DELAY_TICKS = 20;
//    private static final int COOLDOWN_TICKS = 60;
//
//    public CrossDimensionPortalBlock(Properties properties) {
//        super(properties.mapColor(MapColor.COLOR_PURPLE)
//                .noCollission()
//                .randomTicks()
//                .strength(-1.0F)
//                .sound(net.minecraft.world.level.block.SoundType.GLASS)
//                .lightLevel((state) -> 15)
//                .pushReaction(PushReaction.BLOCK));
//        this.registerDefaultState(this.stateDefinition.any()
//                .setValue(AXIS, Direction.Axis.X)
//                .setValue(COLOR_TYPE, 2));
//    }
//
//    @Override
//    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
//        switch (state.getValue(AXIS)) {
//            case Z:
//                return Z_AXIS_AABB;
//            case X:
//            default:
//                return X_AXIS_AABB;
//        }
//    }
//
//    @Override
//    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
//                                     LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
//        Direction.Axis direction$axis = facing.getAxis();
//        Direction.Axis direction$axis1 = state.getValue(AXIS);
//        boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
//
//        if (!flag && !facingState.is(this) && !facingState.is(Blocks.OBSIDIAN)) {
//            return Blocks.AIR.defaultBlockState();
//        }
//
//        if (level instanceof ServerLevel serverLevel) {
//            serverLevel.getServer().tell(new TickTask(serverLevel.getServer().getTickCount() + 2, () -> {
//                updatePortalColorSafely(serverLevel, currentPos);
//            }));
//        }
//
//        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
//    }
//
//    private void updatePortalColorSafely(ServerLevel level, BlockPos pos) {
//        try {
//            BlockState currentState = level.getBlockState(pos);
//            if (!currentState.is(this)) return;
//
//            int newColorType = determinePortalColorType(level, pos);
//            int currentColorType = currentState.getValue(COLOR_TYPE);
//
//            if (currentColorType != newColorType) {
//                BlockState newState = currentState.setValue(COLOR_TYPE, newColorType);
//                level.setBlock(pos, newState, 3);
//
//                updateEntirePortalColor(level, pos, newColorType);
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    private void updateEntirePortalColor(ServerLevel level, BlockPos pos, int colorType) {
//        Optional<PortalShape> shape = findPortalShapeContaining(level, pos);
//        if (shape.isPresent()) {
//            updatePortalShapeColor(level, shape.get(), colorType);
//        }
//    }
//
//    private Optional<PortalShape> findPortalShapeContaining(ServerLevel level, BlockPos pos) {
//        for (int dx = -2; dx <= 2; dx++) {
//            for (int dy = -3; dy <= 3; dy++) {
//                for (int dz = -2; dz <= 2; dz++) {
//                    BlockPos searchPos = pos.offset(dx, dy, dz);
//                    Optional<PortalShape> shape = PortalShape.findEmptyPortalShape(level, searchPos, Direction.Axis.X);
//                    if (!shape.isPresent()) {
//                        shape = PortalShape.findEmptyPortalShape(level, searchPos, Direction.Axis.Z);
//                    }
//                    if (shape.isPresent()) {
//                        return shape;
//                    }
//                }
//            }
//        }
//        return Optional.empty();
//    }
//
//    private void updatePortalShapeColor(ServerLevel level, PortalShape shape, int colorType) {
//        try {
//            java.lang.reflect.Field bottomLeftField = PortalShape.class.getDeclaredField("bottomLeft");
//            java.lang.reflect.Field widthField = PortalShape.class.getDeclaredField("width");
//            java.lang.reflect.Field heightField = PortalShape.class.getDeclaredField("height");
//            java.lang.reflect.Field axisField = PortalShape.class.getDeclaredField("axis");
//
//            bottomLeftField.setAccessible(true);
//            widthField.setAccessible(true);
//            heightField.setAccessible(true);
//            axisField.setAccessible(true);
//
//            BlockPos bottomLeft = (BlockPos) bottomLeftField.get(shape);
//            int width = widthField.getInt(shape);
//            int height = heightField.getInt(shape);
//            Direction.Axis axis = (Direction.Axis) axisField.get(shape);
//
//            BlockPos.betweenClosed(
//                    bottomLeft,
//                    bottomLeft.relative(Direction.UP, height - 1)
//                            .relative(axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH, width - 1)
//            ).forEach(blockPos -> {
//                BlockState state = level.getBlockState(blockPos);
//                if (state.is(this)) {
//                    BlockState newState = state.setValue(COLOR_TYPE, colorType);
//                    level.setBlock(blockPos, newState, 2);
//                }
//            });
//
//        } catch (Exception e) {
//        }
//    }
//
//    @Override
//    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
//        if (random.nextInt(100) == 0) {
//            level.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D,
//                    SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 0.3F, 0.6F + random.nextFloat() * 0.8F, false);
//        }
//
//        int colorType = state.getValue(COLOR_TYPE);
//        DustParticleOptions portalParticle = getParticleByColorType(colorType);
//
//        for (int i = 0; i < 8; ++i) {
//            double x = (double)pos.getX() + random.nextDouble();
//            double y = (double)pos.getY() + random.nextDouble();
//            double z = (double)pos.getZ() + random.nextDouble();
//            double xSpeed = ((double)random.nextFloat() - 0.5D) * 1.0D;
//            double ySpeed = ((double)random.nextFloat() - 0.5D) * 1.0D;
//            double zSpeed = ((double)random.nextFloat() - 0.5D) * 1.0D;
//            int j = random.nextInt(2) * 2 - 1;
//
//            if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
//                x = (double)pos.getX() + 0.5D + 0.25D * (double)j;
//                xSpeed = (double)(random.nextFloat() * 2.0F * (float)j);
//            } else {
//                z = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
//                zSpeed = (double)(random.nextFloat() * 2.0F * (float)j);
//            }
//
//            level.addParticle(portalParticle, x, y, z, xSpeed, ySpeed, zSpeed);
//        }
//    }
//
//    @Override
//    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
//        updatePortalColorSafely(level, pos);
//    }
//
//    private int determinePortalColorType(ServerLevel level, BlockPos pos) {
//        if (level.dimension() == Level.OVERWORLD) {
//            return 0;
//        } else if (level.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
//            boolean mantleAdjacent = isAdjacentToMantle(level, pos);
//            return mantleAdjacent ? 1 : 0;
//        } else if (level.dimension() == Level.NETHER) {
//            return 0;
//        }
//        return 2;
//    }
//
//    private DustParticleOptions getParticleByColorType(int colorType) {
//        switch (colorType) {
//            case 0: return DEEP_BLUE_PARTICLE;
//            case 1: return RED_PARTICLE;
//            default: return DEFAULT_PARTICLE;
//        }
//    }
//
//    @Override
//    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
//        if (!level.isClientSide && entity instanceof ServerPlayer player) {
//            UUID playerId = player.getUUID();
//            long currentTime = level.getGameTime();
//
//            if (TELEPORT_COOLDOWN.containsKey(playerId)) {
//                long lastTeleport = TELEPORT_COOLDOWN.get(playerId);
//                if (currentTime - lastTeleport < COOLDOWN_TICKS) {
//                    return;
//                }
//                TELEPORT_COOLDOWN.remove(playerId);
//            }
//
//            if (TELEPORT_START_TIME.containsKey(playerId)) {
//                long startTime = TELEPORT_START_TIME.get(playerId);
//                long elapsedTicks = currentTime - startTime;
//
//                if (elapsedTicks >= TELEPORT_DELAY_TICKS) {
//                    executeCrossDimensionTeleport(player, (ServerLevel) level, pos);
//                    TELEPORT_START_TIME.remove(playerId);
//                    TELEPORT_COOLDOWN.put(playerId, currentTime);
//                }
//                return;
//            }
//
//            TELEPORT_START_TIME.put(playerId, currentTime);
//            startTeleportBuffer(player, (ServerLevel) level, pos);
//        }
//    }
//
//    private void startTeleportBuffer(ServerPlayer player, ServerLevel level, BlockPos portalPos) {
//        level.playSound(null, portalPos.getX(), portalPos.getY(), portalPos.getZ(),
//                SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0F, 1.5F);
//        createTeleportParticles(level, portalPos);
//    }
//
//    private void createTeleportParticles(ServerLevel level, BlockPos pos) {
//        BlockState state = level.getBlockState(pos);
//        int colorType = state.getValue(COLOR_TYPE);
//        DustParticleOptions particle = getParticleByColorType(colorType);
//
//        for (int i = 0; i < 80; i++) {
//            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 6;
//            double y = pos.getY() + level.random.nextDouble() * 4;
//            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 6;
//
//            level.sendParticles(particle, x, y, z, 1, 0, 0.8, 0, 0.2);
//        }
//    }
//
//    private void executeCrossDimensionTeleport(ServerPlayer player, ServerLevel currentLevel, BlockPos portalPos) {
//        try {
//            ServerLevel targetLevel = determineTargetDimension(player, currentLevel, portalPos);
//            if (targetLevel == null) {
//                return;
//            }
//
//            BlockPos targetPos = calculateTargetPosition(currentLevel, targetLevel, portalPos, player.blockPosition());
//
//            DimensionTransition transition = new DimensionTransition(
//                    targetLevel,
//                    new Vec3(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5),
//                    Vec3.ZERO,
//                    player.getYRot(),
//                    player.getXRot(),
//                    DimensionTransition.DO_NOTHING
//            );
//
//            player.changeDimension(transition);
//
//            targetLevel.playSound(null, targetPos.getX(), targetPos.getY(), targetPos.getZ(),
//                    SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.6F);
//
//            BlockState state = currentLevel.getBlockState(portalPos);
//            int colorType = state.getValue(COLOR_TYPE);
//            DustParticleOptions particleColor = getParticleByColorType(colorType);
//            createArrivalParticles(targetLevel, targetPos, particleColor);
//
//        } catch (Exception e) {
//        }
//    }
//
//    private ServerLevel determineTargetDimension(ServerPlayer player, ServerLevel currentLevel, BlockPos portalPos) {
//        if (currentLevel.dimension() == Level.OVERWORLD) {
//            boolean bedrockAdjacent = isAdjacentToBedrock(currentLevel, portalPos);
//            if (bedrockAdjacent) {
//                return currentLevel.getServer().getLevel(IFWDimensionTypes.UNDERWORLD_LEVEL);
//            } else {
//                return null;
//            }
//        } else if (currentLevel.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
//            boolean mantleAdjacent = isAdjacentToMantle(currentLevel, portalPos);
//            if (mantleAdjacent) {
//                return currentLevel.getServer().getLevel(Level.NETHER);
//            } else {
//                return currentLevel.getServer().getLevel(Level.OVERWORLD);
//            }
//        } else if (currentLevel.dimension() == Level.NETHER) {
//            return currentLevel.getServer().getLevel(IFWDimensionTypes.UNDERWORLD_LEVEL);
//        }
//
//        return null;
//    }
//
//    private boolean isAdjacentToBedrock(ServerLevel level, BlockPos portalPos) {
//        return checkPortalAdjacentToBlock(level, portalPos, Blocks.BEDROCK);
//    }
//
//    private boolean isAdjacentToMantle(ServerLevel level, BlockPos portalPos) {
//        try {
//            Block mantleBlock = huix.infinity.common.world.block.IFWBlocks.mantle.get();
//            return checkPortalAdjacentToBlock(level, portalPos, mantleBlock);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private boolean checkPortalAdjacentToBlock(ServerLevel level, BlockPos portalPos, Block targetBlock) {
//        for (int dx = -3; dx <= 3; dx++) {
//            for (int dy = -3; dy <= 3; dy++) {
//                for (int dz = -3; dz <= 3; dz++) {
//                    BlockPos checkPos = portalPos.offset(dx, dy, dz);
//                    BlockState checkState = level.getBlockState(checkPos);
//
//                    if (checkState.is(Blocks.OBSIDIAN)) {
//                        for (Direction direction : Direction.values()) {
//                            BlockPos adjacentPos = checkPos.relative(direction);
//                            BlockState adjacentState = level.getBlockState(adjacentPos);
//
//                            if (adjacentState.is(targetBlock)) {
//                                return true;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private BlockPos calculateTargetPosition(ServerLevel fromLevel, ServerLevel toLevel, BlockPos portalPos, BlockPos playerPos) {
//        if (fromLevel.dimension() == Level.OVERWORLD && toLevel.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
//            return findSafeSpawnLocation(toLevel, playerPos);
//        } else if (fromLevel.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL && toLevel.dimension() == Level.NETHER) {
//            BlockPos netherPos = new BlockPos(playerPos.getX() / 8, playerPos.getY(), playerPos.getZ() / 8);
//            return findSafeSpawnLocation(toLevel, netherPos);
//        } else if (fromLevel.dimension() == Level.NETHER && toLevel.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL) {
//            BlockPos underworldPos = new BlockPos(playerPos.getX() * 8, playerPos.getY(), playerPos.getZ() * 8);
//            return findSafeSpawnLocation(toLevel, underworldPos);
//        } else if (fromLevel.dimension() == IFWDimensionTypes.UNDERWORLD_LEVEL && toLevel.dimension() == Level.OVERWORLD) {
//            return findSafeSpawnLocation(toLevel, playerPos);
//        }
//
//        return findSafeSpawnLocation(toLevel, playerPos);
//    }
//
//    private BlockPos findSafeSpawnLocation(ServerLevel level, BlockPos targetPos) {
//        for (int y = Math.min(targetPos.getY(), level.getMaxBuildHeight() - 10); y >= level.getMinBuildHeight() + 1; y--) {
//            BlockPos testPos = new BlockPos(targetPos.getX(), y, targetPos.getZ());
//            if (isSafeForTeleport(level, testPos)) {
//                return testPos;
//            }
//        }
//
//        BlockPos safePos = new BlockPos(targetPos.getX(), 64, targetPos.getZ());
//        level.setBlock(safePos, Blocks.AIR.defaultBlockState(), 2);
//        level.setBlock(safePos.above(), Blocks.AIR.defaultBlockState(), 2);
//
//        if (!level.getBlockState(safePos.below()).isSolid()) {
//            level.setBlock(safePos.below(), Blocks.STONE.defaultBlockState(), 2);
//        }
//
//        return safePos;
//    }
//
//    private boolean isSafeForTeleport(ServerLevel level, BlockPos pos) {
//        BlockState feetState = level.getBlockState(pos);
//        BlockState headState = level.getBlockState(pos.above());
//        BlockState groundState = level.getBlockState(pos.below());
//
//        boolean feetSafe = feetState.isAir() || !feetState.isSolid();
//        boolean headSafe = headState.isAir() || !headState.isSolid();
//        boolean groundSafe = groundState.isSolid();
//
//        if (groundState.is(Blocks.LAVA) || feetState.is(Blocks.LAVA) || headState.is(Blocks.LAVA) ||
//                feetState.is(Blocks.FIRE) || headState.is(Blocks.FIRE)) {
//            return false;
//        }
//
//        return feetSafe && headSafe && groundSafe;
//    }
//
//    private void createArrivalParticles(ServerLevel level, BlockPos pos, DustParticleOptions particleColor) {
//        for (int i = 0; i < 50; i++) {
//            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 3;
//            double y = pos.getY() + level.random.nextDouble() * 3;
//            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 3;
//
//            level.sendParticles(particleColor, x, y, z, 1, 0, 0.3, 0, 0.1);
//        }
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//        builder.add(AXIS, COLOR_TYPE);
//    }
//}