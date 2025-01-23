package huix.infinity.funextension;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface PlayerExtension {

    default void onEnchantmentPerformedPoints(ItemStack enchantedItem, int expPoints) {
        Player instance = (Player) this;
        instance.giveExperiencePoints(-expPoints);
    }

}
