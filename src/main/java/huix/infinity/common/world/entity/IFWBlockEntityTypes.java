package huix.infinity.common.world.entity;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.block.entity.AnvilBlockEntity;
import huix.infinity.common.world.block.entity.IFWFurnaceBlockEntity;
import huix.infinity.common.world.block.entity.LevelFurnaceBlockEntity;
import huix.infinity.init.InfinityWay;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;

public class IFWBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, InfinityWay.MOD_ID);


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<IFWFurnaceBlockEntity>> ifw_furnace = BLOCK_ENTITY_TYPES.register(
            "ifw_furnace", () -> BlockEntityType.Builder.of(IFWFurnaceBlockEntity::new
                    , IFWBlocks.clay_furnace.get(), IFWBlocks.hardened_clay_furnace.get(), IFWBlocks.sandstone_furnace.get()
                    , IFWBlocks.stone_furnace.get(), IFWBlocks.obsidian_furnace.get(), IFWBlocks.netherrack_furnace.get())
                            .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "ifw_furnace")));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AnvilBlockEntity>> ifw_anvil = BLOCK_ENTITY_TYPES.register(
            "ifw_anvil", () -> BlockEntityType.Builder.of(AnvilBlockEntity::new
                            , IFWBlocks.copper_anvil.get(), IFWBlocks.chipped_copper_anvil.get(), IFWBlocks.damaged_copper_anvil.get())
                    .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "ifw_anvil")));
}
