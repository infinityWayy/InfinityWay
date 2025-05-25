package huix.infinity.common.world.entity.render.spider;

import com.mojang.blaze3d.vertex.PoseStack;
import huix.infinity.common.world.entity.monster.arachnid.*;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * IFW蜘蛛渲染器 - 简化版本
 */
public class IFWSpiderRenderer extends MobRenderer<IFWArachnid, SpiderModel<IFWArachnid>> {
    // 主贴图
    private static final ResourceLocation SPIDER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/spider/spider.png");
    private static final ResourceLocation WOOD_SPIDER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/spider/wood_spider.png");
    private static final ResourceLocation BLACK_WIDOW_SPIDER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/spider/black_widow_spider.png");
    private static final ResourceLocation CAVE_SPIDER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/spider/cave_spider.png");
    private static final ResourceLocation DEMON_SPIDER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/spider/demon_spider.png");
    private static final ResourceLocation PHASE_SPIDER_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/spider/phase_spider.png");

    // 发光眼睛贴图
    private static final ResourceLocation SPIDER_EYES_LOCATION = ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/spider/spider_glow.png");

    public IFWSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER)), 0.8F);

        // 添加通用的蜘蛛眼睛发光层
        this.addLayer(new EyesLayer<IFWArachnid, SpiderModel<IFWArachnid>>(this) {
            @Override
            public RenderType renderType() {
                return RenderType.eyes(SPIDER_EYES_LOCATION);
            }
        });
    }

    @Override
    protected void scale(IFWArachnid entity, PoseStack poseStack, float partialTickTime) {
        float scale = getSpiderScaleFromEntityType(entity);
        poseStack.scale(scale, scale, scale);
    }

    private float getSpiderScaleFromEntityType(IFWArachnid entity) {
        final float BASE_WIDTH = 1.4F;

        if (entity instanceof IFWSpider) {
            return 1.0F;
        } else if (entity instanceof IFWWoodSpider && !(entity instanceof IFWPhaseSpider) && !(entity instanceof IFWBlackWidowSpider)) {
            return 0.84F / BASE_WIDTH;
        } else if (entity instanceof IFWBlackWidowSpider) {
            return 0.84F / BASE_WIDTH;
        } else if (entity instanceof IFWPhaseSpider) {
            return 0.84F / BASE_WIDTH;
        } else if (entity instanceof IFWCaveSpider) {
            return 0.7F / BASE_WIDTH;
        } else if (entity instanceof IFWDemonSpider) {
            return 1.0F;
        } else {
            return 1.0F;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(IFWArachnid entity) {
        if (entity instanceof IFWPhaseSpider) {
            return PHASE_SPIDER_LOCATION;
        } else if (entity instanceof IFWBlackWidowSpider) {
            return BLACK_WIDOW_SPIDER_LOCATION;
        } else if (entity instanceof IFWWoodSpider) {
            return WOOD_SPIDER_LOCATION;
        } else if (entity instanceof IFWCaveSpider) {
            return CAVE_SPIDER_LOCATION;
        } else if (entity instanceof IFWDemonSpider) {
            return DEMON_SPIDER_LOCATION;
        } else {
            return SPIDER_LOCATION;
        }
    }

    @Override
    protected float getFlipDegrees(IFWArachnid entity) {
        return 180.0F;
    }
}