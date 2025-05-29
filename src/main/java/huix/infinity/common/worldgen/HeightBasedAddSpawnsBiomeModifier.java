package huix.infinity.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import huix.infinity.init.IFWBiomeModifierTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 基于高度的生物生成修饰器
 * 在指定高度范围内为生物群系添加生物生成
 * 注意：实际的高度检查通过事件系统实现
 */
public class HeightBasedAddSpawnsBiomeModifier implements BiomeModifier {

    public static final MapCodec<HeightBasedAddSpawnsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(HeightBasedAddSpawnsBiomeModifier::biomes),
                    MobSpawnSettings.SpawnerData.CODEC.listOf().fieldOf("spawners").forGetter(HeightBasedAddSpawnsBiomeModifier::spawners),
                    com.mojang.serialization.Codec.INT.fieldOf("min_height").forGetter(HeightBasedAddSpawnsBiomeModifier::minHeight),
                    com.mojang.serialization.Codec.INT.fieldOf("max_height").forGetter(HeightBasedAddSpawnsBiomeModifier::maxHeight)
            ).apply(builder, HeightBasedAddSpawnsBiomeModifier::new)
    );

    private final HolderSet<Biome> biomes;
    private final List<MobSpawnSettings.SpawnerData> spawners;
    private final int minHeight;
    private final int maxHeight;

    public HeightBasedAddSpawnsBiomeModifier(HolderSet<Biome> biomes, List<MobSpawnSettings.SpawnerData> spawners, int minHeight, int maxHeight) {
        this.biomes = biomes;
        this.spawners = spawners;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (phase == Phase.ADD && this.biomes.contains(biome)) {
            // 直接添加生成数据，高度检查将通过事件系统处理
            for (MobSpawnSettings.SpawnerData spawner : this.spawners) {
                MobCategory category = spawner.type.getCategory();
                builder.getMobSpawnSettings().addSpawn(category, spawner);
            }
        }
    }

    @Override
    public @NotNull MapCodec<? extends BiomeModifier> codec() {
        return IFWBiomeModifierTypes.HEIGHT_BASED_ADD_SPAWNS.get();
    }

    // Getter 方法
    public HolderSet<Biome> biomes() {
        return biomes;
    }

    public List<MobSpawnSettings.SpawnerData> spawners() {
        return spawners;
    }

    public int minHeight() {
        return minHeight;
    }

    public int maxHeight() {
        return maxHeight;
    }

    /**
     * 检查给定高度是否在允许范围内
     * 这个方法将在生成事件中使用
     */
    public boolean isValidHeight(int y) {
        return y >= minHeight && y <= maxHeight;
    }
}