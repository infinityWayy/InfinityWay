package huix.infinity.extension.func;

import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.curse.PersistentEffectInstance;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.common.world.curse.Curses;
import net.minecraft.core.Holder;
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

    default boolean hasCurse() {
        return !curse().equals(Curses.none.value());
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

    void changeDimension(ServerLevel targetLevel, DimensionTransition dimensionTransition);
}
