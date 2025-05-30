package huix.infinity.common.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MantleBlock extends Block {

    private static final Map<UUID, Integer> PLAYER_EXPOSURE_TIME = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> LAST_DAMAGE_TIME = new ConcurrentHashMap<>();
    private static final Map<String, Long> REGION_MELT_CACHE = new ConcurrentHashMap<>();
    private static final int CACHE_CLEANUP_INTERVAL = 6000;
    private static long lastCacheCleanup = 0;

    private static final int EXPOSURE_THRESHOLD = 60;
    private static final double DAMAGE_RANGE = 3.0;
    private static final double MELT_RANGE = 3.0;
    private static final float DAMAGE_AMOUNT = 4.0F;
    private static final long DAMAGE_COOLDOWN = 2000;
    private static final long MELT_CHECK_COOLDOWN = 1000;
    private static final int MAX_MELTS_PER_TICK = 8;
    private static final int REGION_SIZE = 8;

    public MantleBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.randomTick(state, level, pos, random);

        this.checkAndDamageNearbyPlayers(level, pos);
        if (random.nextFloat() < 0.6F) {
            if (this.shouldProcessMelting(level, pos)) {
                this.meltNearbySnowAndIce(level, pos);
            }
        }
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide) {
            if (level instanceof ServerLevel serverLevel) {
                this.meltNearbySnowAndIce(serverLevel, pos);
            }
            level.scheduleTick(pos, this, 40);
        }
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.tick(state, level, pos, random);

        this.cleanupExpiredCache();

        this.checkAndDamageNearbyPlayers(level, pos);

        if (this.hasPlayersNearby(level, pos) || this.hasSnowIceNearby(level, pos)) {
            if (this.shouldProcessMelting(level, pos)) {
                this.meltNearbySnowAndIce(level, pos);
            }
        }

        level.scheduleTick(pos, this, 40);
    }

    private boolean hasPlayersNearby(ServerLevel level, BlockPos pos) {
        AABB searchArea = new AABB(pos).inflate(DAMAGE_RANGE * 2);
        return !level.getEntitiesOfClass(Player.class, searchArea).isEmpty();
    }

    private boolean hasSnowIceNearby(ServerLevel level, BlockPos pos) {
        int range = 3;
        for (int dx = -range; dx <= range; dx += 2) {
            for (int dy = 0; dy <= range; dy += 2) {
                for (int dz = -range; dz <= range; dz += 2) {
                    BlockPos checkPos = pos.offset(dx, dy, dz);
                    Block block = level.getBlockState(checkPos).getBlock();
                    if (this.shouldMeltBlock(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean shouldProcessMelting(ServerLevel level, BlockPos pos) {
        String regionKey = getRegionKey(pos);
        long currentTime = level.getGameTime();

        Long lastMelt = REGION_MELT_CACHE.get(regionKey);
        if (lastMelt != null && (currentTime - lastMelt) < MELT_CHECK_COOLDOWN / 20) {
            return false;
        }

        REGION_MELT_CACHE.put(regionKey, currentTime);
        return true;
    }

    private String getRegionKey(BlockPos pos) {
        int regionX = pos.getX() / REGION_SIZE;
        int regionZ = pos.getZ() / REGION_SIZE;
        int regionY = pos.getY() / 8;
        return regionX + "," + regionY + "," + regionZ;
    }

    private void cleanupExpiredCache() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCacheCleanup > CACHE_CLEANUP_INTERVAL) {
            long expireTime = currentTime - 300000;

            REGION_MELT_CACHE.entrySet().removeIf(entry ->
                    entry.getValue() < expireTime);

            lastCacheCleanup = currentTime;
        }
    }

    private void checkAndDamageNearbyPlayers(ServerLevel level, BlockPos mantlePos) {
        AABB searchArea = new AABB(mantlePos).inflate(DAMAGE_RANGE);
        List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, searchArea);

        for (Player player : nearbyPlayers) {
            if (this.isPlayerInDamageRange(player, mantlePos)) {
                UUID playerId = player.getUUID();
                long currentTime = System.currentTimeMillis();

                int exposureTime = PLAYER_EXPOSURE_TIME.getOrDefault(playerId, 0);
                exposureTime += 40;
                PLAYER_EXPOSURE_TIME.put(playerId, exposureTime);

                Long lastDamageTime = LAST_DAMAGE_TIME.get(playerId);
                boolean canDamage = lastDamageTime == null || (currentTime - lastDamageTime) >= DAMAGE_COOLDOWN;

                if (exposureTime >= EXPOSURE_THRESHOLD && canDamage) {
                    this.damagePlayer(player, level);
                    LAST_DAMAGE_TIME.put(playerId, currentTime);
                    PLAYER_EXPOSURE_TIME.put(playerId, EXPOSURE_THRESHOLD / 2);
                }
            } else {
                PLAYER_EXPOSURE_TIME.remove(player.getUUID());
            }
        }
        cleanupOfflinePlayers(level);
    }

    private boolean isPlayerInDamageRange(Player player, BlockPos mantlePos) {
        Vec3 playerPos = player.position();
        Vec3 mantleCenter = Vec3.atCenterOf(mantlePos);

        double distance = playerPos.distanceTo(mantleCenter);
        if (distance >= DAMAGE_RANGE) {
            return false;
        }

        double dx = Math.abs(playerPos.x - mantleCenter.x);
        double dy = playerPos.y - mantleCenter.y;
        double dz = Math.abs(playerPos.z - mantleCenter.z);

        if (dy < -1.0) {
            return false;
        }

        return dx < DAMAGE_RANGE && dz < DAMAGE_RANGE && dy < DAMAGE_RANGE;
    }

    private boolean isBlockInMeltRange(BlockPos blockPos, BlockPos mantlePos) {
        int dx = Math.abs(blockPos.getX() - mantlePos.getX());
        int dy = blockPos.getY() - mantlePos.getY();
        int dz = Math.abs(blockPos.getZ() - mantlePos.getZ());

        return dx <= 3 && dz <= 3 && dy >= -1 && dy <= 3;
    }

    private void damagePlayer(Player player, ServerLevel level) {
        DamageSource damageSource = new DamageSource(
                level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(DamageTypes.LAVA)
        );

        boolean damaged = player.hurt(damageSource, DAMAGE_AMOUNT);

        if (damaged) {
            player.setRemainingFireTicks(300);
            level.playSound(null, player.blockPosition(), SoundEvents.LAVA_AMBIENT,
                    SoundSource.BLOCKS, 0.5F, 0.8F + level.random.nextFloat() * 0.4F);

            for (int i = 0; i < 4; ++i) {
                level.sendParticles(net.minecraft.core.particles.ParticleTypes.FLAME,
                        player.getX() + (level.random.nextDouble() - 0.5) * 0.8,
                        player.getY() + level.random.nextDouble() * 1.8,
                        player.getZ() + (level.random.nextDouble() - 0.5) * 0.8,
                        1, 0.0, 0.1, 0.0, 0.0);
            }
        }
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (entity instanceof Player player) {
                UUID playerId = player.getUUID();
                Long lastDamageTime = LAST_DAMAGE_TIME.get(playerId);
                long currentTime = System.currentTimeMillis();

                if (lastDamageTime != null && (currentTime - lastDamageTime) < 1000) {
                    return;
                }
                LAST_DAMAGE_TIME.put(playerId, currentTime);
            }

            DamageSource damageSource = new DamageSource(
                    level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.LAVA)
            );

            boolean damaged = livingEntity.hurt(damageSource, DAMAGE_AMOUNT * 1.5F);

            if (damaged) {
                livingEntity.setRemainingFireTicks(400);
                level.playSound(null, pos, SoundEvents.LAVA_POP,
                        SoundSource.BLOCKS, 0.5F, 0.8F + level.random.nextFloat() * 0.4F);

                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 3; ++i) {
                        serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.LAVA,
                                pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                                1, 0.0, 0.1, 0.0, 0.0);
                    }
                }
            }
        }
        super.stepOn(level, pos, state, entity);
    }

    public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluidState) {
        if (fluidState.getType() == Fluids.WATER || fluidState.getType() == Fluids.FLOWING_WATER) {
            if (level instanceof ServerLevel serverLevel) {
                this.handleWaterContact(serverLevel, pos);
            }
            return false;
        }
        return true;
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (!level.isClientSide) {
            if (level instanceof ServerLevel serverLevel) {
                Block neighbor = level.getBlockState(neighborPos).getBlock();
                if (this.shouldMeltBlock(neighbor) && this.isBlockInMeltRange(neighborPos, pos)) {
                    this.meltBlock(serverLevel, neighborPos, neighbor);
                }
            }

            for (Direction direction : Direction.values()) {
                BlockPos adjacentPos = pos.relative(direction);
                BlockState adjacentState = level.getBlockState(adjacentPos);

                if (adjacentState.getBlock() == Blocks.WATER ||
                        adjacentState.getFluidState().getType() == Fluids.WATER ||
                        adjacentState.getFluidState().getType() == Fluids.FLOWING_WATER) {

                    level.setBlock(adjacentPos, Blocks.AIR.defaultBlockState(), 3);

                    if (level instanceof ServerLevel serverLevel) {
                        this.handleWaterContact(serverLevel, adjacentPos);
                    }
                }
            }
        }
    }

    private void meltNearbySnowAndIce(ServerLevel level, BlockPos mantlePos) {
        List<BlockPos> blocksToMelt = new ArrayList<>();

        int range = 3;
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = 0; dy <= range; dy++) {
                for (int dz = -range; dz <= range; dz++) {
                    BlockPos checkPos = mantlePos.offset(dx, dy, dz);

                    if (!this.isBlockInMeltRange(checkPos, mantlePos)) {
                        continue;
                    }

                    Block block = level.getBlockState(checkPos).getBlock();
                    if (this.shouldMeltBlock(block)) {
                        blocksToMelt.add(checkPos);
                        if (blocksToMelt.size() >= MAX_MELTS_PER_TICK) {
                            break;
                        }
                    }
                }
                if (blocksToMelt.size() >= MAX_MELTS_PER_TICK) break;
            }
            if (blocksToMelt.size() >= MAX_MELTS_PER_TICK) break;
        }

        for (BlockPos pos : blocksToMelt) {
            Block block = level.getBlockState(pos).getBlock();
            this.meltBlock(level, pos, block);
        }
    }

    private boolean shouldMeltBlock(Block block) {
        return block == Blocks.SNOW ||
                block == Blocks.SNOW_BLOCK ||
                block == Blocks.POWDER_SNOW ||
                block == Blocks.ICE ||
                block == Blocks.PACKED_ICE ||
                block == Blocks.BLUE_ICE ||
                block == Blocks.FROSTED_ICE;
    }

    private void meltBlock(ServerLevel level, BlockPos pos, Block block) {
        if (block == Blocks.SNOW) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            this.createMeltingEffects(level, pos, "snow");
        } else if (block == Blocks.SNOW_BLOCK) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
            this.createMeltingEffects(level, pos, "snow_block");
        } else if (block == Blocks.POWDER_SNOW) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
            this.createMeltingEffects(level, pos, "powder_snow");
        } else if (block == Blocks.ICE) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
            this.createMeltingEffects(level, pos, "ice");
        } else if (block == Blocks.FROSTED_ICE) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
            this.createMeltingEffects(level, pos, "frosted_ice");
        } else if (block == Blocks.PACKED_ICE) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
            this.createMeltingEffects(level, pos, "packed_ice");
        } else if (block == Blocks.BLUE_ICE) {
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
            this.createMeltingEffects(level, pos, "blue_ice");
        }
    }

    private void createMeltingEffects(ServerLevel level, BlockPos pos, String blockType) {
        switch (blockType) {
            case "snow":
                level.playSound(null, pos, SoundEvents.SNOW_BREAK,
                        SoundSource.BLOCKS, 0.3F, 1.0F + level.random.nextFloat() * 0.4F);
                level.sendParticles(net.minecraft.core.particles.ParticleTypes.CLOUD,
                        pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5,
                        1, 0.0, 0.05, 0.0, 0.0);
                break;
            case "snow_block":
                level.playSound(null, pos, SoundEvents.SNOW_BREAK,
                        SoundSource.BLOCKS, 0.4F, 0.8F + level.random.nextFloat() * 0.4F);
                level.sendParticles(net.minecraft.core.particles.ParticleTypes.CLOUD,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        2, 0.0, 0.1, 0.0, 0.0);
                break;
            case "powder_snow":
                level.playSound(null, pos, SoundEvents.POWDER_SNOW_BREAK,
                        SoundSource.BLOCKS, 0.4F, 0.9F + level.random.nextFloat() * 0.3F);
                level.sendParticles(net.minecraft.core.particles.ParticleTypes.WHITE_ASH,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        1, 0.0, 0.05, 0.0, 0.0);
                break;
            case "ice":
            case "frosted_ice":
            case "packed_ice":
            case "blue_ice":
                level.playSound(null, pos, SoundEvents.GLASS_BREAK,
                        SoundSource.BLOCKS, 0.4F, 1.2F + level.random.nextFloat() * 0.3F);
                level.sendParticles(net.minecraft.core.particles.ParticleTypes.CLOUD,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        1, 0.0, 0.1, 0.0, 0.0);
                break;
        }
    }

    private void handleWaterContact(ServerLevel level, BlockPos contactPos) {
        level.playSound(null, contactPos, SoundEvents.FIRE_EXTINGUISH,
                SoundSource.BLOCKS, 0.4F, 0.8F + level.random.nextFloat() * 0.4F);

        level.sendParticles(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE,
                contactPos.getX() + 0.5, contactPos.getY() + 0.5, contactPos.getZ() + 0.5,
                2, 0.0, 0.1, 0.0, 0.0);
    }

    private void cleanupOfflinePlayers(ServerLevel level) {
        PLAYER_EXPOSURE_TIME.entrySet().removeIf(entry -> {
            UUID playerId = entry.getKey();
            return level.getPlayerByUUID(playerId) == null;
        });

        LAST_DAMAGE_TIME.entrySet().removeIf(entry -> {
            UUID playerId = entry.getKey();
            return level.getPlayerByUUID(playerId) == null;
        });
    }

    public static void cleanupCacheData() {
        REGION_MELT_CACHE.clear();
    }

    public static void cleanupPlayerData(UUID playerId) {
        PLAYER_EXPOSURE_TIME.remove(playerId);
        LAST_DAMAGE_TIME.remove(playerId);
    }

    public static void clearAllPlayerData() {
        PLAYER_EXPOSURE_TIME.clear();
        LAST_DAMAGE_TIME.clear();
        REGION_MELT_CACHE.clear();
    }
}