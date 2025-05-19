package huix.infinity.datagen.model;


import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class IFWBlockStateProvider extends BlockStateProvider {

    public IFWBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, InfinityWay.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile copperAnvil = models().withExistingParent("copper_anvil", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/copper_anvil_top"))
                .texture("body", modLoc("block/copper_anvil"))
                .texture("top", modLoc("block/copper_anvil_top"));
        getVariantBuilder(IFWBlocks.copper_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(copperAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.copper_anvil.get(), copperAnvil);
        ModelFile chippedCopperAnvil = models().withExistingParent("chipped_copper_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/chipped_copper_anvil_top"))
                .texture("body", modLoc("block/copper_anvil"))
                .texture("top", modLoc("block/chipped_copper_anvil_top"));
        getVariantBuilder(IFWBlocks.chipped_copper_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(chippedCopperAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.chipped_copper_anvil.get(), chippedCopperAnvil);
        ModelFile damagedCopperAnvil = models().withExistingParent("damaged_copper_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/damaged_copper_anvil_top"))
                .texture("body", modLoc("block/copper_anvil"))
                .texture("top", modLoc("block/damaged_copper_anvil_top"));
        getVariantBuilder(IFWBlocks.damaged_copper_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(damagedCopperAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.damaged_copper_anvil.get(), damagedCopperAnvil);
        ModelFile adamantiumAnvil = models().withExistingParent("adamantium_anvil", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/adamantium_anvil_top"))
                .texture("body", modLoc("block/adamantium_anvil"))
                .texture("top", modLoc("block/adamantium_anvil_top"));
        getVariantBuilder(IFWBlocks.adamantium_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(adamantiumAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.adamantium_anvil.get(), adamantiumAnvil);
        ModelFile chippedAdamantiumAnvil = models().withExistingParent("chipped_adamantium_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/chipped_adamantium_anvil_top"))
                .texture("body", modLoc("block/adamantium_anvil"))
                .texture("top", modLoc("block/chipped_adamantium_anvil_top"));
        getVariantBuilder(IFWBlocks.chipped_adamantium_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(chippedAdamantiumAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.chipped_adamantium_anvil.get(), chippedAdamantiumAnvil);
        ModelFile damagedAdamantiumAnvil = models().withExistingParent("damaged_adamantium_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/damaged_adamantium_anvil_top"))
                .texture("body", modLoc("block/adamantium_anvil"))
                .texture("top", modLoc("block/damaged_adamantium_anvil_top"));
        getVariantBuilder(IFWBlocks.damaged_adamantium_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(damagedAdamantiumAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.damaged_adamantium_anvil.get(), damagedAdamantiumAnvil);
        ModelFile mithrilAnvil = models().withExistingParent("mithril_anvil", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/mithril_anvil_top"))
                .texture("body", modLoc("block/mithril_anvil"))
                .texture("top", modLoc("block/mithril_anvil_top"));
        getVariantBuilder(IFWBlocks.mithril_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(mithrilAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.mithril_anvil.get(), mithrilAnvil);
        ModelFile chippedMithrilAnvil = models().withExistingParent("chipped_mithril_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/chipped_mithril_anvil_top"))
                .texture("body", modLoc("block/mithril_anvil"))
                .texture("top", modLoc("block/chipped_mithril_anvil_top"));
        getVariantBuilder(IFWBlocks.chipped_mithril_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(chippedMithrilAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.chipped_mithril_anvil.get(), chippedMithrilAnvil);
        ModelFile damagedMithrilAnvil = models().withExistingParent("damaged_mithril_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/damaged_mithril_anvil_top"))
                .texture("body", modLoc("block/mithril_anvil"))
                .texture("top", modLoc("block/damaged_mithril_anvil_top"));
        getVariantBuilder(IFWBlocks.damaged_mithril_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(damagedMithrilAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.damaged_mithril_anvil.get(), damagedMithrilAnvil);
        ModelFile ancientMetalAnvil = models().withExistingParent("ancient_metal_anvil", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/ancient_metal_anvil_top"))
                .texture("body", modLoc("block/ancient_metal_anvil"))
                .texture("top", modLoc("block/ancient_metal_anvil_top"));
        getVariantBuilder(IFWBlocks.ancient_metal_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(ancientMetalAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.ancient_metal_anvil.get(), ancientMetalAnvil);
        ModelFile chippedAncientMetalAnvil = models().withExistingParent("chipped_ancient_metal_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/chipped_ancient_metal_anvil_top"))
                .texture("body", modLoc("block/ancient_metal_anvil"))
                .texture("top", modLoc("block/chipped_ancient_metal_anvil_top"));
        getVariantBuilder(IFWBlocks.chipped_ancient_metal_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(chippedAncientMetalAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.chipped_ancient_metal_anvil.get(), chippedAncientMetalAnvil);
        ModelFile damagedAncientMetalAnvil = models().withExistingParent("damaged_ancient_metal_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/damaged_ancient_metal_anvil_top"))
                .texture("body", modLoc("block/ancient_metal_anvil"))
                .texture("top", modLoc("block/damaged_ancient_metal_anvil_top"));
        getVariantBuilder(IFWBlocks.damaged_ancient_metal_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(damagedAncientMetalAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.damaged_ancient_metal_anvil.get(), damagedAncientMetalAnvil);

        ModelFile ironAnvil = models().withExistingParent("iron_anvil", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/iron_anvil_top"))
                .texture("body", modLoc("block/iron_anvil"))
                .texture("top", modLoc("block/iron_anvil_top"));
        getVariantBuilder(IFWBlocks.iron_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(ironAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.iron_anvil.get(), ironAnvil);
        ModelFile chippedIronAnvil = models().withExistingParent("chipped_iron_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/chipped_iron_anvil_top"))
                .texture("body", modLoc("block/iron_anvil"))
                .texture("top", modLoc("block/chipped_iron_anvil_top"));
        getVariantBuilder(IFWBlocks.chipped_iron_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(chippedIronAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.chipped_iron_anvil.get(), chippedIronAnvil);
        ModelFile damagedIronAnvil = models().withExistingParent("damaged_iron_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/damaged_iron_anvil_top"))
                .texture("body", modLoc("block/iron_anvil"))
                .texture("top", modLoc("block/damaged_iron_anvil_top"));
        getVariantBuilder(IFWBlocks.damaged_iron_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(damagedIronAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.damaged_iron_anvil.get(), damagedIronAnvil);
        ModelFile goldAnvil = models().withExistingParent("gold_anvil", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/gold_anvil_top"))
                .texture("body", modLoc("block/gold_anvil"))
                .texture("top", modLoc("block/gold_anvil_top"));
        getVariantBuilder(IFWBlocks.gold_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(goldAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.gold_anvil.get(), goldAnvil);
        ModelFile chippedGoldAnvil = models().withExistingParent("chipped_gold_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/chipped_gold_anvil_top"))
                .texture("body", modLoc("block/gold_anvil"))
                .texture("top", modLoc("block/chipped_gold_anvil_top"));
        getVariantBuilder(IFWBlocks.chipped_gold_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(chippedGoldAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.chipped_gold_anvil.get(), chippedGoldAnvil);
        ModelFile damagedGoldAnvil = models().withExistingParent("damaged_gold_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/damaged_gold_anvil_top"))
                .texture("body", modLoc("block/gold_anvil"))
                .texture("top", modLoc("block/damaged_gold_anvil_top"));
        getVariantBuilder(IFWBlocks.damaged_gold_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(damagedGoldAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.damaged_gold_anvil.get(), damagedGoldAnvil);
        ModelFile silverAnvil = models().withExistingParent("silver_anvil", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/silver_anvil_top"))
                .texture("body", modLoc("block/silver_anvil"))
                .texture("top", modLoc("block/silver_anvil_top"));
        getVariantBuilder(IFWBlocks.silver_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(silverAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.silver_anvil.get(), silverAnvil);
        ModelFile chippedSilverAnvil = models().withExistingParent("chipped_silver_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/chipped_silver_anvil_top"))
                .texture("body", modLoc("block/silver_anvil"))
                .texture("top", modLoc("block/chipped_silver_anvil_top"));
        getVariantBuilder(IFWBlocks.chipped_silver_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(chippedSilverAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.chipped_silver_anvil.get(), chippedSilverAnvil);
        ModelFile damagedSilverAnvil = models().withExistingParent("damaged_silver_anvil_top", mcLoc("block/anvil"))
                .texture("particle", modLoc("block/damaged_silver_anvil_top"))
                .texture("body", modLoc("block/silver_anvil"))
                .texture("top", modLoc("block/damaged_silver_anvil_top"));
        getVariantBuilder(IFWBlocks.damaged_silver_anvil.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(damagedSilverAnvil).rotationY(((int) state.getValue(AnvilBlock.FACING).toYRot() + 180) % 360).build());
        simpleBlockItem(IFWBlocks.damaged_silver_anvil.get(), damagedSilverAnvil);

        simpleBlock(IFWBlocks.adamantium_block.get());
        simpleBlock(IFWBlocks.adamantium_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.adamantium_bars.get(), modLoc("block/adamantium_bars"), modLoc("block/adamantium_bars"));
        doorBlock((DoorBlock) IFWBlocks.adamantium_door.get(), "adamantium", modLoc("block/door_adamantium_upper"), modLoc("block/door_adamantium_upper"));
        simpleBlock(IFWBlocks.mithril_block.get());
        simpleBlock(IFWBlocks.mithril_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.mithril_bars.get(), modLoc("block/mithril_bars"), modLoc("block/mithril_bars"));
        doorBlock((DoorBlock) IFWBlocks.mithril_door.get(), "mithril", modLoc("block/door_mithril_upper"), modLoc("block/door_mithril_upper"));
        simpleBlock(IFWBlocks.ancient_metal_block.get());
        paneBlock((IronBarsBlock) IFWBlocks.ancient_metal_bars.get(), modLoc("block/ancient_metal_bars"), modLoc("block/ancient_metal_bars"));
        doorBlock((DoorBlock) IFWBlocks.ancient_metal_door.get(), "ancient_metal", modLoc("block/door_ancient_metal_upper"), modLoc("block/door_ancient_metal_upper"));
        paneBlock((IronBarsBlock) IFWBlocks.copper_bars.get(), modLoc("block/copper_bars"), modLoc("block/copper_bars"));
        paneBlock((IronBarsBlock) IFWBlocks.gold_bars.get(), modLoc("block/gold_bars"), modLoc("block/gold_bars"));
        doorBlock((DoorBlock) IFWBlocks.gold_door.get(), "gold", modLoc("block/door_gold_upper"), modLoc("block/door_gold_upper"));
        simpleBlock(IFWBlocks.silver_block.get());
        simpleBlock(IFWBlocks.silver_ore.get());
        paneBlock((IronBarsBlock) IFWBlocks.silver_bars.get(), modLoc("block/silver_bars"), modLoc("block/silver_bars"));
        doorBlock((DoorBlock) IFWBlocks.silver_door.get(), "silver", modLoc("block/door_silver_upper"), modLoc("block/door_silver_upper"));
        simpleBlock(IFWBlocks.raw_adamantium_block.get());
        simpleBlock(IFWBlocks.raw_mithril_block.get());
        simpleBlock(IFWBlocks.raw_silver_block.get());
        simpleBlock(IFWBlocks.deepslate_adamantium_ore.get());
        simpleBlock(IFWBlocks.deepslate_mithril_ore.get());
        simpleBlock(IFWBlocks.deepslate_silver_ore.get());
        Block stoneFurnaceBlock = IFWBlocks.stone_furnace.get();

        ModelFile blockModel = models().withExistingParent(
                        "block/emerald_enchanting_table",
                        mcLoc("block/block")
                )
                .texture("particle", modLoc("block/emerald_enchanting_table_bottom"))
                .texture("bottom", modLoc("block/emerald_enchanting_table_bottom"))
                .texture("top", modLoc("block/emerald_enchanting_table_top"))
                .texture("side", modLoc("block/emerald_enchanting_table_side"))
                .element()
                .from(0, 0, 0)
                .to(16, 12, 16)
                .face(Direction.DOWN)
                .texture("#bottom")
                .uvs(0, 0, 16, 16)
                .cullface(Direction.DOWN)
                .end()
                .face(Direction.UP)
                .texture("#top")
                .uvs(0, 0, 16, 16)
                .end()
                .face(Direction.NORTH)
                .texture("#side")
                .uvs(0, 4, 16, 16)
                .cullface(Direction.NORTH)
                .end()
                .face(Direction.SOUTH)
                .texture("#side")
                .uvs(0, 4, 16, 16)
                .cullface(Direction.SOUTH)
                .end()
                .face(Direction.WEST)
                .texture("#side")
                .uvs(0, 4, 16, 16)
                .cullface(Direction.WEST)
                .end()
                .face(Direction.EAST)
                .texture("#side")
                .uvs(0, 4, 16, 16)
                .cullface(Direction.EAST)
                .end()
                .end();
        // 注册方块模型和物品模型
        simpleBlock(IFWBlocks.emerald_enchanting_table.get(), blockModel);
        simpleBlockItem(IFWBlocks.emerald_enchanting_table.get(), blockModel);

        ModelFile stoneFurnaceModel = models().orientable("stone_furnace", modLoc("block/stone_furnace_side"),
                 modLoc("block/stone_furnace_front"), modLoc("block/stone_furnace_top"));
        ModelFile stoneFurnaceOnModel = models().orientable("stone_furnace_on", modLoc("block/stone_furnace_side"),
                modLoc("block/stone_furnace_front_on"), modLoc("block/stone_furnace_top"));
        horizontalBlock(stoneFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? stoneFurnaceOnModel : stoneFurnaceModel, 180);
        simpleBlockItem(stoneFurnaceBlock, stoneFurnaceModel);
        Block clayFurnaceBlock = IFWBlocks.clay_furnace.get();
        ModelFile clayFurnaceModel = models().orientable("clay_furnace", modLoc("block/clay_furnace_side"),
                modLoc("block/clay_furnace_front"), modLoc("block/clay_furnace_top"));
        ModelFile clayFurnaceOnModel = models().orientable("clay_furnace_on", modLoc("block/clay_furnace_side"),
                modLoc("block/clay_furnace_front_on"), modLoc("block/clay_furnace_top"));
        horizontalBlock(clayFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? clayFurnaceOnModel : clayFurnaceModel, 180);
        simpleBlockItem(clayFurnaceBlock, clayFurnaceModel);
        Block hardenedClayFurnaceBlock = IFWBlocks.hardened_clay_furnace.get();
        ModelFile hardenedClayFurnaceModel = models().orientable("hardened_clay_furnace", modLoc("block/hardened_clay_furnace_side"),
                modLoc("block/hardened_clay_furnace_front"), modLoc("block/hardened_clay_furnace_top"));
        ModelFile hardenedClayFurnaceOnModel = models().orientable("hardened_clay_furnace_on", modLoc("block/hardened_clay_furnace_side"),
                modLoc("block/hardened_clay_furnace_front_on"), modLoc("block/hardened_clay_furnace_top"));
        horizontalBlock(hardenedClayFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? hardenedClayFurnaceOnModel : hardenedClayFurnaceModel, 180);
        simpleBlockItem(hardenedClayFurnaceBlock, hardenedClayFurnaceModel);
        Block sandstoneFurnaceBlock = IFWBlocks.sandstone_furnace.get();
        ModelFile sandstoneFurnaceModel = models().orientable("sandstone_furnace", modLoc("block/sandstone_furnace_side"),
                modLoc("block/sandstone_furnace_front"), modLoc("block/sandstone_furnace_top"));
        ModelFile sandstoneFurnaceOnModel = models().orientable("sandstone_furnace_on", modLoc("block/sandstone_furnace_side"),
                modLoc("block/sandstone_furnace_front_on"), modLoc("block/sandstone_furnace_top"));
        horizontalBlock(sandstoneFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? sandstoneFurnaceOnModel : sandstoneFurnaceModel, 180);
        simpleBlockItem(sandstoneFurnaceBlock, sandstoneFurnaceModel);
        Block obsidianFurnaceBlock = IFWBlocks.obsidian_furnace.get();
        ModelFile obsidianFurnaceModel = models().orientable("obsidian_furnace", modLoc("block/obsidian_furnace_side"),
                modLoc("block/obsidian_furnace_front"), modLoc("block/obsidian_furnace_top"));
        ModelFile obsidianFurnaceOnModel = models().orientable("obsidian_furnace_on", modLoc("block/obsidian_furnace_side"),
                modLoc("block/obsidian_furnace_front_on"), modLoc("block/obsidian_furnace_top"));
        horizontalBlock(obsidianFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? obsidianFurnaceOnModel : obsidianFurnaceModel, 180);
        simpleBlockItem(obsidianFurnaceBlock, obsidianFurnaceModel);
        Block netherrackFurnaceBlock = IFWBlocks.netherrack_furnace.get();
        ModelFile netherrackFurnaceModel = models().orientable("netherrack_furnace", modLoc("block/netherrack_furnace_side"),
                modLoc("block/netherrack_furnace_front"), modLoc("block/netherrack_furnace_top"));
        ModelFile netherrackFurnaceOnModel = models().orientable("netherrack_furnace_on", modLoc("block/netherrack_furnace_side"),
                modLoc("block/netherrack_furnace_front_on"), modLoc("block/netherrack_furnace_top"));
        horizontalBlock(netherrackFurnaceBlock, state ->
                state.getValue(BlockStateProperties.LIT) ? netherrackFurnaceOnModel : netherrackFurnaceModel, 180);
        simpleBlockItem(netherrackFurnaceBlock, netherrackFurnaceModel);
    }

}
