package huix.infinity.common.world.entity;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.block.entity.AnvilBlockEntity;
import huix.infinity.common.world.block.entity.IFWFurnaceBlockEntity;
import huix.infinity.init.InfinityWay;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, InfinityWay.MOD_ID);


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IFWFurnaceBlockEntity>> ifw_furnace = BLOCK_ENTITY_TYPES.register(
            "ifw_furnace", () -> BlockEntityType.Builder.of(IFWFurnaceBlockEntity::new
                    , IFWBlocks.clay_furnace.get(), IFWBlocks.hardened_clay_furnace.get(), IFWBlocks.sandstone_furnace.get()
                    , IFWBlocks.stone_furnace.get(), IFWBlocks.obsidian_furnace.get(), IFWBlocks.netherrack_furnace.get())
                            .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "ifw_furnace")));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnvilBlockEntity>> ifw_anvil = BLOCK_ENTITY_TYPES.register(
            "ifw_anvil", () -> BlockEntityType.Builder.of(AnvilBlockEntity::new,
                            IFWBlocks.copper_anvil.get(), IFWBlocks.chipped_copper_anvil.get(), IFWBlocks.damaged_copper_anvil.get(),
                            IFWBlocks.silver_anvil.get(), IFWBlocks.chipped_silver_anvil.get(), IFWBlocks.damaged_silver_anvil.get(),
                            IFWBlocks.gold_anvil.get(), IFWBlocks.chipped_gold_anvil.get(), IFWBlocks.damaged_gold_anvil.get(),
                            IFWBlocks.iron_anvil.get(), IFWBlocks.chipped_iron_anvil.get(), IFWBlocks.damaged_iron_anvil.get(),
                            IFWBlocks.ancient_metal_anvil.get(), IFWBlocks.chipped_ancient_metal_anvil.get(), IFWBlocks.damaged_ancient_metal_anvil.get(),
                            IFWBlocks.mithril_anvil.get(), IFWBlocks.chipped_mithril_anvil.get(), IFWBlocks.damaged_mithril_anvil.get(),
                            IFWBlocks.adamantium_anvil.get(), IFWBlocks.chipped_adamantium_anvil.get(), IFWBlocks.damaged_adamantium_anvil.get())
                    .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "ifw_anvil")));
}
