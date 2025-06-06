package huix.infinity.common.command;

import huix.infinity.common.world.curse.CurseType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class CommandHandler {

    public static int giveCurse(CommandSourceStack stack, CurseType curse, Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                if (entities.size() == 1) stack.sendSuccess(() -> Component.translatable("commands.give.curse.player", curse.name(), entities.iterator().next().getDisplayName()), true);
                else stack.sendSuccess(() -> Component.translatable("commands.give.curse.players", curse.name(), entities.size()), true);
                player.setCurse(curse);
            }
        }
        return entities.size();
    }

    public static int clearCurse(CommandSourceStack stack, Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                if (entities.size() == 1) stack.sendSuccess(() -> Component.translatable("commands.clear.curse.player", entities.iterator().next().getDisplayName()), true);
                else stack.sendSuccess(() -> Component.translatable("commands.clear.curse.players", entities.size()), true);
                player.setCurse(CurseType.none);
            }
        }
        return entities.size();
    }
}
