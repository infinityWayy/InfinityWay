package huix.infinity.datagen;

import huix.infinity.InfinityWay;
import huix.infinity.datagen.lang.IFWCNLanguageProvider;
import huix.infinity.datagen.lang.IFWUSLanguageProvider;
import huix.infinity.datagen.loot.IFWLootTableProvider;
import huix.infinity.datagen.recipe.IFWRecipeProvider;
import huix.infinity.datagen.tag.IFWBlockTagsProvider;
import huix.infinity.datagen.tag.IFWEnchantmentTagsProvider;
import huix.infinity.datagen.tag.IFWItemTagsProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class DataEntryPoint {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new IFWItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new IFWBlockStateProvider(output, existingFileHelper));
        //zh_cn
        generator.addProvider(event.includeClient(), new IFWCNLanguageProvider(output));
        //en_us
        generator.addProvider(event.includeClient(), new IFWUSLanguageProvider(output));

        generator.addProvider(event.includeServer(), new IFWLootTableProvider(output, lookupProvider));

        generator.addProvider(event.includeServer(), new IFWRecipeProvider(output, lookupProvider));

        generator.addProvider(event.includeServer(), new IFWEnchantmentTagsProvider(output, lookupProvider));

        IFWBlockTagsProvider blockTags = new IFWBlockTagsProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new IFWItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));

    }
}
