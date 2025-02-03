package huix.infinity.func_extension;

import com.google.common.collect.Lists;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface BlockExtension {

    default List<ItemStack> asList(final Item item) {
        return Lists.newArrayList(new ItemStack(item));
    }

    default List<ItemStack> asList(final ItemStack item) {
        return Lists.newArrayList(item);
    }


}

