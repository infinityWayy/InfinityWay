package huix.infinity.datagen.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * 一个可以通过数据驱动方式修改指定生物群系的原版生物生成参数的BiomeModifier实现。
 * 支持多生物群系和多实体批量配置。
 */
public record ModifySpawnsBiomeModifier(
        HolderSet<Biome> biomes,
        Map<ResourceLocation, MobSpawnSettings.SpawnerData> modifications // key: 要修改的EntityType, value: 新的SpawnerData
) implements BiomeModifier {
    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (phase != Phase.ADD || !this.biomes.contains(biome) || modifications == null || modifications.isEmpty()) {
            return;
        }
        MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
        for (MobCategory cat : MobCategory.values()) {
            List<MobSpawnSettings.SpawnerData> list = spawns.getSpawner(cat);
            if (list.isEmpty()) continue;
            ListIterator<MobSpawnSettings.SpawnerData> it = list.listIterator();
            while (it.hasNext()) {
                MobSpawnSettings.SpawnerData oldData = it.next();
                ResourceLocation typeId = EntityType.getKey(oldData.type);
                MobSpawnSettings.SpawnerData newData = modifications.get(typeId);
                if (newData != null) {
                    it.set(newData);
                }
            }
        }
    }
    @Override
    public @NotNull MapCodec<? extends BiomeModifier> codec() {
        return IFWBiomeModifiers.MODIFY_BIOME_SPAWNS.get();
    }
}