package huix.infinity.common.world.curse;

import huix.infinity.extension.func.PlayerExtension;
import huix.infinity.network.ClientBoundSetCursePayload;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CurseManager {
    public static final CurseManager INSTANCE = new CurseManager();

    private final List<Curse> curses = new CopyOnWriteArrayList<>();
    public static final Set<UUID> pendingCurseClear = new HashSet<>();

    public void addCurse(ServerPlayer player, UUID witchUuid, String witchName, CurseType curseType, long delayTicks) {
        long msDelay = delayTicks * 50;
        curses.add(new Curse(player.getUUID(), witchUuid, witchName, curseType, System.currentTimeMillis() + msDelay));
    }

    public void removeCursesForWitch(UUID witchUuid, Collection<ServerPlayer> onlinePlayers) {
        Set<UUID> affectedPlayers = new HashSet<>();
        for (Curse curse : curses) {
            if (curse.witchUuid.equals(witchUuid)) {
                affectedPlayers.add(curse.playerUuid);
            }
        }
        curses.removeIf(curse -> curse.witchUuid.equals(witchUuid));
        for (UUID playerUuid : affectedPlayers) {
            ServerPlayer online = getOnlinePlayerByUUID(playerUuid, onlinePlayers);
            if (online != null && online instanceof PlayerExtension ext) {
                ext.ifw$setCurse(CurseType.none);
                syncCurseRemoved(online);
            } else {
                pendingCurseClear.add(playerUuid);
            }
        }
    }

    public void removeCursesFromPlayer(ServerPlayer player) {
        UUID uuid = player.getUUID();
        curses.removeIf(curse -> curse.playerUuid.equals(uuid));
        if (player instanceof PlayerExtension ext) {
            ext.ifw$setCurse(CurseType.none);
            syncCurseRemoved(player);
        }
    }

//    public Curse getCurseForPlayer(String playerName) {
//        for (Curse curse : curses) {
//            if (curse.playerName.equals(playerName)) return curse;
//        }
//        return null;
//    }

//    public List<Curse> getAllCursesForPlayer(String playerName) {
//        return curses.stream().filter(c -> c.playerName.equals(playerName)).collect(Collectors.toList());
//    }

    public boolean playerHasCursePending(UUID playerUuid) {
        for (Curse curse : curses) {
            if (curse.playerUuid.equals(playerUuid) && !curse.realized) {
                return true;
            }
        }
        return false;
    }

    public void tick(Collection<ServerPlayer> players) {
        long now = System.currentTimeMillis();
        for (Curse curse : curses) {
            if (!curse.realized && now >= curse.realizeTime) {
                curse.realized = true;
                for (ServerPlayer player : players) {
                    if (player.getUUID().equals(curse.playerUuid)) {
                        if (player instanceof PlayerExtension ext) {
                            ext.setKnownCurse(false);
                            ext.ifw$setCurse(curse.curseType);
                        }
                        var msg = curse.curseType == CurseType.none
                                ? Component.translatable("ifw.witch_curse.discurse", curse.witchName).withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD)
                                : Component.translatable("ifw.witch_curse.curse", curse.witchName).withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD);
                        player.connection.send(new ClientboundSetActionBarTextPacket(msg));
                        PacketDistributor.sendToPlayer(player, new ClientBoundSetCursePayload(curse.curseType.ordinal(), false));
                        player.level().addParticle(ParticleTypes.WITCH, player.getX(), player.getY() + 1, player.getZ(), 0.0, 0.0, 0.0);
                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                curse.curseType == CurseType.none ? SoundEvents.WITCH_AMBIENT : SoundEvents.WITCH_CELEBRATE,
                                player.getSoundSource(), 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    private ServerPlayer getOnlinePlayerByUUID(UUID uuid, Collection<ServerPlayer> onlinePlayers) {
        for (ServerPlayer player : onlinePlayers) {
            if (player.getUUID().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public void syncCurseRemoved(ServerPlayer player) {
        var msg = Component.keybind("ifw.witch_curse.discurse").withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD);
        player.connection.send(new ClientboundSetActionBarTextPacket(msg));
        PacketDistributor.sendToPlayer(player, new ClientBoundSetCursePayload(CurseType.none.ordinal(), true));
    }

}