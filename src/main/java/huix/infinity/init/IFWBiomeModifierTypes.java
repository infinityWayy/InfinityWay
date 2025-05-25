package huix.infinity.init;

import com.mojang.serialization.MapCodec;
import huix.infinity.common.worldgen.HeightBasedAddSpawnsBiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class IFWBiomeModifierTypes {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, InfinityWay.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<HeightBasedAddSpawnsBiomeModifier>> HEIGHT_BASED_ADD_SPAWNS =
            BIOME_MODIFIER_SERIALIZERS.register("height_based_add_spawns", () -> HeightBasedAddSpawnsBiomeModifier.CODEC);
}