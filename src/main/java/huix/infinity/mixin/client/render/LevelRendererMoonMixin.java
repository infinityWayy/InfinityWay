package huix.infinity.mixin.client.render;

import huix.infinity.common.client.render.IFWWorldRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMoonMixin {

    @Inject(method = "renderSky",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getMoonPhase()I"))
    private void renderBloodMoon(Matrix4f matrix4f, Matrix4f matrix4f2, float f,
                                 Camera camera, boolean bl, Runnable runnable,
                                 CallbackInfo ci) {
        IFWWorldRenderer.renderColorMoon(f);
    }

    @ModifyVariable(method = "renderSky",
            at = @At("STORE"),
            ordinal = 5)
    private float modifyMoonSize(float original) {
        return IFWWorldRenderer.getBloodMoonSize(original);
    }
}