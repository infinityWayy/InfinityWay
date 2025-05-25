package huix.infinity.datagen.worldgen;

import huix.infinity.common.worldgen.IFWFeatures;
import huix.infinity.init.InfinityWay;
import huix.infinity.init.IFWBiomeModifierTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * BiomeModifier 数据生成器
 * 负责生成数据包和实现具体的 BiomeModifier 类
 */
public class IFWBiomeModifiers extends DatapackBuiltinEntriesProvider {

    public IFWBiomeModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, IFWFeatures.BUILDER, Set.of(InfinityWay.MOD_ID));
    }

    @Override
    public @NotNull String getName() {
        return "Biome Modifier Registries: " + InfinityWay.MOD_ID;
    }

    /**
     * 生物生成修改器实现
     * 用于修改现有生物群系的生物生成设置
     */
    public static class ModifySpawnsBiomeModifier implements BiomeModifier {
        private final HolderSet<Biome> biomes;
        private final Map<ResourceLocation, MobSpawnSettings.SpawnerData> modifications;

        public ModifySpawnsBiomeModifier(HolderSet<Biome> biomes, Map<ResourceLocation, MobSpawnSettings.SpawnerData> modifications) {
            this.biomes = biomes;
            this.modifications = modifications;
        }

        @Override
        public void modify(net.minecraft.core.Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD && this.biomes.contains(biome)) {
                for (Map.Entry<ResourceLocation, MobSpawnSettings.SpawnerData> entry : modifications.entrySet()) {
                    MobSpawnSettings.SpawnerData spawnerData = entry.getValue();
                    builder.getMobSpawnSettings().addSpawn(spawnerData.type.getCategory(), spawnerData);
                }
            }
        }

        @Override
        public com.mojang.serialization.MapCodec<? extends BiomeModifier> codec() {
            return IFWBiomeModifierTypes.MODIFY_BIOME_SPAWNS.get(); // 使用统一注册器
        }

        public HolderSet<Biome> biomes() { return biomes; }
        public Map<ResourceLocation, MobSpawnSettings.SpawnerData> modifications() { return modifications; }
    }
}