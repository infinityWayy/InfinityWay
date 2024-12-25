package huix.infinity.funextension;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Unique;

public interface PlayerExtension {

    default void onEnchantmentPerformedPoints(ItemStack enchantedItem, int expPoints) {
        Player instance = (Player) this;
        instance.giveExperiencePoints(-expPoints);
        if (instance.experienceLevel < this.ifw_getMinXpLevel()) {
            instance.experienceLevel = this.ifw_getMinXpLevel();
            instance.experienceProgress = 0.0F;
            instance.totalExperience = this.ifw_getXpForMinusLevel(this.ifw_getMinXpLevel());
        }
    }

    default int ifw_getMinXpLevel() {
        return 0;
    }

    default int ifw_getXpForMinusLevel(int c) {
        return 0;
    }

    default int getSyncedDeadExperience() {
        return 0;
    }

    default void setSyncedDeadExperience(int syncedDeadExperience) {
    }


}
