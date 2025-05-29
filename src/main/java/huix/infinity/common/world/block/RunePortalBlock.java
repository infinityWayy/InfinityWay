package huix.infinity.common.world.block;

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
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
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

        return !flag && !facingState.is(this) && !(new PortalShape(level, currentPos, direction$axis1)).isComplete()
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
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
        if (!level.isClientSide && entity instanceof ServerPlayer player && level.dimension() == Level.OVERWORLD) {
            UUID playerId = player.getUUID();
            long currentTime = level.getGameTime();

            // 检查传送冷却
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
                    executeActualTeleport(player, (ServerLevel) level);
                    TELEPORT_START_TIME.remove(playerId);
                    TELEPORT_COOLDOWN.put(playerId, currentTime);
                }
                return;
            }

            TELEPORT_START_TIME.put(playerId, currentTime);
            startTeleportBuffer(player, (ServerLevel) level, pos);
        }
    }

    /**
     * 开始传送缓冲效果
     */
    private void startTeleportBuffer(ServerPlayer player, ServerLevel level, BlockPos portalPos) {
        level.playSound(null, portalPos.getX(), portalPos.getY(), portalPos.getZ(),
                SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0F, 1.2F);
        createTeleportParticles(level, portalPos);
    }

    /**
     * 创建传送粒子效果
     */
    private void createTeleportParticles(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 50; i++) {
            double x = pos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 4;
            double y = pos.getY() + level.random.nextDouble() * 3;
            double z = pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 4;

            level.sendParticles(RUNE_PARTICLE, x, y, z, 1, 0, 0.5, 0, 0.1);
        }
    }

    /**
     * 执行实际传送
     */
    private void executeActualTeleport(ServerPlayer player, ServerLevel level) {
        // 直接获取世界重生点并传送
        BlockPos spawnPos = level.getSharedSpawnPos();

        player.teleportTo(
                spawnPos.getX() + 0.5,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5
        );

        level.playSound(null, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(),
                SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.8F);

        createArrivalParticles(level, spawnPos);
    }

    /**
     * 创建到达粒子效果
     */
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