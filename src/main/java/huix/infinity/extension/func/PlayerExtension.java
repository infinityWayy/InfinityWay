package huix.infinity.extension.func;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.curse.CurseManager;
import huix.infinity.common.world.curse.CurseType;
import huix.infinity.common.world.entity.player.NutritionalStatus;
import huix.infinity.common.world.food.EnumInsulinResistanceLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

/**
 * 玩家扩展接口（Mixin/Attachment）
 * 修正版：支持诅咒识别机制，推荐使用 Boolean 类型 Attachment
 */
public interface PlayerExtension {
    FoodData getFoodData();

    default Player instance() {
        return (Player) this;
    }

    default void onEnchantmentPerformedPoints(final ItemStack enchantedItem, final int expPoints) {
        instance().giveExperiencePoints(-expPoints);
    }

    default void ifw_updateTotalExperience() {}

    default boolean ifw$canResetTimeBySleeping() {
        return false;
    }

    default EnumInsulinResistanceLevel getInsulinResistanceLevel() {
        return EnumInsulinResistanceLevel.fromValue(this.getFoodData().ifw_insulinResponse());
    }

    default boolean suffering_insulinResistance_mild() {
        return getInsulinResistanceLevel() == EnumInsulinResistanceLevel.MILD;
    }
    default boolean suffering_insulinResistance_moderate() {
        return getInsulinResistanceLevel() == EnumInsulinResistanceLevel.MODERATE;
    }
    default boolean suffering_insulinResistance_severe() {
        return getInsulinResistanceLevel() == EnumInsulinResistanceLevel.SEVERE;
    }

    default boolean sufferingMalnutrition() {
        NutritionalStatus status = this.getFoodData().ifw_nutritionalStatus();
        return status == NutritionalStatus.LIGHT || status == NutritionalStatus.SERIOUS;
    }

    /**
     * 是否已经识别当前诅咒（用于“未知诅咒”机制）
     * 需要在 IFWAttachments 注册 player_curse_known（Boolean 类型）
     */
    default boolean knownCurse() {
        return instance().getData(IFWAttachments.player_curse_known);
    }

    /**
     * 设置诅咒是否被识别
     */
    default void setKnownCurse(boolean known) {
        instance().setData(IFWAttachments.player_curse_known, known);
    }

    /**
     * 是否有任何诅咒
     */
    default boolean hasCurse() {
        return getCurse() != CurseType.none;
    }

    default boolean hasCursePending() {
        if (instance() instanceof ServerPlayer serverPlayer) {
            UUID uuid = serverPlayer.getUUID();
            return CurseManager.INSTANCE.playerHasCursePending(uuid);
        }
        return false;
    }

    /**
     * 是否拥有特定类型诅咒
     */
    default boolean hasCurse(CurseType curse) {
        return getCurse() == curse;
    }

    /**
     * 获取当前诅咒类型
     */
    default CurseType getCurse() {
        return CurseType.values()[instance().getData(IFWAttachments.player_curse)];
    }

    /**
     * 设置当前诅咒类型
     */
    default void ifw$setCurse(CurseType curse) {
        instance().setData(IFWAttachments.player_curse, curse.ordinal());
    }
}