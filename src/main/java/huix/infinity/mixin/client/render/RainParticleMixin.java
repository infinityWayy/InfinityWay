package huix.infinity.mixin.client.render;

import huix.infinity.common.client.render.IFWWorldRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.WaterDropParticle;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterDropParticle.class)
public abstract class RainParticleMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyRainParticleColor(ClientLevel level, double x, double y, double z, CallbackInfo ci) {
        Vector3f bloodRainColor = IFWWorldRenderer.getRainColor();
        if (bloodRainColor != null) {
            ((WaterDropParticle)(Object) this).setColor(
                    bloodRainColor.x(),
                    bloodRainColor.y(),
                    bloodRainColor.z()
            );
        }
    }
}