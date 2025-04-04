package huix.infinity.common.command;

import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.curse.Curses;
import huix.infinity.common.world.effect.PersistentEffect;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

public class CommandHandler {

    public static int giveCurse(CommandSourceStack stack, Holder<PersistentEffect> peffect, Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Player player && peffect.value() instanceof Curse curse) {
                if (entities.size() == 1) stack.sendSuccess(() -> Component.translatable("commands.give.curse.player", curse.getDisplayName(), entities.iterator().next().getDisplayName()), true);
                else stack.sendSuccess(() -> Component.translatable("commands.give.curse.players", curse.getDisplayName(), entities.size()), true);
                player.curse(curse);
            }
        }
        return entities.size();
    }

    public static int clearCurse(CommandSourceStack stack, Collection<? extends Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Player player) {
                if (entities.size() == 1) stack.sendSuccess(() -> Component.translatable("commands.clear.curse.player", entities.iterator().next().getDisplayName()), true);
                else stack.sendSuccess(() -> Component.translatable("commands.clear.curse.players", entities.size()), true);
                player.curse((Curse) Curses.none.get());
            }
        }
        return entities.size();
    }
}
