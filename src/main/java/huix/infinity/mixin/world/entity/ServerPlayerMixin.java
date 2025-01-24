package huix.infinity.mixin.world.entity;

import com.mojang.authlib.GameProfile;
import huix.infinity.common.world.entity.player.NutritionalStatus;
import huix.infinity.network.ClientboundSetHealthPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayer.class )
public class ServerPlayerMixin extends Player {

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
            this.connection.send(new ClientboundSetHealthPayload(foodData.ifw_maxFoodLevel(), foodData.ifw_nutritionalStatusByINT(), foodData.ifw_phytonutrients(), foodData.ifw_protein()));
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
}
