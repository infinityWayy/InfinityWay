package huix.infinity.datagen.tag;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.core.tag.IFWItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
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
        tag(ItemTags.PICKAXES).add(IFWItems.adamantium_pickaxe.get(), IFWItems.mithril_pickaxe.get(), IFWItems.ancient_metal_pickaxe.get(),
                IFWItems.iron_pickaxe.get(), IFWItems.copper_pickaxe.get(), IFWItems.gold_pickaxe.get(), IFWItems.silver_pickaxe.get(), IFWItems.rusted_iron_pickaxe.get());
        tag(ItemTags.AXES).add(IFWItems.adamantium_axe.get(), IFWItems.mithril_axe.get(), IFWItems.ancient_metal_axe.get(),
                IFWItems.iron_axe.get(), IFWItems.copper_axe.get(), IFWItems.gold_axe.get(), IFWItems.silver_axe.get(), IFWItems.rusted_iron_axe.get());
        tag(ItemTags.SWORDS).add(IFWItems.adamantium_sword.get(), IFWItems.mithril_sword.get(), IFWItems.ancient_metal_sword.get(),
                IFWItems.iron_sword.get(), IFWItems.copper_sword.get(), IFWItems.gold_sword.get(), IFWItems.silver_sword.get(), IFWItems.rusted_iron_sword.get());
        tag(ItemTags.HOES).add(IFWItems.adamantium_hoe.get(), IFWItems.mithril_hoe.get(), IFWItems.ancient_metal_hoe.get(),
                IFWItems.iron_hoe.get(), IFWItems.copper_hoe.get(), IFWItems.gold_hoe.get(), IFWItems.silver_hoe.get(), IFWItems.rusted_iron_hoe.get());
        tag(ItemTags.SHOVELS).add(IFWItems.adamantium_shovel.get(), IFWItems.mithril_shovel.get(), IFWItems.ancient_metal_shovel.get(),
                IFWItems.iron_shovel.get(), IFWItems.copper_shovel.get(), IFWItems.gold_shovel.get(), IFWItems.silver_shovel.get(), IFWItems.rusted_iron_shovel.get());

        tag(IFWItemTags.battle_axe).add(IFWItems.adamantium_battle_axe.get(), IFWItems.mithril_battle_axe.get(), IFWItems.ancient_metal_battle_axe.get(),
                IFWItems.iron_battle_axe.get(), IFWItems.copper_battle_axe.get(), IFWItems.gold_battle_axe.get(), IFWItems.silver_battle_axe.get(), IFWItems.rusted_iron_battle_axe.get());
        tag(IFWItemTags.dagger).add(IFWItems.adamantium_dagger.get(), IFWItems.mithril_dagger.get(), IFWItems.ancient_metal_dagger.get(),
                IFWItems.iron_dagger.get(), IFWItems.copper_dagger.get(), IFWItems.gold_dagger.get(), IFWItems.silver_dagger.get(), IFWItems.rusted_iron_dagger.get());
        tag(IFWItemTags.mattock).add(IFWItems.adamantium_mattock.get(), IFWItems.mithril_mattock.get(), IFWItems.ancient_metal_mattock.get(),
                IFWItems.iron_mattock.get(), IFWItems.copper_mattock.get(), IFWItems.gold_mattock.get(), IFWItems.silver_mattock.get(), IFWItems.rusted_iron_mattock.get());
        tag(IFWItemTags.scythe).add(IFWItems.adamantium_scythe.get(), IFWItems.mithril_scythe.get(), IFWItems.ancient_metal_scythe.get(),
                IFWItems.iron_scythe.get(), IFWItems.copper_scythe.get(), IFWItems.gold_scythe.get(), IFWItems.silver_scythe.get(), IFWItems.rusted_iron_scythe.get());
        tag(IFWItemTags.shears).add(IFWItems.adamantium_shears.get(), IFWItems.mithril_shears.get(), IFWItems.ancient_metal_shears.get(),
                IFWItems.iron_shears.get(), IFWItems.copper_shears.get(), IFWItems.gold_shears.get(), IFWItems.silver_shears.get(), IFWItems.rusted_iron_shears.get());
        tag(IFWItemTags.war_hammer).add(IFWItems.adamantium_war_hammer.get(), IFWItems.mithril_war_hammer.get(), IFWItems.ancient_metal_war_hammer.get(),
                IFWItems.iron_war_hammer.get(), IFWItems.copper_war_hammer.get(), IFWItems.gold_war_hammer.get(), IFWItems.silver_war_hammer.get(), IFWItems.rusted_iron_war_hammer.get());

        tag(IFWItemTags.string).add(IFWItems.sinew.get(), Items.STRING);
    }
}
