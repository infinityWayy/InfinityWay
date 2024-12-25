package huix.infinity.mixin.world.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayer.class )
public class ServerPlayerMixin extends Player{

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;setHealth(F)V", ordinal = 0), method = "restoreFrom")
    private void injectLevel(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        if (this.getSyncedDeadExperience() == 10) {
            this.setSyncedDeadExperience(0);
            this.experienceLevel = that.experienceLevel;
            this.totalExperience = that.totalExperience;
            this.experienceProgress = that.experienceProgress;
        }
    }

//    @Shadow private WardenSpawnTracker wardenSpawnTracker;
//    @Shadow private RemoteChatSession chatSession;
//    @Shadow @Final public ServerPlayerGameMode gameMode;
//    @Shadow private float lastSentHealth = -1.0E8F;
//    @Shadow private int lastSentFood = -99999999;
//    @Shadow private boolean lastFoodSaturationZero = true;
//    @Shadow private int lastSentExp = -99999999;
//    @Shadow @Final private ServerRecipeBook recipeBook;
//    @Shadow public boolean seenCredits;
//    @Shadow private Vec3 enteredNetherPosition;
//    @Shadow private ChunkTrackingView chunkTrackingView;
//    @Shadow private Component tabListHeader;
//    @Shadow private Component tabListFooter;
//
//    @Overwrite
//    public void restoreFrom(ServerPlayer that, boolean keepEverything) {
//        this.wardenSpawnTracker = that.getWardenSpawnTracker().get();
//        this.chatSession = that.getChatSession();
//        this.gameMode.setGameModeForPlayer(that.gameMode.getGameModeForPlayer(), that.gameMode.getPreviousGameModeForPlayer());
//        this.onUpdateAbilities();
//        this.getAttributes().assignBaseValues(that.getAttributes());
//        this.setHealth(this.getMaxHealth());
//        if (keepEverything) {
//            this.getInventory().replaceWith(that.getInventory());
//            this.setHealth(that.getHealth());
//            this.foodData = that.getFoodData();
//
//            for(MobEffectInstance mobeffectinstance : that.getActiveEffects()) {
//                this.addEffect(new MobEffectInstance(mobeffectinstance));
//            }
//
//            this.experienceProgress = that.experienceProgress;
//            this.setScore(that.getScore());
//            this.portalProcess = that.portalProcess;
//        } else if (this.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || that.isSpectator()) {
//            this.getInventory().replaceWith(that.getInventory());
//            this.experienceLevel = that.experienceLevel;
//            this.totalExperience = that.totalExperience;
//            this.experienceProgress = that.experienceProgress;
//            this.setScore(that.getScore());
//        }
//
//        this.experienceLevel = that.experienceLevel;
//        this.totalExperience = that.totalExperience;
//
//
//        this.enchantmentSeed = that.enchantmentSeed;
//        this.enderChestInventory = that.getEnderChestInventory();
//        this.getEntityData().set(DATA_PLAYER_MODE_CUSTOMISATION, that.getEntityData().get(DATA_PLAYER_MODE_CUSTOMISATION));
//        this.lastSentExp = -1;
//        this.lastSentHealth = -1.0F;
//        this.lastSentFood = -1;
//        this.recipeBook.copyOverData(that.getRecipeBook());
//        this.seenCredits = that.seenCredits;
//        this.enteredNetherPosition = that.enteredNetherPosition;
//        this.chunkTrackingView = that.getChunkTrackingView();
//        this.setShoulderEntityLeft(that.getShoulderEntityLeft());
//        this.setShoulderEntityRight(that.getShoulderEntityRight());
//        this.setLastDeathLocation(that.getLastDeathLocation());
//        CompoundTag old = that.getPersistentData();
//        if (old.contains("PlayerPersisted")) {
//            this.getPersistentData().put("PlayerPersisted", Objects.requireNonNull(old.get("PlayerPersisted")));
//        }
//
//        EventHooks.onPlayerClone(this, that, !keepEverything);
//        this.tabListHeader = that.getTabListHeader();
//        this.tabListFooter = that.getTabListFooter();
//    }

    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Shadow
    public boolean isSpectator() {
        return false;
    }

    @Shadow
    public boolean isCreative() {
        return false;
    }
}
