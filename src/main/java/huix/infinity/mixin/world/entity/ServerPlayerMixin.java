package huix.infinity.mixin.world.entity;

import com.mojang.authlib.GameProfile;
import huix.infinity.common.world.entity.player.NutritionalStatus;
import huix.infinity.network.ClientBoundSetHealthPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayer.class )
public abstract class ServerPlayerMixin extends Player {

    @Inject(at = @At("RETURN"), method = "setExperienceLevels")
    private void updateTotalXP_0(int level, CallbackInfo ci){
        this.ifw_updateTotalExperience();
    }

    @Inject(at = @At("RETURN"), method = "setExperiencePoints")
    private void updateTotalXP_1(int level, CallbackInfo ci){
        this.ifw_updateTotalExperience();
    }

    private int lastMaxFoodLevel;
    private NutritionalStatus lastStats;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getHealth()F", ordinal = 0, shift = At.Shift.BEFORE), method = "doTick")
    private void updateHealth(CallbackInfo ci){
        if (this.lastMaxFoodLevel !=  this.foodData.ifw_maxFoodLevel() || this.lastStats != foodData.ifw_nutritionalStatus()) {
            this.connection.send(new ClientBoundSetHealthPayload(foodData.ifw_maxFoodLevel(), foodData.ifw_nutritionalStatusByINT(), foodData.ifw_phytonutrients(), foodData.ifw_protein()));
            this.lastMaxFoodLevel = this.foodData.ifw_maxFoodLevel();
            this.lastStats = foodData.ifw_nutritionalStatus();
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isDay()Z")
            , method = "lambda$startSleepInBed$13")
    private boolean daySleep(Level instance) {
        return false;
    }


    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }
    @Shadow
    public ServerGamePacketListenerImpl connection;

    @Shadow
    public boolean isSpectator() {
        return false;
    }

    @Shadow
    public boolean isCreative() {
        return false;
    }


    @Shadow
    private static boolean didNotMove(double dx, double dy, double dz) {
        return false;
    }

    @Overwrite
    public void checkMovementStatistics(double dx, double dy, double dz) {
        if (!this.isPassenger() && !didNotMove(dx, dy, dz)) {
            if (this.isSwimming()) {
                int i = Math.round((float)Math.sqrt(dx * dx + dy * dy + dz * dz) * 100.0F);
                if (i > 0) {
                    this.awardStat(Stats.SWIM_ONE_CM, i);
                    this.causeFoodExhaustion(0.015F * (float)i * 0.01F);
                }
            } else if (this.isEyeInFluid(FluidTags.WATER)) {
                int j = Math.round((float)Math.sqrt(dx * dx + dy * dy + dz * dz) * 100.0F);
                if (j > 0) {
                    this.awardStat(Stats.WALK_UNDER_WATER_ONE_CM, j);
                    this.causeFoodExhaustion(0.015F * (float)j * 0.01F);
                }
            } else if (this.isInWater()) {
                int k = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0F);
                if (k > 0) {
                    this.awardStat(Stats.WALK_ON_WATER_ONE_CM, k);
                    this.causeFoodExhaustion(0.015F * (float)k * 0.01F);
                }
            } else if (this.onClimbable()) {
                if (dy > (double)0.0F) {
                    this.awardStat(Stats.CLIMB_ONE_CM, (int)Math.round(dy * (double)100.0F));
                    this.causeFoodExhaustion((float)dy / 10.0F);
                }
            } else if (this.onGround()) {
                int l = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0F);
                if (l > 0) {
                    if (this.isSprinting()) {
                        this.awardStat(Stats.SPRINT_ONE_CM, l);
                        this.causeFoodExhaustion(0.05F * (float)l * 0.01F);
                    } else if (this.isCrouching()) {
                        this.awardStat(Stats.CROUCH_ONE_CM, l);
                        this.causeFoodExhaustion(0.01F * (float)l * 0.01F);
                    } else {
                        this.awardStat(Stats.WALK_ONE_CM, l);
                        this.causeFoodExhaustion(0.01F * (float)l * 0.01F);
                    }
                }
            } else if (this.isFallFlying()) {
                int i1 = Math.round((float)Math.sqrt(dx * dx + dy * dy + dz * dz) * 100.0F);
                this.awardStat(Stats.AVIATE_ONE_CM, i1);
            } else {
                int j1 = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0F);
                if (j1 > 25) {
                    this.awardStat(Stats.FLY_ONE_CM, j1);
                }
            }
        }

    }
}
