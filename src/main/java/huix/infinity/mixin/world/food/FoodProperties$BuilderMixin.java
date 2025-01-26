package huix.infinity.mixin.world.food;

import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin( FoodProperties.Builder.class )
public class FoodProperties$BuilderMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodConstants;saturationByModifier(IF)F"), method = "build")
    private float redirectNutrition(int foodLevel, float saturationModifier) {
        return saturationModifier;
    }

}
