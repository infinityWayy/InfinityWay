package huix.infinity.init;

import com.mojang.serialization.MapCodec;
import huix.infinity.common.worldgen.HeightBasedAddSpawnsBiomeModifier;
import huix.infinity.datagen.worldgen.IFWBiomeModifiers.ModifySpawnsBiomeModifier;
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

    /**
     * 高度检测生物生成修饰器
     * 根据高度范围添加生物生成
     */
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<HeightBasedAddSpawnsBiomeModifier>> HEIGHT_BASED_ADD_SPAWNS =
            BIOME_MODIFIER_SERIALIZERS.register("height_based_add_spawns", () -> HeightBasedAddSpawnsBiomeModifier.CODEC);

    /**
     * 生物生成修改修饰器
     * 修改现有生物群系的生物生成参数
     */
    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ModifySpawnsBiomeModifier>> MODIFY_BIOME_SPAWNS =
            BIOME_MODIFIER_SERIALIZERS.register("modify_biome_spawns", () -> ModifySpawnsBiomeModifier.CODEC);
}