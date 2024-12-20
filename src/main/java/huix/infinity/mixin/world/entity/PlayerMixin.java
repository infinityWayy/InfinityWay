package huix.infinity.mixin.world.entity;

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

import javax.annotation.Nullable;

@Mixin( Player.class )
public class PlayerMixin extends LivingEntity {
    @Shadow
    public int experienceLevel;

    @Overwrite
    public int getXpNeededForNextLevel() {
        return Math.abs(this.experienceLevel <= -1 ? 10 : 10 * this.experienceLevel + 20);
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
