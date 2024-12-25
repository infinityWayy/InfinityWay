package huix.infinity.mixin.world.entity;

import huix.infinity.funextension.PlayerExtension;
import huix.infinity.util.ReflectHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin( Player.class )
public class PlayerMixin extends LivingEntity implements PlayerExtension {
    @Shadow
    public int experienceLevel;
    @Shadow
    public int totalExperience;
    @Shadow
    private int lastLevelUpTime;
    @Shadow
    public float experienceProgress;

    @Unique
    public int syncedDeadExperience = -1;

    @Unique
    @Override
    public int getSyncedDeadExperience() {
        return syncedDeadExperience;
    }

    @Unique
    @Override
    public void setSyncedDeadExperience(int syncedDeadExperience) {
        this.syncedDeadExperience = syncedDeadExperience;
    }

    @Overwrite
    public int getXpNeededForNextLevel() {
        return Math.abs(this.experienceLevel < 0 ? 20 : 10 * this.experienceLevel + 20);
    }

    @Override
    protected void dropExperience(@Nullable Entity entity) {
        if (this.totalExperience > 0)
            super.dropExperience(entity);
        else if (this.experienceLevel > this.ifw_getMinXpLevel()) {
            this.syncedDeadExperience = 10;
            this.giveExperiencePoints(-20);
        }
    }

    @Overwrite
    public void giveExperiencePoints(int xpPoints) {
        PlayerXpEvent.XpChange event = new PlayerXpEvent.XpChange(ReflectHelper.dyCast(this), xpPoints);
        if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
            xpPoints = event.getAmount();
            this.increaseScore(xpPoints);
            this.experienceProgress += (float)xpPoints / (float)this.getXpNeededForNextLevel();
            this.updateTotalExperience();

            while(this.experienceProgress < 0.0F) {
                float f = this.experienceProgress * (float)this.getXpNeededForNextLevel();
                if (this.experienceLevel > 0) {
                    this.giveExperienceLevels(-1);
                    this.experienceProgress = 1.0F + f / (float)this.getXpNeededForNextLevel();
                } else {
                    this.giveExperienceLevels(-1);
                    this.experienceProgress = 0.0F;
                }
            }

            while(this.experienceProgress >= 1.0F) {
                this.experienceProgress = (this.experienceProgress - 1.0F) * (float)this.getXpNeededForNextLevel();
                this.giveExperienceLevels(1);
                this.experienceProgress /= (float)this.getXpNeededForNextLevel();
            }
        }
    }

    @Overwrite
    public void onEnchantmentPerformed(ItemStack enchantedItem, int levelCost) {
        this.giveExperienceLevels(-levelCost);
        if (this.experienceLevel < this.ifw_getMinXpLevel()) {
            this.experienceLevel = this.ifw_getMinXpLevel();
//            this.experienceProgress = 0.0F;
            this.totalExperience = ifw_getXpForMinusLevel(this.ifw_getMinXpLevel());
        }
    }

    @Overwrite
    public void giveExperienceLevels(int levels) {
        PlayerXpEvent.LevelChange event = new PlayerXpEvent.LevelChange(ReflectHelper.dyCast(this), levels);
        if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
            levels = event.getLevels();
            this.experienceLevel += levels;
            if (this.experienceLevel < this.ifw_getMinXpLevel()) {
                this.experienceLevel = this.ifw_getMinXpLevel();
//                this.experienceProgress = 0.0F;
                this.totalExperience = this.ifw_getXpForMinusLevel(this.ifw_getMinXpLevel());
            }

            if (levels > 0 && this.experienceLevel % 5 == 0 && (float)this.lastLevelUpTime < (float)this.tickCount - 100.0F) {
                float f = this.experienceLevel > 30 ? 1.0F : (float)this.experienceLevel / 30.0F;
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_LEVELUP, this.getSoundSource(), f * 0.75F, 1.0F);
                this.lastLevelUpTime = this.tickCount;
            }

        }
        //this.updateTotalExperience();
    }

    @Unique
    @Override
    public int ifw_getMinXpLevel() {
        return -40;
    }

    @Overwrite
    protected int getBaseExperienceReward() {
        if (this.totalExperience > 0 && !this.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && !this.isSpectator()) {
            return this.totalExperience / 3;
        } else {
            return 0;
        }
    }

    @Unique
    public void updateTotalExperience() {
        if (this.experienceLevel >= 0) {
            this.totalExperience = (int)(5.0 * Math.pow(this.experienceLevel, 2.0) + (double)(15 * this.experienceLevel)) +
                    Mth.ceil(this.experienceProgress * (float)this.getXpNeededForNextLevel());
        } else {
            this.totalExperience = this.experienceLevel * -20;
        }

    }

    @Unique
    @Override
    public int ifw_getXpForMinusLevel(int c) {
        return c * -20;
    }

    @Overwrite
    public double blockInteractionRange() {
        double i = this.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
        if (!this.getMainHandItem().isEmpty())
            return i + this.getMainHandItem().getItem().getReachBonus();
        return i;
    }

    @Overwrite
    public double entityInteractionRange() {
        double i = this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
        if (!this.getMainHandItem().isEmpty())
            return i + this.getMainHandItem().getItem().getReachBonus();
        return i;
    }



    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public void increaseScore(int score) {
    }

    @Shadow
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Shadow
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return null;
    }

    @Shadow
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
    }

    @Shadow
    public HumanoidArm getMainArm() {
        return null;
    }
}
