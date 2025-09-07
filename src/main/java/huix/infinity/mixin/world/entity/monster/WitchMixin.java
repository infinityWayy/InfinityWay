package huix.infinity.mixin.world.entity.monster;

import huix.infinity.common.world.curse.CurseManager;
import huix.infinity.common.world.curse.CurseType;
import huix.infinity.common.world.entity.bridge.WitchDuck;
import huix.infinity.common.world.entity.bridge.WolfDuck;
import huix.infinity.extension.func.PlayerExtension;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Witch.class)
public abstract class WitchMixin extends Raider implements WitchDuck {

    @Unique
    private int ifw$summonWolfCountdown = 0;
    @Unique
    private LivingEntity ifw$summonWolfTarget = null;
    @Unique
    private boolean ifw$hasSummonedWolves = false;

    @Unique
    public void ifw_setSummonWolfTarget(LivingEntity target) {
        this.ifw$summonWolfTarget = target;
    }

    @Unique
    public void ifw_setSummonWolfCountdown(int ticks) {
        this.ifw$summonWolfCountdown = ticks;
    }

    protected WitchMixin(EntityType<? extends Raider> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "performRangedAttack", at = @At("HEAD"))
    private void ifw$curseOnAttack(LivingEntity target, float distanceFactor, CallbackInfo ci) {
        if (target instanceof ServerPlayer player && this.getHealth() > 0.0F) {
            if (player instanceof PlayerExtension ext) {
                if (!ext.hasCurse() && !ext.hasCursePending()) {
                    CurseType curse = ifw$randomCurse();
                    CurseManager.INSTANCE.addCurse(player, this.getUUID(), this.getCustomName() != null ? this.getCustomName().getString() : "Unknown Witch", curse, 0L);
                }
            }
        }
    }

    @Inject(method = "aiStep", at = @At("RETURN"))
    private void ifw$wolfSummonTick(CallbackInfo ci) {
        if (this.ifw$summonWolfCountdown > 0) {
            this.ifw$summonWolfCountdown--;
            if (this.ifw$summonWolfCountdown == 0 && this.ifw$summonWolfTarget != null && !this.ifw$hasSummonedWolves) {
                this.ifw$hasSummonedWolves = ifw$summonWolves(this.ifw$summonWolfTarget);
            }
        }
    }

    @Unique
    private CurseType ifw$randomCurse() {
        CurseType[] types = CurseType.values();
        CurseType curse;
        Random rand = new Random();
        do {
            curse = types[rand.nextInt(types.length)];
        } while (curse == CurseType.none);
        return curse;
    }

    @Unique
    private boolean ifw$summonWolves(LivingEntity target) {
        if (target == null || target.isDeadOrDying()) return false;
        int maxWolves = new Random().nextInt(3) + 1;
        int spawned = 0;
        Level world = this.level();
        Vec3 pos = target.position();
        for (int i = 0; i < 16 && spawned < maxWolves; i++) {
            double dx = pos.x + (world.random.nextDouble() - 0.5) * 8.0;
            double dz = pos.z + (world.random.nextDouble() - 0.5) * 8.0;
            double dy = pos.y;
            Wolf wolf = EntityType.WOLF.create(world);
            if (wolf != null) {
                wolf.moveTo(dx, dy, dz, world.random.nextFloat() * 360.0F, 0.0F);
                wolf.setTarget(target);
                wolf.getPersistentData().putUUID("witch_ally", this.getUUID());
                ((WolfDuck)wolf).ifw$setWitchAlly(true);
                ((WolfDuck)wolf).ifw$setHostileToPlayers(9999999);
                world.addFreshEntity(wolf);
                spawned++;
            }
        }
        return spawned > 0;
    }
}