package huix.infinity.mixin.world.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin( BlockBehaviour.Properties.class )
public class PropertiesMixin {

//    @ModifyVariable(at = @At(value = "HEAD"), method = "destroyTime", argsOnly = true)
//    private float ifw_exchangeDestroyTime(float value) {
//        return value * 100;
//    }
}
