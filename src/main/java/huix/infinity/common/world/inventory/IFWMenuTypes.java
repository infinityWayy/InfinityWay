package huix.infinity.common.world.inventory;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, InfinityWay.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<IFWAnvilMenu>> anvil_menu = MENUS.register(
            "ifw_anvil_menu", () -> new MenuType<>(IFWAnvilMenu::new, FeatureFlags.VANILLA_SET));

    public static final DeferredHolder<MenuType<?>, MenuType<EmeraldEnchantmentMenu>> emerald_enchantment_menu = MENUS.register(
            "ifw_emerald_enchantment_menu", () -> new MenuType<>(EmeraldEnchantmentMenu::new, FeatureFlags.VANILLA_SET));
}
