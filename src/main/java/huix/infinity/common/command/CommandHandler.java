package huix.infinity.common.command;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.curse.CurseType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class CommandHandler {
    /**
     * 给予玩家指定诅咒，并设置识别状态为未识别
     */
    public static int giveCurse(CommandSourceStack stack, CurseType curse, Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                if (entities.size() == 1) stack.sendSuccess(() -> Component.translatable("commands.give.curse.player", curse.name(), entity.getDisplayName()), true);
                else stack.sendSuccess(() -> Component.translatable("commands.give.curse.players", curse.name(), entities.size()), true);
                player.ifw$setCurse(curse);
                player.setData(IFWAttachments.player_curse_known, false);
            }
        }
        return entities.size();
    }

    /**
     * 清除玩家诅咒
     */
    public static int clearCurse(CommandSourceStack stack, Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                if (entities.size() == 1) stack.sendSuccess(() -> Component.translatable("commands.clear.curse.player", entity.getDisplayName()), true);
                else stack.sendSuccess(() -> Component.translatable("commands.clear.curse.players", entities.size()), true);
                player.ifw$setCurse(CurseType.none);
            }
        }
        return entities.size();
    }
}