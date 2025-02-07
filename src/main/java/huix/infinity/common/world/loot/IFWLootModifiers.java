package huix.infinity.common.world.loot;

import com.mojang.serialization.MapCodec;
import huix.infinity.init.InfinityWay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class IFWLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, InfinityWay.MOD_ID);

    public static final Supplier<MapCodec<JungleLeavesLootModifier>> JUNGLE_LEAVES_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("jungle_leaves_drop", () -> JungleLeavesLootModifier.CODEC);

    public static final Supplier<MapCodec<OakLeavesLootModifier>> OAK_LEAVES_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("oak_leaves_drop", () -> OakLeavesLootModifier.CODEC);

}