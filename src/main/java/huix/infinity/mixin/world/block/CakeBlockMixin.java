package huix.infinity.mixin.world.block;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.block.CakeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CakeBlock.class)
public class CakeBlockMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;eat(IF)V"), method = "eat")
    private static void ifw_fixCakeEat(FoodData instance, int foodLevelModifier, float saturationLevelModifier, @Local(argsOnly = true) Player player) {
        player.getFoodData().eat(foodLevelModifier, foodLevelModifier);
    }
}
