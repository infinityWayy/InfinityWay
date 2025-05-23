package huix.infinity.datagen.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import huix.infinity.common.worldgen.IFWFeatures;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class IFWBiomeModifiers extends DatapackBuiltinEntriesProvider {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, InfinityWay.MOD_ID);
    public static final Supplier<MapCodec<ModifySpawnsBiomeModifier>> MODIFY_BIOME_SPAWNS =
            BIOME_MODIFIERS.register("modify_biome_spawns", () -> RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModifySpawnsBiomeModifier::biomes),
                            Codec.unboundedMap(ResourceLocation.CODEC, MobSpawnSettings.SpawnerData.CODEC)
                                    .fieldOf("modifications").forGetter(ModifySpawnsBiomeModifier::modifications)
                    ).apply(instance, ModifySpawnsBiomeModifier::new)
            ));

    public IFWBiomeModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, IFWFeatures.BUILDER, Set.of(InfinityWay.MOD_ID));
    }

    @Override
    public @NotNull String getName() {
        return "Biome Modifier Registries: " + InfinityWay.MOD_ID;
    }
}
