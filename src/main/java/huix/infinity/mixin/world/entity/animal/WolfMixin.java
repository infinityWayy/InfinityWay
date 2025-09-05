package huix.infinity.mixin.world.entity.animal;

import huix.infinity.common.world.entity.bridge.WolfDuck;
import huix.infinity.common.world.entity.ai.HostileToPlayersTargetGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Wolf.class)
public abstract class WolfMixin implements WolfDuck {

    @Unique
    private int ifw$hostileToPlayersCountdown = 0;
    @Unique
    private boolean ifw$witchAlly = false;
    @Unique
    private boolean ifw$isAttacking = false;

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void ifw$registerHostileGoal(CallbackInfo ci) {
        Wolf self = (Wolf)(Object)this;
        self.targetSelector.addGoal(3, new HostileToPlayersTargetGoal<>(self, ServerPlayer.class, true));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void ifw$tick(CallbackInfo ci) {
        Wolf wolf = (Wolf)(Object)this;
        if (!wolf.level().isClientSide()) {
            if (ifw$hostileToPlayersCountdown > 0) ifw$hostileToPlayersCountdown--;
            if (ifw$witchAlly) ifw$hostileToPlayersCountdown = 9999999;
            if (!wolf.isTame() && !wolf.isBaby() && !wolf.isInLove() && wolf.getRandom().nextFloat() < 0.004F) {
                Player player = wolf.level().getNearestPlayer(wolf, 4.0F);
                if (player != null) ifw$hostileToPlayersCountdown = 800 + wolf.getRandom().nextInt(100);
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void ifw$save(CompoundTag tag, CallbackInfo ci) {
        tag.putInt("ifw_HostileToPlayersCountdown", ifw$hostileToPlayersCountdown);
        tag.putBoolean("ifw_WitchAlly", ifw$witchAlly);
        tag.putBoolean("ifw_IsAttacking", ifw$isAttacking);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void ifw$load(CompoundTag tag, CallbackInfo ci) {
        ifw$hostileToPlayersCountdown = tag.getInt("ifw_HostileToPlayersCountdown");
        ifw$witchAlly = tag.getBoolean("ifw_WitchAlly");
        ifw$isAttacking = tag.getBoolean("ifw_IsAttacking");
    }

    @Unique
    public boolean ifw$isHostileToPlayers() { return ifw$hostileToPlayersCountdown > 0; }
    @Unique
    public void ifw$setHostileToPlayers(int ticks) { this.ifw$hostileToPlayersCountdown = ticks; }
    @Unique
    @Override
    public void ifw$setWitchAlly(boolean flag) { this.ifw$witchAlly = flag; }
    @Unique
    public boolean ifw$isWitchAlly() { return this.ifw$witchAlly; }
    @Unique
    public void ifw$setIsAttacking(boolean attack) { this.ifw$isAttacking = attack; }
    @Unique
    public boolean ifw$isAttacking() { return this.ifw$isAttacking; }

    @Inject(method = "wantsToAttack", at = @At("HEAD"), cancellable = true)
    private void ifw$blockAttackWitch(LivingEntity target, LivingEntity owner, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof Witch witch) {
            Wolf wolf = (Wolf)(Object)this;
            CompoundTag data = wolf.getPersistentData();
            if (data.contains("witch_ally")) {
                UUID witchId = data.getUUID("witch_ally");
                if (witch.getUUID().equals(witchId)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}