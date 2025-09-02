package huix.infinity.mixin.world.item;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.crafting.EnchantingRecipe;
import huix.infinity.extension.func.ItemStackExtension;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public class ItemStackMixin implements ItemStackExtension {


    @Unique
    @Override
    public boolean ifw_hasEncRecipe() {
        return this.getItem().components().has(IFWDataComponents.enchantment_recipe.get());
    }

    @Unique
    @Override
    public EnchantingRecipe ifw_encRecipe() {
        return this.getItem().components().get(IFWDataComponents.enchantment_recipe.get());
    }

    @Unique
    @Override
    public ItemStack ifw_encRecipeResult() {
        return ifw_encRecipe().result();
    }

    @Unique
    @Override
    public int ifw_encRecipeXP() {
        return ifw_encRecipe().experience();
    }

    @Unique
    @Override
    public int ifw_cookingLevel() {
        return this.getItem().components().getOrDefault(IFWDataComponents.ifw_cooking_level.get(), 1);
    }

    @Unique
    @Override
    public int ifw_beCookingLevel() {
        return this.getItem().components().getOrDefault(IFWDataComponents.ifw_be_cooking_level.get(), 1);
    }

    @Shadow
    public Item getItem() {
        return null;
    }

    @Override
    public boolean ifw_isAnimalProduct() {
        return ((ItemStack)(Object)this).is(IFWItemTags.ANIMAL_PRODUCTS);
    }
    @Override
    public boolean ifw_isPlant() {
        return ((ItemStack)(Object)this).is(IFWItemTags.VEGETABLES);
    }
    @Override
    public boolean ifw_isDrinkable() {
        return ((ItemStack)(Object)this).is(IFWItemTags.DRINKS);
    }
    @Override
    public boolean ifw_isArmor() {
        return ((ItemStack)(Object)this).is(IFWItemTags.ARMORS);
    }
    @Override
    public boolean ifw_isChest() {
        return ((ItemStack)(Object)this).is(IFWItemTags.CHESTS);
    }

}
