package huix.infinity.common.world.entity.render.hound;

import huix.infinity.common.client.model.ModelLayers;
import huix.infinity.init.InfinityWay;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import huix.infinity.common.world.entity.monster.Hellhound;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.ParticleTypes;

public class HellhoundRenderer extends MobRenderer<Hellhound, HellhoundModel> {

    private static final ResourceLocation HELLHOUND_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/hound/hellhound.png");
    private static final ResourceLocation HELLHOUND_GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "textures/entity/hound/hellhound_glow.png");

    private int particleTimer = 0;

    public HellhoundRenderer(EntityRendererProvider.Context context) {
        super(context, new HellhoundModel(context.bakeLayer(ModelLayers.HELLHOUND)), 0.5F);
        this.addLayer(new HellhoundGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Hellhound hellhound) {
        return HELLHOUND_TEXTURE;
    }

    @Override
    public void render(Hellhound hellhound, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        if (this.model instanceof HellhoundModel) {
            ((HellhoundModel) this.model).prepareMobModel(hellhound, 0, 0, partialTicks);
        }

        super.render(hellhound, entityYaw, partialTicks, poseStack, buffer, packedLight);

        if (hellhound.level().isClientSide) {
            this.handleParticleEffects(hellhound);
        }
    }

    public static class HellhoundGlowLayer extends RenderLayer<Hellhound, HellhoundModel> {

        public HellhoundGlowLayer(HellhoundRenderer renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                           Hellhound hellhound, float limbSwing, float limbSwingAmount,
                           float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(HELLHOUND_GLOW_TEXTURE));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
        }
    }

    private void handleParticleEffects(Hellhound hellhound) {
        this.particleTimer++;

        if (hellhound.isInSittingPose()) {

            if (this.particleTimer % 40 == 0 && hellhound.getRandom().nextFloat() < 0.08F) {
                this.spawnSittingFireParticles(hellhound);
            }

            if (this.particleTimer % 50 == 0 && hellhound.getRandom().nextFloat() < 0.05F) {
                this.spawnSittingSmokeParticles(hellhound);
            }
        } else {

            boolean isMoving = hellhound.getDeltaMovement().lengthSqr() > 0.01D;

            if (isMoving) {
                if (this.particleTimer % 10 == 0 && hellhound.getRandom().nextFloat() < 0.35F) {
                    this.spawnBasicFireParticles(hellhound);
                }
            } else {
                if (this.particleTimer % 20 == 0 && hellhound.getRandom().nextFloat() < 0.15F) {
                    this.spawnBasicFireParticles(hellhound);
                }
            }

            if (isMoving) {
                if (this.particleTimer % 18 == 0 && hellhound.getRandom().nextFloat() < 0.2F) {
                    this.spawnSmokeParticles(hellhound);
                }
            } else {
                if (this.particleTimer % 25 == 0 && hellhound.getRandom().nextFloat() < 0.1F) {
                    this.spawnSmokeParticles(hellhound);
                }
            }

            if (isMoving && this.particleTimer % 12 == 0 && hellhound.getRandom().nextFloat() < 0.35F) {
                this.spawnMovementFireParticles(hellhound);
            }

            if (hellhound.getTarget() != null && hellhound.distanceTo(hellhound.getTarget()) < 2.0F) {
                if (this.particleTimer % 8 == 0 && hellhound.getRandom().nextFloat() < 0.4F) {
                    this.spawnCombatFireParticles(hellhound);
                }
            }
        }

        if (this.particleTimer > 200) {
            this.particleTimer = 0;
        }
    }

    // 坐下时的火焰粒子
    private void spawnSittingFireParticles(Hellhound hellhound) {
        double offsetX = (hellhound.getRandom().nextDouble() - 0.5D) * 0.25D;
        double offsetZ = (hellhound.getRandom().nextDouble() - 0.5D) * 0.25D;
        double offsetY = hellhound.getRandom().nextDouble() * 0.2D;

        double x = hellhound.getX() + offsetX;
        double y = hellhound.getY() + offsetY;
        double z = hellhound.getZ() + offsetZ;

        hellhound.level().addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.015D, 0.0D);
    }

    // 坐下时的烟雾粒子
    private void spawnSittingSmokeParticles(Hellhound hellhound) {
        double offsetX = (hellhound.getRandom().nextDouble() - 0.5D) * 0.3D;
        double offsetZ = (hellhound.getRandom().nextDouble() - 0.5D) * 0.3D;
        double offsetY = hellhound.getRandom().nextDouble() * 0.3D;

        double x = hellhound.getX() + offsetX;
        double y = hellhound.getY() + offsetY;
        double z = hellhound.getZ() + offsetZ;

        hellhound.level().addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.03D, 0.0D);
    }

    // 其他粒子
    private void spawnBasicFireParticles(Hellhound hellhound) {
        double offsetX = (hellhound.getRandom().nextDouble() - 0.5D) * 0.3D;
        double offsetZ = (hellhound.getRandom().nextDouble() - 0.5D) * 0.3D;
        double offsetY = hellhound.getRandom().nextDouble() * 0.5D;

        double x = hellhound.getX() + offsetX;
        double y = hellhound.getY() + offsetY;
        double z = hellhound.getZ() + offsetZ;

        hellhound.level().addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.02D, 0.0D);
    }

    private void spawnSmokeParticles(Hellhound hellhound) {
        double offsetX = (hellhound.getRandom().nextDouble() - 0.5D) * 0.4D;
        double offsetZ = (hellhound.getRandom().nextDouble() - 0.5D) * 0.4D;
        double offsetY = hellhound.getRandom().nextDouble() * 0.6D;

        double x = hellhound.getX() + offsetX;
        double y = hellhound.getY() + offsetY;
        double z = hellhound.getZ() + offsetZ;

        hellhound.level().addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.04D, 0.0D);
    }

    private void spawnMovementFireParticles(Hellhound hellhound) {
        double offsetX = (hellhound.getRandom().nextDouble() - 0.5D) * 0.35D;
        double offsetZ = (hellhound.getRandom().nextDouble() - 0.5D) * 0.35D;
        double offsetY = hellhound.getRandom().nextDouble() * 0.18D;

        double x = hellhound.getX() + offsetX;
        double y = hellhound.getY() + offsetY;
        double z = hellhound.getZ() + offsetZ;

        double motionX = -hellhound.getDeltaMovement().x * 0.4D + (hellhound.getRandom().nextDouble() - 0.5D) * 0.02D;
        double motionY = 0.015D + hellhound.getRandom().nextDouble() * 0.02D;
        double motionZ = -hellhound.getDeltaMovement().z * 0.4D + (hellhound.getRandom().nextDouble() - 0.5D) * 0.02D;

        hellhound.level().addParticle(ParticleTypes.FLAME, x, y, z, motionX, motionY, motionZ);

        if (hellhound.getRandom().nextFloat() < 0.2F) {
            hellhound.level().addParticle(ParticleTypes.SMALL_FLAME, x, y, z, motionX * 0.6D, motionY * 0.4D, motionZ * 0.6D);
        }
    }

    private void spawnCombatFireParticles(Hellhound hellhound) {
        double offsetX = (hellhound.getRandom().nextDouble() - 0.5D) * 0.5D;
        double offsetZ = (hellhound.getRandom().nextDouble() - 0.5D) * 0.5D;
        double offsetY = hellhound.getRandom().nextDouble() * 0.8D;

        double x = hellhound.getX() + offsetX;
        double y = hellhound.getY() + offsetY;
        double z = hellhound.getZ() + offsetZ;

        hellhound.level().addParticle(ParticleTypes.FLAME, x, y, z,
                (hellhound.getRandom().nextDouble() - 0.5D) * 0.03D,
                0.06D,
                (hellhound.getRandom().nextDouble() - 0.5D) * 0.03D);
    }
}