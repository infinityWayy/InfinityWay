package huix.infinity.datagen.loot;

import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.loot.IFWBuiltInLootTables;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Stream;

public class IFWEntityLootSubProvider extends EntityLootSubProvider {
    private final Set<EntityType<?>> knownEntityTypes = new ReferenceOpenHashSet<>();

    protected IFWEntityLootSubProvider(HolderLookup.Provider provider) {
        super(FeatureFlags.DEFAULT_FLAGS, provider);
    }

    @Override
    public void generate() {
        // 动物类实体掉落
        this.add(
                IFWEntityType.CHICKEN.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.FEATHER)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                                        .when(isNotFire())
                                        )

                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.CHICKEN)
                                                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
        );
        this.add(
                IFWEntityType.SHEEP.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.MUTTON)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.LEATHER)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
        );
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_BLACK, createSheepTable(Blocks.BLACK_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_BLUE, createSheepTable(Blocks.BLUE_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_BROWN, createSheepTable(Blocks.BROWN_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_CYAN, createSheepTable(Blocks.CYAN_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_GRAY, createSheepTable(Blocks.GRAY_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_GREEN, createSheepTable(Blocks.GREEN_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_LIGHT_BLUE, createSheepTable(Blocks.LIGHT_BLUE_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_LIGHT_GRAY, createSheepTable(Blocks.LIGHT_GRAY_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_LIME, createSheepTable(Blocks.LIME_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_MAGENTA, createSheepTable(Blocks.MAGENTA_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_ORANGE, createSheepTable(Blocks.ORANGE_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_PINK, createSheepTable(Blocks.PINK_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_PURPLE, createSheepTable(Blocks.PURPLE_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_RED, createSheepTable(Blocks.RED_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_WHITE, createSheepTable(Blocks.WHITE_WOOL));
        this.add(IFWEntityType.SHEEP.get(), IFWBuiltInLootTables.SHEEP_YELLOW, createSheepTable(Blocks.YELLOW_WOOL));
        this.add(
                IFWEntityType.PIG.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.PORKCHOP)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
        );
        this.add(
                IFWEntityType.COW.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.LEATHER)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.BEEF)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                                        .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
        );
        this.add(
                IFWEntityType.ZOMBIE.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                        )
                        )
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.IRON_INGOT))
                                        .add(LootItem.lootTableItem(Items.CARROT))
                                        .add(LootItem.lootTableItem(Items.POTATO).apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))
                                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.025F, 0.01F))
                        )
        );

        this.add(
                IFWEntityType.INFERNO_CREEPER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                )
                        )
        );

        // ========== 蜘蛛掉落系统（修改版）==========

        // 普通蜘蛛和恶魔蜘蛛 - 标准掉落
        this.add(IFWEntityType.SPIDER.get(), createStandardSpiderTable());
        this.add(IFWEntityType.DEMON_SPIDER.get(), createStandardSpiderTable());

        // 其他有蛛网储存的蜘蛛 - 只掉落蛛网转化的丝线
        this.add(IFWEntityType.WOOD_SPIDER.get(), createWebOnlySpiderTable());
        this.add(IFWEntityType.BLACK_WIDOW_SPIDER.get(), createWebOnlySpiderTable());
        this.add(IFWEntityType.CAVE_SPIDER.get(), createWebOnlySpiderTable());

        // 相位蜘蛛 - 只掉落蜘蛛眼，不掉落任何丝线
        this.add(IFWEntityType.PHASE_SPIDER.get(), createPhaseSpiderTable());
    }

    /**
     * 标准蜘蛛掉落表（普通蜘蛛、恶魔蜘蛛）
     * 包含蛛网转化丝线 + 蜘蛛眼
     */
    protected LootTable.Builder createStandardSpiderTable() {
        return LootTable.lootTable()
                // 剩余蛛网掉落为丝线（MITE逻辑：在代码中已处理，这里只是备用）
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.STRING)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))) // 0-3个蛛网转化为丝线
                                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                )
                )
                // 蜘蛛眼掉落（1/3概率或抢夺附魔加成）
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.SPIDER_EYE)
                                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                        this.registries, 0.33F, 0.02F  // 33%基础概率，抢夺附魔加成
                                                ))
                                )
                );
    }

    /**
     * 仅蛛网掉落蜘蛛表（木蜘蛛、黑寡妇蜘蛛、洞穴蜘蛛）
     * 只包含蛛网转化丝线 + 蜘蛛眼，不包含基础丝线
     */
    protected LootTable.Builder createWebOnlySpiderTable() {
        return LootTable.lootTable()
                // 剩余蛛网掉落为丝线（MITE逻辑：实际在代码中处理）
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.STRING)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))) // 0-3个蛛网转化为丝线
                                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                )
                )
                // 蜘蛛眼掉落（1/3概率或抢夺附魔加成）
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.SPIDER_EYE)
                                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                        this.registries, 0.33F, 0.02F  // 33%基础概率，抢夺附魔加成
                                                ))
                                )
                );
    }

    /**
     * 相位蜘蛛掉落表
     * 只掉落蜘蛛眼，不掉落任何丝线
     */
    protected LootTable.Builder createPhaseSpiderTable() {
        return LootTable.lootTable()
                // 只有蜘蛛眼掉落
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(Items.SPIDER_EYE)
                                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                        this.registries, 0.33F, 0.02F  // 33%基础概率，抢夺附魔加成
                                                ))
                                )
                );
    }

    protected static LootTable.@NotNull Builder createSheepTable(ItemLike woolItem) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(woolItem)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(NestedLootTable.lootTableReference(
                        IFWEntityType.SHEEP.get().getDefaultLootTable()).when(isNotFire())));
    }

    protected static LootItemCondition.Builder isNotFire() {
        return InvertedLootItemCondition.invert(LootItemEntityPropertyCondition.hasProperties(
                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))
        ));
    }

    @Override
    protected void add(@NotNull EntityType<?> type, @NotNull LootTable.Builder table) {
        super.add(type, table);
        knownEntityTypes.add(type);
    }

    @NotNull
    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return knownEntityTypes.stream();
    }
}