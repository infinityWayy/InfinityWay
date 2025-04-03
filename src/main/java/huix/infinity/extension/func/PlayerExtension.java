package huix.infinity.extension.func;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface PlayerExtension {

    default void onEnchantmentPerformedPoints(final ItemStack enchantedItem, final int expPoints) {
        Player instance = (Player) this;
        instance.giveExperiencePoints(-expPoints);
    }

    default void ifw_updateTotalExperience() {
    }

    default boolean canResetTimeBySleeping() {
        return false;
    }
}
