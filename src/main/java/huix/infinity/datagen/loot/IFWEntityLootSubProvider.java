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
        //Overwrite the core register method to add to our list of known entity types
        //Note: This isn't the actual core method as that one takes a ResourceLocation, but all our things wil pass through this one
        super.add(type, table);
        knownEntityTypes.add(type);
    }

    @NotNull
    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return knownEntityTypes.stream();
    }
}
