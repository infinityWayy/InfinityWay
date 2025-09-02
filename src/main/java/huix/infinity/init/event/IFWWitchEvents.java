package huix.infinity.init.event;

import huix.infinity.common.world.curse.CurseManager;
import huix.infinity.common.world.entity.bridge.WitchDuck;
import huix.infinity.util.WitchNameManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

@EventBusSubscriber
public class IFWWitchEvents {

    @SubscribeEvent
    public static void onWitchHurt(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Witch witch) {
            if (event.getSource().getEntity() instanceof ServerPlayer player) {
                ((WitchDuck)witch).ifw_setSummonWolfTarget(player);
                ((WitchDuck)witch).ifw_setSummonWolfCountdown(60);
            }
        }
    }

    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.PositionCheck event) {
        if (event.getEntity() instanceof Witch witch) {
            String name = WitchNameManager.getUniqueName();
            witch.setCustomName(Component.literal(name));
            witch.setCustomNameVisible(true);
        }
    }

    @SubscribeEvent
    public static void onWitchDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Witch witch) {
            var name = witch.getCustomName();
            if (name != null) WitchNameManager.releaseName(name.getString());
            var server = event.getEntity().level().getServer();
            if (server != null) {
                var onlinePlayers = server.getPlayerList().getPlayers();
                CurseManager.INSTANCE.removeCursesForWitch(witch.getUUID(), onlinePlayers);
            }
        }
    }
}