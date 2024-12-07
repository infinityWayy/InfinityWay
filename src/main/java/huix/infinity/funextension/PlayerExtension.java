package huix.infinity.funextension;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface PlayerExtension {

    default void onEnchantmentPerformedPoints(ItemStack enchantedItem, int expPoints) {
        Player instance = (Player) this;
        instance.giveExperiencePoints(-expPoints);
        if (instance.experienceLevel < 0) {
            instance.experienceLevel = 0;
            instance.experienceProgress = 0.0F;
            instance.totalExperience = 0;
        }

        instance.enchantmentSeed = instance.getRandom().nextInt();
    }


}
