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

    private final Holder<Biome> underworld;

    public UnderworldBiomeSource(HolderGetter<Biome> biomeGetter) {
        super();
        try {
            this.underworld = biomeGetter.getOrThrow(UnderworldBiomes.UNDER_WORLD);
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
        return Stream.of(underworld);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        return underworld;
    }
}