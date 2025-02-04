package huix.infinity.mixin.world.food;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Foods.class)
public class FoodsMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodProperties$Builder;saturationModifier(F)Lnet/minecraft/world/food/FoodProperties$Builder;")
            , method = "stew")
    private static FoodProperties.Builder modifySaturation(FoodProperties.Builder instance, float saturationModifier, @Local(argsOnly = true) int nutrition) {
        return (new FoodProperties.Builder()).nutrition(nutrition).saturationModifier(nutrition).usingConvertsTo(Items.BOWL);
    }
}
