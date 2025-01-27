package huix.infinity.mixin.world.block.entity;

import com.llamalad7.mixinextras.sugar.Local;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.item.crafting.LevelCookingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 1), method = "canBurn", cancellable = true)
    private static void injectCookingLevel(RegistryAccess registryAccess, RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize
            , AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Boolean> cir){
        if (inventory.get(1).cookingLevel() < ifw_getCookingLevel(furnace.getLevel(), furnace))
            cir.setReturnValue(false);
    }


    @Unique
    private static int ifw_getCookingLevel(Level level, AbstractFurnaceBlockEntity blockEntity) {

        SingleRecipeInput singlerecipeinput = new SingleRecipeInput(blockEntity.getItem(0));
        return blockEntity.quickCheck.getRecipeFor(singlerecipeinput, level)
                .map(holder -> ((LevelCookingRecipe) holder.value()).cookingLevel()).orElse(0);
    }
}
