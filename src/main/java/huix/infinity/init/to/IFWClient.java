package huix.infinity.init.to;


import huix.infinity.common.client.screen.IFWAnvilScreen;
import huix.infinity.common.client.resources.PersistentEffectTextureManager;
import huix.infinity.common.client.screen.EmeraldEnchantmentScreen;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.render.*;
import huix.infinity.common.world.inventory.IFWMenuTypes;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.InfinityWay;
import huix.infinity.util.IFWConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
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
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(value = Dist.CLIENT, modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class IFWClient {

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    static void clientSetup(final FMLClientSetupEvent event) {
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

        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.silver_bars.get(), ChunkRenderTypeSet.of(cutout));

        registerFishingRodModel(IFWItems.copper_fishing_rod.get());
        registerFishingRodModel(IFWItems.silver_fishing_rod.get());
        registerFishingRodModel(IFWItems.gold_fishing_rod.get());
        registerFishingRodModel(IFWItems.ancient_metal_fishing_rod.get());
        registerFishingRodModel(IFWItems.mithril_fishing_rod.get());
        registerFishingRodModel(IFWItems.adamantium_fishing_rod.get());
        registerFishingRodModel(IFWItems.obsidian_fishing_rod.get());
        registerFishingRodModel(IFWItems.flint_fishing_rod.get());

        BlockEntityRenderers.register(IFWBlockEntityTypes.private_chest.get(), ChestRenderer::new);
    }

    @SubscribeEvent
    static void registerReloadListenerEvent(RegisterClientReloadListenersEvent event) {
        IFWConstants.persistentEffectTextureManager = new PersistentEffectTextureManager(Minecraft.getInstance().getTextureManager());
        //assert IFWConstants.persistentEffectTextureManager != null;
        event.registerReloadListener(IFWConstants.persistentEffectTextureManager);
    }


    @SubscribeEvent
    static void registerMenuScreens(final RegisterMenuScreensEvent event) {
        event.register(IFWMenuTypes.anvil_menu.get(), IFWAnvilScreen::new);
        event.register(IFWMenuTypes.emerald_enchantment_menu.get(), EmeraldEnchantmentScreen::new);
    }

    @SubscribeEvent
    static void registerRecipeBookCategories(final RegisterRecipeBookCategoriesEvent event) {
    }

    @SubscribeEvent
    static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(IFWEntityType.CHICKEN.get(), IFWChickenRenderer::new);
        event.registerEntityRenderer(IFWEntityType.SHEEP.get(), IFWSheepRenderer::new);
        event.registerEntityRenderer(IFWEntityType.PIG.get(), IFWPigRenderer::new);
        event.registerEntityRenderer(IFWEntityType.COW.get(), IFWCowRenderer::new);
        event.registerEntityRenderer(IFWEntityType.ZOMBIE.get(), IFWZombieRenderer::new);
    }


    static void registerFishingRodModel(Item fishingRod) {
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
