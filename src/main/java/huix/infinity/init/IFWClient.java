package huix.infinity.init;


import com.google.common.collect.ImmutableList;
import huix.infinity.common.client.IFWFurnaceScreen;
import huix.infinity.common.client.IFWAnvilScreen;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.inventory.IFWMenuType;
import huix.infinity.common.world.item.crafting.IFWRecipeType;
import huix.infinity.enum_extesion.IFWEnums;
import huix.infinity.enum_extesion.IFWRecipeBookCategories;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class IFWClient {

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    private static void clientSetup(final FMLClientSetupEvent event) {
        final RenderType cutout = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.adamantium_door.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.adamantium_bars.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.mithril_door.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.mithril_bars.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.ancient_metal_door.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.ancient_metal_bars.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.copper_door.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.copper_bars.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.silver_door.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.silver_bars.get(), ChunkRenderTypeSet.of(cutout));
    }

    @SubscribeEvent
    public static void registerMenuScreens(final RegisterMenuScreensEvent event) {
        event.register(IFWMenuType.ifw_furnace_menu.get(), IFWFurnaceScreen::new);
        event.register(IFWMenuType.anvil_menu.get(), IFWAnvilScreen::new);
    }

    @SubscribeEvent
    public static void registerRecipeBookCategories(final RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(IFWEnums.cooking_recipe_enum_proxy.getValue(),
                ImmutableList.of(IFWRecipeBookCategories.level_recipe.get(), RecipeBookCategories.FURNACE_BLOCKS
                        , RecipeBookCategories.FURNACE_FOOD, RecipeBookCategories.FURNACE_MISC));

        event.registerRecipeCategoryFinder(IFWRecipeType.ifw_smelting.get(), r -> IFWRecipeBookCategories   .level_recipe.get());
    }
}
