package huix.infinity.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import java.util.List;

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
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && this.biomes.contains(biome)) {
            for (MobSpawnSettings.SpawnerData spawner : this.spawners) {
                // 添加带高度检查的生成数据
                builder.getMobSpawnSettings().addSpawn(spawner.type.getCategory(),
                        new HeightBasedSpawnerData(spawner, minHeight, maxHeight));
            }
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }

    public HolderSet<Biome> biomes() { return biomes; }
    public List<MobSpawnSettings.SpawnerData> spawners() { return spawners; }
    public int minHeight() { return minHeight; }
    public int maxHeight() { return maxHeight; }

    /**
     * 带高度检查的生成数据
     */
    public static class HeightBasedSpawnerData extends MobSpawnSettings.SpawnerData {
        private final int minHeight;
        private final int maxHeight;

        public HeightBasedSpawnerData(MobSpawnSettings.SpawnerData original, int minHeight, int maxHeight) {
            super(original.type, original.getWeight().asInt(), original.minCount, original.maxCount);
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        public boolean canSpawnAtHeight(int y) {
            return y >= minHeight && y <= maxHeight;
        }

        public int getMinHeight() { return minHeight; }
        public int getMaxHeight() { return maxHeight; }
    }
}