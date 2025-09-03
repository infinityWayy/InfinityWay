package huix.infinity.mixin.world.item;

import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.crafting.EnchantingRecipe;
import huix.infinity.extension.func.ItemStackExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
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

    @Shadow
    public boolean is(TagKey<Item> tag) {
        throw new AssertionError("Shadow stub called!");
    }

    @Override
    public boolean ifw_isAnimalProduct() {
        return this.is(IFWItemTags.ANIMAL_PRODUCTS)
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "meat")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "meat")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("farmersdelight", "meat")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("croptopia", "meats")));
    }

    @Override
    public boolean ifw_isPlant() {
        return this.is(IFWItemTags.VEGETABLES)
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "vegetable")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "vegetable")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("farmersdelight", "vegetable")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("farmersdelight", "foods/onion")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("farmersdelight", "foods/tomato")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("farmersdelight", "foods/cabbage")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("farmersdelight", "foods/cabbage_leaf")))
                || this.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("croptopia", "vegetables")));
    }

    @Override
    public boolean ifw_isDrink() {
        ItemStack stack = (ItemStack) (Object) this;
        Item item = stack.getItem();
        return item.getUseAnimation(stack) == UseAnim.DRINK;
    }
}
