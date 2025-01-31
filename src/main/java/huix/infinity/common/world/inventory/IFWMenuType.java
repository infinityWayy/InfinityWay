package huix.infinity.common.world.inventory;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWMenuType {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, InfinityWay.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<IFWFurnaceMenu>> ifw_furnace_menu = MENUS.register(
            "ifw_furnace_menu", () -> new MenuType<>(IFWFurnaceMenu::new, FeatureFlags.VANILLA_SET));

    public static final DeferredHolder<MenuType<?>, MenuType<IFWAnvilMenu>> ifw_anvil_menu = MENUS.register(
            "ifw_anvil_menu", () -> new MenuType<>(IFWAnvilMenu::new, FeatureFlags.VANILLA_SET));
}
