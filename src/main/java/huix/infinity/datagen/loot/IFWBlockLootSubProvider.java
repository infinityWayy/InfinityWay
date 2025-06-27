package huix.infinity.datagen.loot;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
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
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class IFWBlockLootSubProvider extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.04F, 0.033333336F, 0.05F, 0.06666667F, 0.2F};
    private static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

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
        add(Blocks.OAK_LEAVES, oakLeavesBlock -> this.createOakLeavesDrops(oakLeavesBlock, Blocks.OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.SPRUCE_LEAVES, leavesBlock -> this.createLeavesDrops(leavesBlock, Blocks.SPRUCE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.BIRCH_LEAVES, leavesBlock -> this.createLeavesDrops(leavesBlock, Blocks.BIRCH_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.JUNGLE_LEAVES, leavesBlock -> this.createLeavesDrops(leavesBlock, Blocks.JUNGLE_SAPLING, JUNGLE_LEAVES_SAPLING_CHANGES));
        add(Blocks.ACACIA_LEAVES, leavesBlock -> this.createLeavesDrops(leavesBlock, Blocks.ACACIA_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.DARK_OAK_LEAVES, oakLeavesBlock -> this.createOakLeavesDrops(oakLeavesBlock, Blocks.DARK_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.CHERRY_LEAVES, leavesBlock -> this.createLeavesDrops(leavesBlock, Blocks.CHERRY_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.AZALEA_LEAVES, leavesBlock -> this.createLeavesDrops(leavesBlock, Blocks.AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.FLOWERING_AZALEA_LEAVES, leavesBlock -> this.createLeavesDrops(leavesBlock, Blocks.FLOWERING_AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
        add(Blocks.MANGROVE_LEAVES, this::createMangroveLeavesDrops);
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
    protected LootTable.@NotNull Builder createOakLeavesDrops(@NotNull Block oakLeavesBlock, @NotNull Block saplingBlock, float @NotNull ... chances) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(oakLeavesBlock, saplingBlock, chances)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(this.doesNotHaveShearsOrSilkTouch())
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(oakLeavesBlock, LootItem.lootTableItem(Items.APPLE)))
                                                .when(
                                                        BonusLevelTableCondition.bonusLevelFlatChance(
                                                                registrylookup.getOrThrow(Enchantments.FORTUNE), 0.01F, 0.0111111114F, 0.0125F, 0.016666668F, 0.05F
                                                        )
                                                )
                                )
                );
    }

    @Override
    protected LootTable.@NotNull Builder createMangroveLeavesDrops(@NotNull Block block) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchOrShearsDispatchTable(
                block,
                ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionDecay(
                        Blocks.MANGROVE_LEAVES, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                ))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))
        );
    }

    @Override
    protected LootTable.@NotNull Builder createLeavesDrops(@NotNull Block leavesBlock, @NotNull Block saplingBlock, float @NotNull ... chances) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchOrShearsDispatchTable(
                        leavesBlock,
                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(leavesBlock, LootItem.lootTableItem(saplingBlock)))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), chances))
                )
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(this.doesNotHaveShearsOrSilkTouch())
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionDecay(
                                                leavesBlock, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                        ))
                                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))
                                )
                );
    }

    private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
        return this.hasShearsOrSilkTouch().invert();
    }

    private LootItemCondition.Builder hasShearsOrSilkTouch() {
        return HAS_SHEARS.or(this.hasSilkTouch());
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }
}