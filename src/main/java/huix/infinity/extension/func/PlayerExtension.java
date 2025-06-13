package huix.infinity.extension.func;


import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.curse.CurseType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.portal.DimensionTransition;


public interface PlayerExtension {
    FoodData getFoodData();
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

    default boolean suffering_insulinResistance_mild() {
        return this.getFoodData().ifw_insulinResponse() > 48000;
    }

    default boolean suffering_insulinResistance_moderate() {
        return this.getFoodData().ifw_insulinResponse() > 96000;
    }

    default boolean suffering_insulinResistance_severe() {
        return this.getFoodData().ifw_insulinResponse() > 144000;
    }

    default boolean knownCurse() {
        return false;
    }

    default boolean hasCurse() {
        return getCurse() != CurseType.none;
    }

    default boolean hasCurse(CurseType curse) {
        return hasCurse() && !getCurse().equals(curse);
    }

    default CurseType getCurse() {
        return CurseType.values()[instance().getData(IFWAttachments.player_curse)];
    }

    default void setCurse(CurseType curse) {
        instance().setData(IFWAttachments.player_curse, curse.ordinal());
    }

    default void setCurse(int curseID) {
        setCurse(CurseType.values()[curseID]);
    }

}
