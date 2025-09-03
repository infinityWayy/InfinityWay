package huix.infinity.mixin.world.entity;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.curse.CurseEffectHelper;
import huix.infinity.common.world.entity.LivingEntityAccess;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {

    @Unique
    public int food_or_repair_item_pickup_cooldown = 0;

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);


    @Shadow public abstract double getAttributeValue(Holder<Attribute> attribute);

    @Shadow @Nullable public abstract MobEffectInstance getEffect(Holder<MobEffect> effect);

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z"), method = "getDamageAfterMagicAbsorb")
    public boolean removeResistanceEffect(LivingEntity instance, Holder<MobEffect> effect) {
        return false;
    }


    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z", opcode = 0), method = "tick")
    public void addResistanceEffect(CallbackInfo ci) {
        if (this.hasEffect(MobEffects.DAMAGE_RESISTANCE))
            this.setData(IFWAttachments.armor_effect.get(), (this.getEffect(MobEffects.DAMAGE_RESISTANCE).getAmplifier() + 1) * 5);
        else
            this.setData(IFWAttachments.armor_effect.get(), 0);
    }

    @Overwrite
    public int getArmorValue() {
        return Mth.floor(this.getAttributeValue(Attributes.ARMOR)) + this.getData(IFWAttachments.armor_effect.get());
    }


    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public int ifw_foodOrRepairItemPickupCoolDown() {
        return food_or_repair_item_pickup_cooldown;
    }

    public void ifw_foodOrRepairItemPickupCoolDown(int i) {
        this.food_or_repair_item_pickup_cooldown = i;
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo ci) {
        if (this.food_or_repair_item_pickup_cooldown > 0) {
            --this.food_or_repair_item_pickup_cooldown;
        }
    }

    // Entanglement Curse
    @Inject(method = "travel", at = @At("HEAD"))
    private void onTravel(Vec3 travelVector, CallbackInfo ci) {
        if ((Object)this instanceof Player player) {
            float slowdown = CurseEffectHelper.getEntangleCurseSlowdown(player);
            if (slowdown < 1.0F) {
                Vec3 motion = player.getDeltaMovement();
                player.setDeltaMovement(motion.x * slowdown, motion.y, motion.z * slowdown);
            }
        }
    }
    @Inject(method = "handleOnClimbable", at = @At("RETURN"), cancellable = true)
    private void patchClimbable(Vec3 deltaMovement, CallbackInfoReturnable<Vec3> cir) {
        if ((Object)this instanceof Player player) {
            float slowdown = CurseEffectHelper.getEntangleCurseSlowdown(player);
            if (slowdown < 1.0F) {
                Vec3 original = cir.getReturnValue();
                Vec3 result = new Vec3(original.x * slowdown, original.y, original.z * slowdown);
                cir.setReturnValue(result);
            }
        }
    }
}
