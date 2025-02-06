package huix.infinity.mixin.world.material;

import huix.infinity.func_extension.FluidExtension;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public class FluidMixin implements FluidExtension {

}
