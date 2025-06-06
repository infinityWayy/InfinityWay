package huix.infinity.common.command;

import huix.infinity.common.command.arguments.CurseArgument;
import huix.infinity.common.core.registries.IFWRegistries;
import huix.infinity.init.InfinityWay;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.checkerframework.checker.units.qual.C;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class IFWCommands {

    @SubscribeEvent
    private static void onRegisterCommand(final RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("curse")
                .requires(stack -> stack.hasPermission(2))
                .then(Commands.literal("curse")
                    .then(Commands.argument("targets", EntityArgument.players())
                    .then(Commands.argument("curse", new CurseArgument())
                            .executes(commandContext ->
                                    CommandHandler.giveCurse(
                                            commandContext.getSource(),
                                            CurseArgument.getInstance(commandContext, "curse"),
                                            EntityArgument.getPlayers(commandContext, "targets"))))))
                .then(Commands.literal("clear")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(commandContext ->
                                        CommandHandler.clearCurse(
                                                commandContext.getSource(),
                                                EntityArgument.getPlayers(commandContext, "targets")))))
        );

    }


}
