package huix.infinity.mixin.world.enchantment;

import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.RemoveBinomial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( RemoveBinomial.class )
public class RemoveBinomialMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/LevelBasedValue;calculate(I)F")
            , method = "process")
    private float ifw_fixEnch(LevelBasedValue instance, int i) {
        return instance.calculate(i) * 0.75F;
    }
}
