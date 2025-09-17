package huix.infinity.init.to;

import huix.infinity.common.client.model.ModelLayers;
import huix.infinity.common.client.render.EmeraldEnchantTableRenderer;
import huix.infinity.common.client.render.PrivateChestRenderer;
import huix.infinity.common.client.screen.IFWAnvilScreen;
import huix.infinity.common.client.resources.PersistentEffectTextureManager;
import huix.infinity.common.client.screen.EmeraldEnchantmentScreen;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.render.animal.*;
import huix.infinity.common.world.entity.render.gelatinous.GelatinousCubeRenderer;
import huix.infinity.common.world.entity.render.hound.HellHoundRenderer;
import huix.infinity.common.world.entity.render.hound.HellHoundModel;
import huix.infinity.common.world.entity.render.projectile.ThrownSlimeBallRenderer;
import huix.infinity.common.world.entity.render.silverfish.IFWSilverfishRenderer;
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
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

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

        registerFishingRodModel(IFWItems.copper_fishing_rod.get());
        registerFishingRodModel(IFWItems.silver_fishing_rod.get());
        registerFishingRodModel(IFWItems.gold_fishing_rod.get());
        registerFishingRodModel(IFWItems.ancient_metal_fishing_rod.get());
        registerFishingRodModel(IFWItems.mithril_fishing_rod.get());
        registerFishingRodModel(IFWItems.adamantium_fishing_rod.get());
        registerFishingRodModel(IFWItems.obsidian_fishing_rod.get());
        registerFishingRodModel(IFWItems.flint_fishing_rod.get());

        ItemProperties.register(IFWItems.gold_pan_gravel.get(),
                ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "gravel_amount"),
                (stack, level, entity, seed) -> {
                    if (entity instanceof Player player && player.isUsingItem() && player.getUseItem() == stack) {
                        int useTicks = player.getTicksUsingItem();
                        int totalTime = stack.getUseDuration(player);

                        float progress = (float) useTicks / totalTime;

                        return Math.max(0.25f, 1.0f - progress * 0.75f);
                    }
                    return 1.0f;
                });

        BlockEntityRenderers.register(IFWBlockEntityTypes.emerald_enchanting_table.get(), EmeraldEnchantTableRenderer::new);
        BlockEntityRenderers.register(IFWBlockEntityTypes.private_chest.get(), PrivateChestRenderer::new);
        NeoForge.EVENT_BUS.addListener(IFWClient::onHandRender);
    }

    @SubscribeEvent
    static void registerReloadListenerEvent(RegisterClientReloadListenersEvent event) {
        IFWConstants.persistentEffectTextureManager = new PersistentEffectTextureManager(Minecraft.getInstance().getTextureManager());
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
        event.registerLayerDefinition(ModelLayers.DIRE_WOLF, DireWolfModel::createBodyLayer);
    }

    @SubscribeEvent
    static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        // 动物渲染器
        event.registerEntityRenderer(IFWEntityType.CHICKEN.get(), IFWChickenRenderer::new);
        event.registerEntityRenderer(IFWEntityType.SHEEP.get(), IFWSheepRenderer::new);
        event.registerEntityRenderer(IFWEntityType.PIG.get(), IFWPigRenderer::new);
        event.registerEntityRenderer(IFWEntityType.COW.get(), IFWCowRenderer::new);
        event.registerEntityRenderer(IFWEntityType.DIRE_WOLF.get(), IFWDireWolfRenderer::new);

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

        event.registerEntityRenderer(IFWEntityType.SILVERFISH.get(), IFWSilverfishRenderer::new);
        event.registerEntityRenderer(IFWEntityType.COPPERSPINE.get(), IFWSilverfishRenderer::new);
        event.registerEntityRenderer(IFWEntityType.HOARY_SILVERFISH.get(), IFWSilverfishRenderer::new);
        event.registerEntityRenderer(IFWEntityType.NETHER_SILVERFISH.get(), IFWSilverfishRenderer::new);

        // 投射物渲染器
        event.registerEntityRenderer(IFWEntityType.WEB_PROJECTILE.get(), IFWWebProjectileRenderer::new);
        event.registerEntityRenderer(IFWEntityType.THROWN_SLIME_BALL.get(), ThrownSlimeBallRenderer::new);
    }

    @SubscribeEvent
    static void registerItemExtensions(final RegisterClientExtensionsEvent event) {

    }

    public static void onHandRender(RenderHandEvent event) {
        final Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        final ItemStack mainHand = player.getMainHandItem();
        if (mainHand.getItem() == IFWItems.gold_pan.get() || mainHand.getItem() == IFWItems.gold_pan_gravel.get()) {

            if (event.getHand() == InteractionHand.MAIN_HAND) {
                final PoseStack poseStack = event.getPoseStack();
                poseStack.pushPose();
                renderTwoHandedItem(poseStack, event.getMultiBufferSource(), event.getPackedLight(),
                        event.getInterpolatedPitch(), event.getEquipProgress(), event.getSwingProgress(), mainHand);
                poseStack.popPose();
            }
            event.setCanceled(true);
        }
    }

    public static void renderTwoHandedItem(PoseStack poseStack, MultiBufferSource source, int combinedLight, float pitch, float equipProgress, float swingProgress, ItemStack stack) {
        final Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        final float swingSqrt = Mth.sqrt(swingProgress);
        final float y = -0.2F * Mth.sin(swingProgress * (float) Math.PI);
        final float z = -0.4F * Mth.sin(swingSqrt * (float) Math.PI);
        poseStack.translate(0.0D, -y / 2.0F, z);

        final float tilt = calculateTilt(pitch);
        poseStack.translate(0.0D, 0.04F + equipProgress * -1.2F + tilt * -0.5F, -1.8F);

        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.clamp(tilt, 0.3F, 0.51F) * 85.0F));
        if (!mc.player.isInvisible()) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            renderMapHand(mc, poseStack, source, combinedLight, HumanoidArm.RIGHT);
            renderMapHand(mc, poseStack, source, combinedLight, HumanoidArm.LEFT);
            poseStack.popPose();
        }

        addSiftingMovement(mc.player, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(swingSqrt * (float) Math.PI) * 20.0F));
        poseStack.scale(2.0F, 2.0F, 2.0F);

        final boolean right = mc.player.getMainArm() == HumanoidArm.RIGHT;
        mc.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(mc.player, stack,
                right ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                !right, poseStack, source, combinedLight);
    }

    private static float calculateTilt(float pitch) {
        final float deg = Mth.clamp(1.0F - pitch / 45.0F + 0.1F, 0.0F, 1.0F) * (float) Math.PI;
        return -Mth.cos(deg) * 0.5F + 0.5F;
    }

    private static void renderMapHand(Minecraft mc, PoseStack poseStack, MultiBufferSource source, int combinedLight, HumanoidArm arm) {
        assert mc.player != null;

        final PlayerRenderer playerRenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().<AbstractClientPlayer>getRenderer(mc.player);
        poseStack.pushPose();
        final float side = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(92.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        float degrees = side * -41.0F + calculateArmMovement(mc.player);
        poseStack.mulPose(Axis.ZP.rotationDegrees(degrees));

        poseStack.translate(side * 0.5D, -1.5D, 0.1D);

        if (arm == HumanoidArm.RIGHT) {
            playerRenderer.renderRightHand(poseStack, source, combinedLight, mc.player);
        } else {
            playerRenderer.renderLeftHand(poseStack, source, combinedLight, mc.player);
        }
        poseStack.popPose();
    }

    private static void addSiftingMovement(LocalPlayer player, PoseStack stack) {
        final float degrees = player.getUseItemRemainingTicks() * (float) Math.PI / 10F;
        if (degrees > 0f) {
            final float scale = 0.1f;
            stack.translate(scale * Mth.cos(degrees), 0f, scale * Mth.sin(degrees));
        }
    }

    private static float calculateArmMovement(LocalPlayer player) {
        final float degrees = player.getUseItemRemainingTicks() * (float) Math.PI / 10F;
        if (degrees > 0f) {
            return 10f * Mth.cos(degrees);
        }
        return 0f;
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