package huix.infinity.datagen.loot;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.loot.JungleLeavesLootModifier;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.concurrent.CompletableFuture;

public class IFWGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public IFWGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, InfinityWay.MOD_ID);
    }

    @Override
    protected void start() {
        // 添加丛林树叶掉落香蕉的 LootModifier
        add("jungle_leaves_drop", new JungleLeavesLootModifier(
                new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.JUNGLE_LEAVES).build()
                },
                new ItemStack(IFWItems.banana.get()), 200
        ));
        // 添加橡树树叶在丛林群系掉落橘子的 LootModifier
        add("oak_leaves_drop", new OakLeavesLootModifier(
                new LootItemCondition[]{
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.OAK_LEAVES).build()
                },
                new ItemStack(IFWItems.orange.get()), 200
        ));
    }
}
