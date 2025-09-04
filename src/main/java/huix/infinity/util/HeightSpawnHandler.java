package huix.infinity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.entity.Mob.checkMobSpawnRules;
import static net.minecraft.world.entity.monster.Monster.isDarkEnoughToSpawn;

public class HeightSpawnHandler {

    public static boolean checkShallowCaveSpawnRules(
            @NotNull EntityType<? extends Monster> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (spawnType == MobSpawnType.NATURAL) {
            int y = pos.getY();
            if (y > 63 || (y < 0 && random.nextFloat() < 0.8f)) {
                return false;
            }
        }
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkDeepSlateLayerSpawnRules(@NotNull EntityType<? extends Mob> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (spawnType == MobSpawnType.NATURAL) {
            if (pos.getY() > 0 || pos.getY() < -64) {
                return false;
            }
        }
        return HeightSpawnHandler.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkUniversalSpawnRules(@NotNull EntityType<? extends Monster> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (spawnType == MobSpawnType.NATURAL) {
            if (pos.getY() < -32 && random.nextFloat() < 0.3f) {
                return false;
            }
        }
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkSurfaceLayerSpawnRules(@NotNull EntityType<? extends Monster> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (spawnType == MobSpawnType.NATURAL) {
            int y = pos.getY();
            if (y < 0 && random.nextFloat() < 0.95f) {
                return false;
            } else if (y < 64 && random.nextFloat() < 0.7f) {
                return false;
            }
        }
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkWoodSpiderSpawnRules(@NotNull EntityType<? extends Monster> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockState state = level.getBlockState(pos.below());
        if (spawnType == MobSpawnType.NATURAL) {
            if (!state.is(BlockTags.LEAVES) && !state.is(BlockTags.LOGS)) {
                return false;
            }
        }
        return checkSurfaceLayerSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean checkMonsterSpawnRules(EntityType<? extends Mob> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL
                && (MobSpawnType.ignoresLightRequirements(spawnType) || isDarkEnoughToSpawn(level, pos, random))
                && checkMobSpawnRules(type, level, spawnType, pos, random);
    }
}
