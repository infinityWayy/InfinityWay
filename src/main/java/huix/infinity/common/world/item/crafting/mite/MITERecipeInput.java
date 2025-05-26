package huix.infinity.common.world.item.crafting.mite;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * MITE合成输入接口
 */
public record MITERecipeInput(ItemStack[] items) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < items.length ? items[index] : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return items.length;
    }

    public static MITERecipeInput of(ItemStack... stacks) {
        return new MITERecipeInput(stacks);
    }
}