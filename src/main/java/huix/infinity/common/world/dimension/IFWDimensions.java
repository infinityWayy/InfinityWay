package huix.infinity.common.world.dimension;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class IFWDimensions {
    // 注册 BiomeSource 的 Codec
    public static final DeferredRegister<com.mojang.serialization.MapCodec<? extends BiomeSource>> BIOME_SOURCE_CODECS =
            DeferredRegister.create(Registries.BIOME_SOURCE, InfinityWay.MOD_ID);

    // 注册 ChunkGenerator 的 Codec
    public static final DeferredRegister<com.mojang.serialization.MapCodec<? extends ChunkGenerator>> CHUNK_GENERATOR_CODECS =
            DeferredRegister.create(Registries.CHUNK_GENERATOR, InfinityWay.MOD_ID);

    public static final Supplier<com.mojang.serialization.MapCodec<? extends BiomeSource>> UNDERWORLD_BIOME_SOURCE =
            BIOME_SOURCE_CODECS.register("underworld", () -> UnderworldBiomeSource.CODEC);

    public static final Supplier<com.mojang.serialization.MapCodec<? extends ChunkGenerator>> UNDERWORLD_CHUNK_GENERATOR =
            CHUNK_GENERATOR_CODECS.register("underworld", () -> UnderworldChunkGenerator.CODEC);

    // 确保注册被调用
    public static void init() {
        InfinityWay.LOGGER.info("Initializing IFW Dimensions...");
        // 触发静态初始化
        InfinityWay.LOGGER.info("Registered {} biome source codecs", BIOME_SOURCE_CODECS.getEntries().size());
        InfinityWay.LOGGER.info("Registered {} chunk generator codecs", CHUNK_GENERATOR_CODECS.getEntries().size());
    }
}