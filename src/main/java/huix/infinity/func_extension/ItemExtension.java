package huix.infinity.func_extension;

import huix.infinity.common.core.component.IFWDataComponents;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

public interface ItemExtension {

    default float getReachBonus() {
        return 0.0F;
    }

    default float getReachBonus(final ItemStack itemStack) {
        return this.getReachBonus();
    }

    @ApiStatus.Internal
    default void ifw_modifyDefaultComponentsFrom(DataComponentPatch patch) {
        Item instance = (Item) this;
        if (instance.components().has(DataComponents.MAX_DAMAGE)) return;
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(instance.components);
        patch.entrySet().forEach((entry) -> builder.set((DataComponentType)entry.getKey(), ((Optional)entry.getValue()).orElse((Object)null)));
        instance.components = Item.Properties.COMPONENT_INTERNER.intern(Item.Properties.validateComponents(builder.build()));
    }

    @ApiStatus.Internal
    default void ifw_modifyDefaultComponentsToAnvil(DataComponentPatch patch) {
        Item instance = (Item) this;
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(instance.components);
        patch.entrySet().forEach((entry) -> builder.set((DataComponentType)entry.getKey(), ((Optional)entry.getValue()).orElse((Object)null)));
        instance.components = Item.Properties.COMPONENT_INTERNER.intern(Item.Properties.validateComponents(builder.build()));
    }

    default boolean ifw_isFood() {
        DataComponentMap components = ((Item) this).components;
        return components.has(DataComponents.FOOD) || components.has(IFWDataComponents.ifw_food_data.get());
    }
}
