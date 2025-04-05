package huix.infinity.extension.func;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.curse.PersistentEffectInstance;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.common.world.curse.Curses;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Supplier;

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
        return !curse().equals(Curses.none.value());
    }

    default boolean knownCurse() {
        return false;
    }

    default boolean hasCurse(Curse curse) {
        return hasCurse() && !curse().equals(curse);
    }

    default Curse curse() {
        return (Curse) Curses.none.value();
    }

    default void curse(Holder<PersistentEffect> curse) {
        curse(new PersistentEffectInstance(curse));
    }

    default void curse(PersistentEffectInstance curse) {
    }

    default void learnCurse() {
    }


}
