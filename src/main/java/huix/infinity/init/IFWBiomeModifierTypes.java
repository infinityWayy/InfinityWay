package huix.infinity.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import huix.infinity.common.worldgen.HeightBasedAddSpawnsBiomeModifier;
import huix.infinity.datagen.worldgen.IFWBiomeModifiers.ModifySpawnsBiomeModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * 统一的 BiomeModifier 序列化器注册中心
 * 所有自定义 BiomeModifier 的序列化器都在这里注册
 */
public class IFWBiomeModifierTypes {

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, InfinityWay.MOD_ID);

    // 高度检测生物生成修饰器
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<HeightBasedAddSpawnsBiomeModifier>> HEIGHT_BASED_ADD_SPAWNS =
            BIOME_MODIFIER_SERIALIZERS.register("height_based_add_spawns", () -> HeightBasedAddSpawnsBiomeModifier.CODEC);

    // 生物生成修改修饰器
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ModifySpawnsBiomeModifier>> MODIFY_BIOME_SPAWNS =
            BIOME_MODIFIER_SERIALIZERS.register("modify_biome_spawns", () -> RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModifySpawnsBiomeModifier::biomes),
                            Codec.unboundedMap(ResourceLocation.CODEC, MobSpawnSettings.SpawnerData.CODEC)
                                    .fieldOf("modifications").forGetter(ModifySpawnsBiomeModifier::modifications)
                    ).apply(instance, ModifySpawnsBiomeModifier::new)
            ));
}