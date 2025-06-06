package huix.infinity.mixin.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import huix.infinity.common.client.render.IFWWorldRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererRainMixin {
    @Inject(method = "renderSnowAndRain", at = @At(value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;addVertex(FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private void modifyRainColor(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        Vector3f rainColor = IFWWorldRenderer.getRainColor();
        if (rainColor != null) {
            RenderSystem.setShaderColor(rainColor.x(), rainColor.y(), rainColor.z(), 1.0f);
        }
    }

    // 添加新的注入点来重置颜色
    @Inject(method = "renderSnowAndRain", at = @At("RETURN"))
    private void resetRainColor(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        // 恢复默认颜色
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
