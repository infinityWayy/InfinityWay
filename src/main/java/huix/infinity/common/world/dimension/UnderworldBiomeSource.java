package huix.infinity.common.world.dimension;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class UnderworldBiomeSource extends BiomeSource {
    public static final MapCodec<UnderworldBiomeSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    RegistryOps.retrieveGetter(Registries.BIOME)
            ).apply(instance, UnderworldBiomeSource::new)
    );

    private final Holder<Biome> underworldWasteland;
    private final Holder<Biome> stalagmiteForest;
    private final Holder<Biome> lushCaverns;
    private final Holder<Biome> deepDarkRealm;

    public UnderworldBiomeSource(HolderGetter<Biome> biomeGetter) {
        super();
        try {
            this.underworldWasteland = biomeGetter.getOrThrow(UnderworldBiomes.UNDERWORLD_WASTELAND);
            this.stalagmiteForest = biomeGetter.getOrThrow(UnderworldBiomes.STALAGMITE_FOREST);
            this.lushCaverns = biomeGetter.getOrThrow(UnderworldBiomes.LUSH_CAVERNS);
            this.deepDarkRealm = biomeGetter.getOrThrow(UnderworldBiomes.DEEP_DARK_REALM);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize UnderworldBiomeSource: " + e.getMessage(), e);
        }
    }

    @Override
    protected @NotNull MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of(underworldWasteland, stalagmiteForest, lushCaverns, deepDarkRealm);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        try {
            // 改进的生物群系分布算法，基于深度和距离
            int absX = Math.abs(x);
            int absZ = Math.abs(z);
            int distance = (int) Math.sqrt(absX * absX + absZ * absZ);

            // 深层区域 (Y < -2，即 Y < -32 在世界坐标中)
            if (y < -2) {
                return deepDarkRealm;
            }

            // 边缘区域也是深暗之域
            if (distance > 80) {
                return deepDarkRealm;
            }

            // 使用更复杂的噪声函数来分布生物群系
            int hash1 = Mth.murmurHash3Mixer(x * 1619 + z * 31337 + y * 52711);
            int hash2 = Mth.murmurHash3Mixer(x * 22695477 + z * 1343 + y * 114649);

            // 基于深度的生物群系偏好
            if (y < -1) { // Y < -16
                // 深层偏向荒地和钟乳石森林
                int biomeChoice = Math.abs(hash1) % 10;
                if (biomeChoice < 4) return underworldWasteland;
                if (biomeChoice < 7) return stalagmiteForest;
                if (biomeChoice < 9) return lushCaverns;
                return deepDarkRealm;
            } else if (y < 1) { // -16 <= Y < 16
                // 中层更平衡的分布
                int biomeChoice = Math.abs(hash2) % 8;
                if (biomeChoice < 3) return underworldWasteland;
                if (biomeChoice < 5) return stalagmiteForest;
                if (biomeChoice < 7) return lushCaverns;
                return deepDarkRealm;
            } else { // Y >= 16
                // 上层偏向繁茂洞穴
                int biomeChoice = Math.abs(hash1 ^ hash2) % 6;
                if (biomeChoice < 2) return underworldWasteland;
                if (biomeChoice < 3) return stalagmiteForest;
                if (biomeChoice < 5) return lushCaverns;
                return deepDarkRealm;
            }
        } catch (Exception e) {
            // 出错时返回默认生物群系
            return underworldWasteland != null ? underworldWasteland : deepDarkRealm;
        }
    }
}