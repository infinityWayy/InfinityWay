package huix.infinity.init.to;


import com.google.common.collect.ImmutableList;
import huix.infinity.common.client.IFWAnvilScreen;
import huix.infinity.common.client.IFWFurnaceScreen;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.render.IFWChickenRenderer;
import huix.infinity.common.world.entity.render.IFWCowRenderer;
import huix.infinity.common.world.entity.render.IFWSheepRenderer;
import huix.infinity.common.world.entity.render.sheep.IFWPigRenderer;
import huix.infinity.common.world.inventory.IFWMenuTypes;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.crafting.IFWRecipeTypes;
import huix.infinity.enum_extesion.IFWRecipeBookCategories;
import huix.infinity.enum_extesion.IFWRecipeBookTypes;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
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
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.copper_bars.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.silver_door.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.silver_bars.get(), ChunkRenderTypeSet.of(cutout));
        registerFishingRodModel(IFWItems.copper_fishing_rod.get());
        registerFishingRodModel(IFWItems.silver_fishing_rod.get());
        registerFishingRodModel(IFWItems.gold_fishing_rod.get());
        registerFishingRodModel(IFWItems.ancient_metal_fishing_rod.get());
        registerFishingRodModel(IFWItems.mithril_fishing_rod.get());
        registerFishingRodModel(IFWItems.adamantium_fishing_rod.get());
        registerFishingRodModel(IFWItems.obsidian_fishing_rod.get());
        registerFishingRodModel(IFWItems.flint_fishing_rod.get());
    }

    @SubscribeEvent
    public static void registerMenuScreens(final RegisterMenuScreensEvent event) {
        event.register(IFWMenuTypes.ifw_furnace_menu.get(), IFWFurnaceScreen::new);
        event.register(IFWMenuTypes.anvil_menu.get(), IFWAnvilScreen::new);
    }

    @SubscribeEvent
    public static void registerRecipeBookCategories(final RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(IFWRecipeBookTypes.cooking_recipe_enum_proxy.getValue(),
                ImmutableList.of(IFWRecipeBookCategories.level_recipe.get(), RecipeBookCategories.FURNACE_BLOCKS
                        , RecipeBookCategories.FURNACE_FOOD, RecipeBookCategories.FURNACE_MISC));

        event.registerRecipeCategoryFinder(IFWRecipeTypes.ifw_smelting.get(), r -> IFWRecipeBookCategories   .level_recipe.get());
    }

    @SubscribeEvent
    public static void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(IFWEntityType.CHICKEN.get(), IFWChickenRenderer::new);
        event.registerEntityRenderer(IFWEntityType.SHEEP.get(), IFWSheepRenderer::new);
        event.registerEntityRenderer(IFWEntityType.PIG.get(), IFWPigRenderer::new);
        event.registerEntityRenderer(IFWEntityType.COW.get(), IFWCowRenderer::new);
    }


    public static void registerFishingRodModel(Item fishingRod) {
        ItemProperties.register(fishingRod, ResourceLocation.withDefaultNamespace("cast"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                boolean isMainhand = entity.getMainHandItem() == stack;
                boolean isOffHand = entity.getOffhandItem() == stack;
                if (entity.getMainHandItem().getItem() instanceof FishingRodItem) {
                    isOffHand = false;
                }
                return (isMainhand || isOffHand) && entity instanceof Player && ((Player) entity).fishing != null ? 1.0F : 0.0F;
            }
        });
    }

}
