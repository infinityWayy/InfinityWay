package huix.infinity.mixin.world.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
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

import javax.annotation.Nullable;

@Mixin( Player.class )
public class PlayerMixin extends LivingEntity {
    @Shadow
    public int experienceLevel;
//    @Shadow
//    public int totalExperience;
//    @Shadow
//    public float experienceProgress;
//    @Shadow
//    private int lastLevelUpTime;

    @Overwrite
    public int getXpNeededForNextLevel() {
        return Math.abs(this.experienceLevel <= -1 ? 10 : 10 * this.experienceLevel + 20);
    }

//    @Overwrite
//    public void giveExperiencePoints(int xpPoints) {
//        //PlayerXpEvent.XpChange event = new PlayerXpEvent.XpChange(this, xpPoints);
//        if (true) {
//            this.increaseScore(xpPoints);
//            this.experienceProgress += (float)xpPoints / (float)this.getXpNeededForNextLevel();
//            //this.totalExperience = Mth.clamp(this.totalExperience + xpPoints, 0, Integer.MAX_VALUE);
//
//            while(this.experienceProgress < 0.0F) {
//                float f = this.experienceProgress * (float)this.getXpNeededForNextLevel();
//                if (this.experienceLevel > 0) {
//                    this.giveExperienceLevels(-1);
//                    this.experienceProgress = 1.0F + f / (float)this.getXpNeededForNextLevel();
//                } else {
//                    this.giveExperienceLevels(-1);
//                    this.experienceProgress = 0.0F;
//                }
//            }
//
//            while(this.experienceProgress >= 1.0F) {
//                this.experienceProgress = (this.experienceProgress - 1.0F) * (float)this.getXpNeededForNextLevel();
//                this.giveExperienceLevels(1);
//                this.experienceProgress /= (float)this.getXpNeededForNextLevel();
//            }
//
//        }
//    }

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

//    @Shadow
//    public void giveExperienceLevels(int levels) {
//
//    }
//
//    @Shadow
//    public void increaseScore(int score) {
//
//    }

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
