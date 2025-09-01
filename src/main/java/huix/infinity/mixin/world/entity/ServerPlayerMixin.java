package huix.infinity.mixin.world.entity;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import huix.infinity.common.world.curse.CurseType;
import huix.infinity.common.world.entity.player.NutritionalStatus;
import huix.infinity.extension.func.PlayerExtension;
import huix.infinity.network.ClientBoundSetCursePayload;
import huix.infinity.network.ClientBoundSetFoodPayload;
import huix.infinity.util.ReflectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements PlayerExtension {

    @Inject(at = @At("RETURN"), method = "setExperienceLevels")
    private void updateTotalXP_0(int level, CallbackInfo ci){
        this.ifw_updateTotalExperience();
    }

    @Inject(at = @At("RETURN"), method = "setExperiencePoints")
    private void updateTotalXP_1(int level, CallbackInfo ci){
        this.ifw_updateTotalExperience();
    }

    @Unique
    private int ifw_lastMaxFoodLevel;
    @Unique
    private NutritionalStatus ifw_lastStats;
    @Unique
    private int ifw_lastInsulinResponse;

    @Unique
    @Override
    public void setCurse(CurseType curse) {
        super.setCurse(curse);
        if (curse == CurseType.none)
            this.connection.send(new ClientboundSetActionBarTextPacket(Component.keybind("ifw.witch_curse.discurse").withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD)));
        else
            this.connection.send(new ClientboundSetActionBarTextPacket(Component.keybind("ifw.witch_curse.curse").withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD)));
        PacketDistributor.sendToPlayer(ReflectHelper.dyCast(this), new ClientBoundSetCursePayload(curse.ordinal()));
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getHealth()F", ordinal = 0, shift = At.Shift.BEFORE), method = "doTick")
    private void updateHealth(CallbackInfo ci){
        if (
                this.ifw_lastMaxFoodLevel != this.foodData.ifw_maxFoodLevel() ||
                        this.ifw_lastStats != this.foodData.ifw_nutritionalStatus() ||
                        this.ifw_lastInsulinResponse != this.foodData.ifw_insulinResponse()) {
            this.connection.send(new ClientBoundSetFoodPayload(
                    foodData.ifw_maxFoodLevel(),
                    foodData.ifw_nutritionalStatusByINT(),
                    foodData.ifw_phytonutrients(),
                    foodData.ifw_protein(),
                    foodData.ifw_insulinResponse()
            ));
            this.ifw_lastMaxFoodLevel = foodData.ifw_maxFoodLevel();
            this.ifw_lastStats = foodData.ifw_nutritionalStatus();
            this.ifw_lastInsulinResponse = foodData.ifw_insulinResponse();
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/util/Either;left(Ljava/lang/Object;)Lcom/mojang/datafixers/util/Either;", ordinal = 4), method = "lambda$startSleepInBed$13")
    private Either daySleep(Object value, @Local(argsOnly = true) BlockPos at) {
        if (!this.isCreative()) {
            Vec3 vec3 = Vec3.atBottomCenterOf(at);
            List<Monster> list = this.level().getEntitiesOfClass(Monster.class, new AABB(vec3.x() - 8.0, vec3.y() - 5.0, vec3.z() - 8.0, vec3.x() + 8.0, vec3.y() + 5.0, vec3.z() + 8.0)
                    , (monster) -> monster.isPreventingPlayerRest(this));
            if (!list.isEmpty()) {
                return Either.left(BedSleepingProblem.NOT_SAFE);
            }
        }
        return Either.right(Unit.INSTANCE);
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
            } else if (this.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value())) {
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
        } else if (this.getVehicle() instanceof Boat && this.moveDist != 0.0F) {
            this.causeFoodExhaustion(Math.abs(this.moveDist) * 0.01F);
        }

    }
}
