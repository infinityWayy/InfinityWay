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
        Player player = (Player) this;
        return player.hasData(IFWAttachments.player_curse) && !curse().equals(Curses.none.get());
    }

    default boolean knownCurse() {
        Player player = (Player) this;
        return hasCurse() && player.hasData(IFWAttachments.learned_curse) && player.getData(IFWAttachments.learned_curse);
    }

    default boolean hasCurse(Curse curse) {
        Player player = (Player) this;
        return hasCurse() && player.getData(IFWAttachments.player_curse) == curse;
    }

    default Curse curse() {
        Player player = (Player) this;
        return (Curse) player.getData(IFWAttachments.player_curse);
    }

    default void curse(Curse curse) {
        Player player = (Player) this;
        player.setData(IFWAttachments.player_curse, curse);
    }

}
