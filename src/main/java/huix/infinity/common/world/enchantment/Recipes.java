package huix.infinity.common.world.enchantment;

import huix.infinity.common.world.item.crafting.EnchantmentRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Recipes {

    public static final EnchantmentRecipe enchanted_golden_apple = (new EnchantmentRecipe.Builder()).ingredient(new ItemStack(Items.GOLDEN_APPLE))
            .result(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE)).experience(200).build();
}
