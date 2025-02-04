package huix.infinity.datagen.tag;

import huix.infinity.common.core.tag.IFWBlockTags;
import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.IFWItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IFWItemTagsProvider extends ItemTagsProvider {
    public IFWItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider, "vanilla", existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.@NotNull Provider provider) {
        tag(ItemTags.PICKAXES).add(IFWItems.adamantium_pickaxe.get(), IFWItems.mithril_pickaxe.get(), IFWItems.ancient_metal_pickaxe.get(), IFWItems.copper_pickaxe.get()
                , IFWItems.iron_pickaxe.get(), IFWItems.golden_pickaxe.get(), IFWItems.silver_pickaxe.get(), IFWItems.rusted_iron_pickaxe.get());
        tag(ItemTags.AXES).add(IFWItems.adamantium_axe.get(), IFWItems.mithril_axe.get(), IFWItems.ancient_metal_axe.get(),
                IFWItems.iron_axe.get(), IFWItems.copper_axe.get(), IFWItems.golden_axe.get(), IFWItems.silver_axe.get(), IFWItems.rusted_iron_axe.get());
        tag(ItemTags.SWORDS).add(IFWItems.adamantium_sword.get(), IFWItems.mithril_sword.get(), IFWItems.ancient_metal_sword.get(),
                IFWItems.iron_sword.get(), IFWItems.copper_sword.get(), IFWItems.golden_sword.get(), IFWItems.silver_sword.get(), IFWItems.rusted_iron_sword.get());
        tag(ItemTags.HOES).add(IFWItems.adamantium_hoe.get(), IFWItems.mithril_hoe.get(), IFWItems.ancient_metal_hoe.get(),
                IFWItems.iron_hoe.get(), IFWItems.copper_hoe.get(), IFWItems.golden_hoe.get(), IFWItems.silver_hoe.get(), IFWItems.rusted_iron_hoe.get());
        tag(ItemTags.SHOVELS).add(IFWItems.adamantium_shovel.get(), IFWItems.mithril_shovel.get(), IFWItems.ancient_metal_shovel.get(),
                IFWItems.iron_shovel.get(), IFWItems.copper_shovel.get(), IFWItems.golden_shovel.get(), IFWItems.silver_shovel.get(), IFWItems.rusted_iron_shovel.get());

        tag(IFWItemTags.BATTLE_AXE).add(IFWItems.adamantium_battle_axe.get(), IFWItems.mithril_battle_axe.get(), IFWItems.ancient_metal_battle_axe.get(),
                IFWItems.iron_battle_axe.get(), IFWItems.copper_battle_axe.get(), IFWItems.golden_battle_axe.get(), IFWItems.silver_battle_axe.get(), IFWItems.rusted_iron_battle_axe.get());
        tag(IFWItemTags.DAGGER).add(IFWItems.adamantium_dagger.get(), IFWItems.mithril_dagger.get(), IFWItems.ancient_metal_dagger.get(),
                IFWItems.iron_dagger.get(), IFWItems.copper_dagger.get(), IFWItems.golden_dagger.get(), IFWItems.silver_dagger.get(), IFWItems.rusted_iron_dagger.get());
        tag(IFWItemTags.MATTOCK).add(IFWItems.adamantium_mattock.get(), IFWItems.mithril_mattock.get(), IFWItems.ancient_metal_mattock.get(),
                IFWItems.iron_mattock.get(), IFWItems.copper_mattock.get(), IFWItems.golden_mattock.get(), IFWItems.silver_mattock.get(), IFWItems.rusted_iron_mattock.get());
        tag(IFWItemTags.SCYTHE).add(IFWItems.adamantium_scythe.get(), IFWItems.mithril_scythe.get(), IFWItems.ancient_metal_scythe.get(),
                IFWItems.iron_scythe.get(), IFWItems.copper_scythe.get(), IFWItems.golden_scythe.get(), IFWItems.silver_scythe.get(), IFWItems.rusted_iron_scythe.get());
        tag(IFWItemTags.SHEARS).add(IFWItems.adamantium_shears.get(), IFWItems.mithril_shears.get(), IFWItems.ancient_metal_shears.get(),
                IFWItems.iron_shears.get(), IFWItems.copper_shears.get(), IFWItems.golden_shears.get(), IFWItems.silver_shears.get(), IFWItems.rusted_iron_shears.get());
        tag(IFWItemTags.WAR_HAMMER).add(IFWItems.adamantium_war_hammer.get(), IFWItems.mithril_war_hammer.get(), IFWItems.ancient_metal_war_hammer.get(),
                IFWItems.iron_war_hammer.get(), IFWItems.copper_war_hammer.get(), IFWItems.golden_war_hammer.get(), IFWItems.silver_war_hammer.get(), IFWItems.rusted_iron_war_hammer.get());

        tag(IFWItemTags.STRING).add(IFWItems.sinew.get(), Items.STRING);
        copy(BlockTags.ANVIL, IFWItemTags.ANVIL);
        tag(Tags.Items.RODS_WOODEN).add(Items.STICK);
    }
}
