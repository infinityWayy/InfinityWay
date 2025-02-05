package huix.infinity.init.event;

import huix.infinity.datagen.enchantment.IFWDataEnchantments;
import huix.infinity.datagen.lang.MCCNLang;
import huix.infinity.datagen.lang.MCUSLang;
import huix.infinity.datagen.model.IFWBlockStateProvider;
import huix.infinity.datagen.model.IFWItemModelProvider;
import huix.infinity.datagen.lang.IFWCNLang;
import huix.infinity.datagen.lang.IFWUSLang;
import huix.infinity.datagen.loot.IFWGlobalLootModifierProvider;
import huix.infinity.datagen.loot.IFWLootTableProvider;
import huix.infinity.datagen.recipe.IFWRecipeProvider;
import huix.infinity.datagen.tag.IFWBlockTagsProvider;
import huix.infinity.datagen.tag.IFWEnchantmentTagsProvider;
import huix.infinity.datagen.tag.IFWItemTagsProvider;
import huix.infinity.datagen.worldgen.IFWBiomeModifiers;
import huix.infinity.init.InfinityWay;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class IFWData {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new IFWBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new IFWItemModelProvider(output, existingFileHelper));
        //zh_cn
        generator.addProvider(event.includeClient(), new IFWCNLang(output));
        //generator.addProvider(event.includeClient(), new MCCNLang(output));
        //en_us
        generator.addProvider(event.includeClient(), new IFWUSLang(output));
        //generator.addProvider(event.includeClient(), new MCUSLang(output));

        generator.addProvider(event.includeServer(), new IFWLootTableProvider(output, lookupProvider));

        generator.addProvider(event.includeServer(), new IFWRecipeProvider(output, lookupProvider));

        generator.addProvider(event.includeServer(), new IFWEnchantmentTagsProvider(output, lookupProvider));

        IFWBlockTagsProvider blockTags = new IFWBlockTagsProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new IFWItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new IFWGlobalLootModifierProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<IFWBiomeModifiers>) packOutput -> new IFWBiomeModifiers(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<IFWDataEnchantments>) packOutput -> new IFWDataEnchantments(packOutput, lookupProvider));

    }
}
