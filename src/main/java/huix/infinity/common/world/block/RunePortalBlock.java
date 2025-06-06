package huix.infinity.common.world.block;

import huix.infinity.util.RunePortalUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class RunePortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    private static final DustParticleOptions RUNE_PARTICLE = new DustParticleOptions(
            new Vector3f(0.1F, 0.5F, 0.6F), 1.0F);

    private static final Map<UUID, Long> TELEPORT_START_TIME = new HashMap<>();
    private static final Map<UUID, Long> TELEPORT_COOLDOWN = new HashMap<>();
    private static final int TELEPORT_DELAY_TICKS = 20;
    private static final int COOLDOWN_TICKS = 60;

    public RunePortalBlock(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_BLACK)
                .noCollission()
                .randomTicks()
                .strength(-1.0F)
                .sound(net.minecraft.world.level.block.SoundType.GLASS)
                .lightLevel((state) -> 11)
                .pushReaction(PushReaction.BLOCK));
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AXIS)) {
            case Z:
                return Z_AXIS_AABB;
            case X:
            default:
                return X_AXIS_AABB;
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
                                     LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        Direction.Axis direction$axis = facing.getAxis();
        Direction.Axis direction$axis1 = state.getValue(AXIS);
        boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();

        if (!flag && !facingState.is(this) && !facingState.is(Blocks.OBSIDIAN) && !isRunestoneBlock(facingState)) {
            if (level instanceof ServerLevel serverLevel) {
                Optional<PortalShape> portalShape = findPortalShapeContaining(serverLevel, currentPos);
                if (!portalShape.isPresent() || !isPortalShapeComplete(serverLevel, portalShape.get())) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    private Optional<PortalShape> findPortalShapeContaining(ServerLevel level, BlockPos pos) {
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    BlockPos searchPos = pos.offset(dx, dy, dz);
                    Optional<PortalShape> shape = PortalShape.findEmptyPortalShape(level, searchPos, Direction.Axis.X);
                    if (!shape.isPresent()) {
                        shape = PortalShape.findEmptyPortalShape(level, searchPos, Direction.Axis.Z);
                    }
                    if (shape.isPresent()) {
                        return shape;
                    }
                }
            }
        }
        return Optional.empty();
    }

    private boolean isPortalShapeComplete(ServerLevel level, PortalShape shape) {
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

            for (int x = -1; x <= width; x++) {
                for (int y = -1; y <= height; y++) {
                    BlockPos framePos;
                    if (axis == Direction.Axis.X) {
                        framePos = bottomLeft.offset(x, y, 0);
                    } else {
                        framePos = bottomLeft.offset(0, y, x);
                    }

                    boolean isEdge = (x == -1 || x == width || y == -1 || y == height);

                    if (isEdge) {
                        BlockState frameState = level.getBlockState(framePos);
                        if (!frameState.is(Blocks.OBSIDIAN) && !isRunestoneBlock(frameState)) {
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

    private boolean isRunestoneBlock(BlockState state) {
        try {
            return state.is(huix.infinity.common.core.tag.IFWBlockTags.RUNESTONE);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D,
                    SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 0.3F, 0.8F + random.nextFloat() * 0.4F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double x = (double)pos.getX() + random.nextDouble();
            double y = (double)pos.getY() + random.nextDouble();
            double z = (double)pos.getZ() + random.nextDouble();
            double xSpeed = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double ySpeed = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double zSpeed = ((double)random.nextFloat() - 0.5D) * 0.5D;
            int j = random.nextInt(2) * 2 - 1;

            if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
                x = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                xSpeed = (double)(random.nextFloat() * 2.0F * (float)j);
            } else {
                z = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                zSpeed = (double)(random.nextFloat() * 2.0F * (float)j);
            }

            level.addParticle(RUNE_PARTICLE, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            UUID playerId = player.getUUID();
            long currentTime = level.getGameTime();

            if (TELEPORT_COOLDOWN.containsKey(playerId)) {
                long lastTeleport = TELEPORT_COOLDOWN.get(playerId);
                if (currentTime - lastTeleport < COOLDOWN_TICKS) {
                    return;
                }
                TELEPORT_COOLDOWN.remove(playerId);
            }

            if (TELEPORT_START_TIME.containsKey(playerId)) {
                long startTime = TELEPORT_START_TIME.get(playerId);
                long elapsedTicks = currentTime - startTime;

                if (elapsedTicks >= TELEPORT_DELAY_TICKS) {
                    executeRuneTeleport(player, (ServerLevel) level, pos, state);
                    TELEPORT_START_TIME.remove(playerId);
                    TELEPORT_COOLDOWN.put(playerId, currentTime);
                }
                return;
            }

            TELEPORT_START_TIME.put(playerId, currentTime);
            startTeleportBuffer(player, (ServerLevel) level, pos, state);
        }
    }

    private void startTeleportBuffer(ServerPlayer player, ServerLevel level, BlockPos portalPos, BlockState state) {
        level.playSound(null, portalPos.getX(), portalPos.getY(), portalPos.getZ(),
                SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0F, 1.2F);
        createTeleportParticles(level, portalPos);
    }

    private void createTeleportParticles(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 50; i++) {
            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 4;
            double y = pos.getY() + level.random.nextDouble() * 3;
            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 4;

            level.sendParticles(RUNE_PARTICLE, x, y, z, 1, 0, 0.5, 0, 0.1);
        }
    }

    private void executeRuneTeleport(ServerPlayer player, ServerLevel level, BlockPos portalPos, BlockState state) {
        try {
            RunePortalUtil.RunestoneType runeType = RunePortalUtil.detectPortalRunestones(level, portalPos);
            BlockPos destination = RunePortalUtil.calculateTeleportDestination(level, portalPos, runeType);

            player.teleportTo(
                    destination.getX() + 0.5,
                    destination.getY(),
                    destination.getZ() + 0.5
            );

            level.playSound(null, destination.getX(), destination.getY(), destination.getZ(),
                    SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.8F);

            createArrivalParticles(level, destination);

        } catch (Exception e) {
        }
    }

    private void createArrivalParticles(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 30; i++) {
            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 2;
            double y = pos.getY() + level.random.nextDouble() * 2;
            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 2;

            level.sendParticles(RUNE_PARTICLE, x, y, z, 1, 0, 0.2, 0, 0.05);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}