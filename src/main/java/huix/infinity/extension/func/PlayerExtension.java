package huix.infinity.extension.func;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.common.world.curse.Curses;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface PlayerExtension {

    default Player instance() {
        return (Player) this;
    }

    default void onEnchantmentPerformedPoints(final ItemStack enchantedItem, final int expPoints) {
        instance().giveExperiencePoints(-expPoints);
    }

    default void ifw_updateTotalExperience() {
    }

    default boolean canResetTimeBySleeping() {
        return false;
    }

    default boolean hasCurse() {
        if (instance().curse() == null) curse(Curses.none.get());
        return curse() != Curses.none.get();
    }

    default boolean knownCurse() {
        return false;
    }

    default boolean hasCurse(Curse curse) {
        return hasCurse() && instance().curse() == curse;
    }

    default Curse curse() {
        return Curses.none.get();
    }

    default void curse(Curse curse) {}

}
