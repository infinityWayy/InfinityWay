package huix.infinity.init.to;

import huix.infinity.common.client.model.ModelLayers;
import huix.infinity.common.client.render.EmeraldEnchantTableRenderer;
import huix.infinity.common.client.screen.IFWAnvilScreen;
import huix.infinity.common.client.resources.PersistentEffectTextureManager;
import huix.infinity.common.client.screen.EmeraldEnchantmentScreen;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.projectile.ThrownSlimeBall;
import huix.infinity.common.world.entity.render.animal.IFWChickenRenderer;
import huix.infinity.common.world.entity.render.animal.IFWCowRenderer;
import huix.infinity.common.world.entity.render.animal.IFWPigRenderer;
import huix.infinity.common.world.entity.render.animal.IFWSheepRenderer;
import huix.infinity.common.world.entity.render.gelatinous.GelatinousCubeRenderer;
import huix.infinity.common.world.entity.render.hound.HellHoundRenderer;
import huix.infinity.common.world.entity.render.hound.HellHoundModel;
import huix.infinity.common.world.entity.render.projectile.ThrownSlimeBallRenderer;
import huix.infinity.common.world.entity.render.skeleton.IFWSkeletonRenderer;
import huix.infinity.common.world.entity.render.zombie.HumanoidTypeRenderer;
import huix.infinity.common.world.entity.render.creeper.InfernoCreeperRenderer;
import huix.infinity.common.world.entity.render.projectile.IFWWebProjectileRenderer;
import huix.infinity.common.world.entity.render.spider.IFWSpiderRenderer;
import huix.infinity.common.world.entity.render.zombie.IFWZombieRenderer;
import huix.infinity.common.world.entity.render.zombie.InvisibleStalkerRender;
import huix.infinity.common.world.entity.render.zombie.RevenantRenderer;
import huix.infinity.common.world.inventory.IFWMenuTypes;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.init.InfinityWay;
import huix.infinity.util.IFWConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
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

        // 注册渔具模型
        registerFishingRodModel(IFWItems.copper_fishing_rod.get());
        registerFishingRodModel(IFWItems.silver_fishing_rod.get());
        registerFishingRodModel(IFWItems.gold_fishing_rod.get());
        registerFishingRodModel(IFWItems.ancient_metal_fishing_rod.get());
        registerFishingRodModel(IFWItems.mithril_fishing_rod.get());
        registerFishingRodModel(IFWItems.adamantium_fishing_rod.get());
        registerFishingRodModel(IFWItems.obsidian_fishing_rod.get());
        registerFishingRodModel(IFWItems.flint_fishing_rod.get());

        BlockEntityRenderers.register(IFWBlockEntityTypes.private_chest.get(), ChestRenderer::new);
        BlockEntityRenderers.register(IFWBlockEntityTypes.emerald_enchanting_table.get(), EmeraldEnchantTableRenderer::new);
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

    // 模型层注册
    @SubscribeEvent
    static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayers.HELLHOUND, HellHoundModel::createBodyLayer);
    }

    @SubscribeEvent
    static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        // 动物渲染器
        event.registerEntityRenderer(IFWEntityType.CHICKEN.get(), IFWChickenRenderer::new);
        event.registerEntityRenderer(IFWEntityType.SHEEP.get(), IFWSheepRenderer::new);
        event.registerEntityRenderer(IFWEntityType.PIG.get(), IFWPigRenderer::new);
        event.registerEntityRenderer(IFWEntityType.COW.get(), IFWCowRenderer::new);

        // 怪物渲染器
        event.registerEntityRenderer(IFWEntityType.ZOMBIE.get(), IFWZombieRenderer::new);
        event.registerEntityRenderer(IFWEntityType.REVENANT.get(), RevenantRenderer::new);
        event.registerEntityRenderer(IFWEntityType.GHOUL.get(), HumanoidTypeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.WIGHT.get(), HumanoidTypeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.INVISIBLE_STALKER.get(), InvisibleStalkerRender::new);
        event.registerEntityRenderer(IFWEntityType.SHADOW.get(), HumanoidTypeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.INFERNO_CREEPER.get(), InfernoCreeperRenderer::new);
        event.registerEntityRenderer(IFWEntityType.HELL_HOUND.get(), HellHoundRenderer::new);

        // 骷髅类渲染器
        event.registerEntityRenderer(IFWEntityType.SKELETON.get(), IFWSkeletonRenderer::new);
        event.registerEntityRenderer(IFWEntityType.LONGDEAD.get(), IFWSkeletonRenderer::new);
        event.registerEntityRenderer(IFWEntityType.LONGDEAD_GUARDIAN.get(), IFWSkeletonRenderer::new);
        event.registerEntityRenderer(IFWEntityType.BONE_LORD.get(), IFWSkeletonRenderer::new);
        event.registerEntityRenderer(IFWEntityType.ANCIENT_BONE_LORD.get(), IFWSkeletonRenderer::new);

        // 蜘蛛类渲染器 - 所有蜘蛛都使用通用渲染器
        event.registerEntityRenderer(IFWEntityType.SPIDER.get(), IFWSpiderRenderer::new);
        event.registerEntityRenderer(IFWEntityType.WOOD_SPIDER.get(), IFWSpiderRenderer::new);
        event.registerEntityRenderer(IFWEntityType.BLACK_WIDOW_SPIDER.get(), IFWSpiderRenderer::new);
        event.registerEntityRenderer(IFWEntityType.CAVE_SPIDER.get(), IFWSpiderRenderer::new);
        event.registerEntityRenderer(IFWEntityType.DEMON_SPIDER.get(), IFWSpiderRenderer::new);
        event.registerEntityRenderer(IFWEntityType.PHASE_SPIDER.get(), IFWSpiderRenderer::new);

        // 史莱姆类渲染器
        event.registerEntityRenderer(IFWEntityType.SLIME.get(), GelatinousCubeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.JELLY.get(), GelatinousCubeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.BLOB.get(), GelatinousCubeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.PUDDING.get(), GelatinousCubeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.OOZE.get(), GelatinousCubeRenderer::new);
        event.registerEntityRenderer(IFWEntityType.MAGMA_CUBE.get(), GelatinousCubeRenderer::new);

        // 投射物渲染器
        event.registerEntityRenderer(IFWEntityType.WEB_PROJECTILE.get(), IFWWebProjectileRenderer::new);
        event.registerEntityRenderer(IFWEntityType.THROWN_SLIME_BALL.get(), ThrownSlimeBallRenderer::new);
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