package huix.infinity.common.world.dimension;

import com.mojang.serialization.MapCodec;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class IFWDimensions {
    public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCE_CODECS =
            DeferredRegister.create(Registries.BIOME_SOURCE, InfinityWay.MOD_ID);

    public static final DeferredRegister<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR_CODECS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, InfinityWay.MOD_ID);

    public static final Supplier<MapCodec<? extends BiomeSource>> UNDERWORLD_BIOME_SOURCE =
            BIOME_SOURCE_CODECS.register("underworld", () -> UnderworldBiomeSource.CODEC);

    public static final Supplier<MapCodec<? extends ChunkGenerator>> UNDERWORLD_CHUNK_GENERATOR =
            CHUNK_GENERATOR_CODECS.register("underworld", () -> UnderworldChunkGenerator.CODEC);

}