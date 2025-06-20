package huix.infinity.datagen.loot;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class IFWBlockLootSubProvider extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    protected IFWBlockLootSubProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }

    @Override
    protected void generate() {
        dropSelf(IFWBlocks.copper_anvil.get());
        dropSelf(IFWBlocks.chipped_copper_anvil.get());
        dropSelf(IFWBlocks.damaged_copper_anvil.get());
        dropSelf(IFWBlocks.adamantium_anvil.get());
        dropSelf(IFWBlocks.chipped_adamantium_anvil.get());
        dropSelf(IFWBlocks.damaged_adamantium_anvil.get());
        dropSelf(IFWBlocks.mithril_anvil.get());
        dropSelf(IFWBlocks.chipped_mithril_anvil.get());
        dropSelf(IFWBlocks.damaged_mithril_anvil.get());
        dropSelf(IFWBlocks.ancient_metal_anvil.get());
        dropSelf(IFWBlocks.chipped_ancient_metal_anvil.get());
        dropSelf(IFWBlocks.damaged_ancient_metal_anvil.get());
        dropSelf(IFWBlocks.iron_anvil.get());
        dropSelf(IFWBlocks.chipped_iron_anvil.get());
        dropSelf(IFWBlocks.damaged_iron_anvil.get());
        dropSelf(IFWBlocks.gold_anvil.get());
        dropSelf(IFWBlocks.chipped_gold_anvil.get());
        dropSelf(IFWBlocks.damaged_gold_anvil.get());
        dropSelf(IFWBlocks.silver_anvil.get());
        dropSelf(IFWBlocks.chipped_silver_anvil.get());
        dropSelf(IFWBlocks.damaged_silver_anvil.get());
        dropSelf(IFWBlocks.emerald_enchanting_table.get());
        dropSelf(IFWBlocks.copper_private_chest.get());
        dropSelf(IFWBlocks.silver_private_chest.get());
        dropSelf(IFWBlocks.gold_private_chest.get());
        dropSelf(IFWBlocks.iron_private_chest.get());
        dropSelf(IFWBlocks.ancient_metal_private_chest.get());
        dropSelf(IFWBlocks.mithril_private_chest.get());
        dropSelf(IFWBlocks.adamantium_private_chest.get());
        add(IFWBlocks.adamantium_ore.get(), block -> this.createOreDrop(block, IFWItems.raw_adamantium.get()));
        add(IFWBlocks.deepslate_adamantium_ore.get(), block -> this.createOreDrop(block, IFWItems.raw_adamantium.get()));
        add(IFWBlocks.mithril_ore.get(), block -> this.createOreDrop(block, IFWItems.raw_mithril.get()));
        add(IFWBlocks.deepslate_mithril_ore.get(), block -> this.createOreDrop(block, IFWItems.raw_mithril.get()));
        add(IFWBlocks.silver_ore.get(), block -> this.createOreDrop(block, IFWItems.raw_silver.get()));
        add(IFWBlocks.deepslate_silver_ore.get(), block -> this.createOreDrop(block, IFWItems.raw_silver.get()));
        dropSelf(IFWBlocks.adamantium_block.get());
        dropSelf(IFWBlocks.adamantium_bars.get());
        add(IFWBlocks.adamantium_door.get(), this::createDoorTable);
        dropSelf(IFWBlocks.mithril_block.get());
        dropSelf(IFWBlocks.mithril_bars.get());
        add(IFWBlocks.mithril_door.get(), this::createDoorTable);
        dropSelf(IFWBlocks.ancient_metal_block.get());
        dropSelf(IFWBlocks.ancient_metal_bars.get());
        add(IFWBlocks.ancient_metal_door.get(), this::createDoorTable);
        dropSelf(IFWBlocks.gold_bars.get());
        add(IFWBlocks.gold_door.get(), this::createDoorTable);
        dropSelf(IFWBlocks.silver_block.get());
        dropSelf(IFWBlocks.silver_bars.get());
        add(IFWBlocks.silver_door.get(), this::createDoorTable);
        dropSelf(IFWBlocks.copper_bars.get());
        dropSelf(IFWBlocks.raw_adamantium_block.get());
        dropSelf(IFWBlocks.raw_mithril_block.get());
        dropSelf(IFWBlocks.raw_silver_block.get());
        dropSelf(IFWBlocks.clay_furnace.get());
        dropSelf(IFWBlocks.hardened_clay_furnace.get());
        dropSelf(IFWBlocks.sandstone_furnace.get());
        dropSelf(IFWBlocks.stone_furnace.get());
        dropSelf(IFWBlocks.obsidian_furnace.get());
        dropSelf(IFWBlocks.netherrack_furnace.get());
        dropSelf(IFWBlocks.mithril_runestone_quas.get());
        dropSelf(IFWBlocks.mithril_runestone_por.get());
        dropSelf(IFWBlocks.mithril_runestone_an.get());
        dropSelf(IFWBlocks.mithril_runestone_nox.get());
        dropSelf(IFWBlocks.mithril_runestone_flam.get());
        dropSelf(IFWBlocks.mithril_runestone_vas.get());
        dropSelf(IFWBlocks.mithril_runestone_des.get());
        dropSelf(IFWBlocks.mithril_runestone_ort.get());
        dropSelf(IFWBlocks.mithril_runestone_tym.get());
        dropSelf(IFWBlocks.mithril_runestone_corp.get());
        dropSelf(IFWBlocks.mithril_runestone_lor.get());
        dropSelf(IFWBlocks.mithril_runestone_mani.get());
        dropSelf(IFWBlocks.mithril_runestone_jux.get());
        dropSelf(IFWBlocks.mithril_runestone_ylem.get());
        dropSelf(IFWBlocks.mithril_runestone_sanct.get());
        dropSelf(IFWBlocks.adamantium_runestone_nul.get());
        dropSelf(IFWBlocks.adamantium_runestone_quas.get());
        dropSelf(IFWBlocks.adamantium_runestone_por.get());
        dropSelf(IFWBlocks.adamantium_runestone_an.get());
        dropSelf(IFWBlocks.adamantium_runestone_nox.get());
        dropSelf(IFWBlocks.adamantium_runestone_flam.get());
        dropSelf(IFWBlocks.adamantium_runestone_vas.get());
        dropSelf(IFWBlocks.adamantium_runestone_des.get());
        dropSelf(IFWBlocks.adamantium_runestone_ort.get());
        dropSelf(IFWBlocks.adamantium_runestone_tym.get());
        dropSelf(IFWBlocks.adamantium_runestone_corp.get());
        dropSelf(IFWBlocks.adamantium_runestone_lor.get());
        dropSelf(IFWBlocks.adamantium_runestone_mani.get());
        dropSelf(IFWBlocks.adamantium_runestone_jux.get());
        dropSelf(IFWBlocks.adamantium_runestone_ylem.get());
        dropSelf(IFWBlocks.adamantium_runestone_sanct.get());
        add(Blocks.OBSIDIAN, this::createObsidianExplosionTable);
        add(Blocks.CRYING_OBSIDIAN, this::createObsidianExplosionTable);
    }

    private LootTable.Builder createObsidianExplosionTable(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block)
                                .when(AnyOfCondition.anyOf(
                                        MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                .of(ItemTags.PICKAXES)),
                                        MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                .of(IFWItemTags.WAR_HAMMER))
                                ))))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(IFWItems.obsidian_shard.get())
                                .when(InvertedLootItemCondition.invert(
                                        AnyOfCondition.anyOf(
                                                MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                        .of(ItemTags.PICKAXES)),
                                                MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                        .of(IFWItemTags.WAR_HAMMER))
                                        )))));
    }
    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }
}