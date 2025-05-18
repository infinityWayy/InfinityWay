package huix.infinity.common.world.enchantment;

import huix.infinity.common.world.item.crafting.EnchantingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Recipes {

    public static final EnchantingRecipe enchanted_golden_apple = (new EnchantingRecipe.Builder()).ingredient(new ItemStack(Items.GOLDEN_APPLE))
            .result(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE)).experience(200).build();
    public static final EnchantingRecipe experience_bottle = (new EnchantingRecipe.Builder()).ingredient(new ItemStack(Items.POTION))
            .result(new ItemStack(Items.EXPERIENCE_BOTTLE)).experience(200).build();
}
