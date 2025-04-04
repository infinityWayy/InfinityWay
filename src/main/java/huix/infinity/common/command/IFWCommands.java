package huix.infinity.common.command;

import huix.infinity.common.core.registries.IFWRegistries;
import huix.infinity.common.world.curse.Curse;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Collection;
import java.util.function.Supplier;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class IFWCommands {

    @SubscribeEvent
    private static void onRegisterCommand(final RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("peffect")
                .requires(stack -> stack.hasPermission(2))
                .then(Commands.literal("curse")
                    .then(Commands.argument("targets", EntityArgument.players())
                    .then(Commands.argument("peffects", ResourceArgument.resource(event.getBuildContext(), IFWRegistries.persistent_eff_registry_key))
                            .executes(commandContext ->
                                    CommandHandler.giveCurse(
                                            commandContext.getSource(),
                                            ResourceArgument.getResource(commandContext, "peffects", IFWRegistries.persistent_eff_registry_key),
                                            EntityArgument.getPlayers(commandContext, "targets"))))))
                .then(Commands.literal("clear")
                        .then(Commands.literal("curse")
                            .then(Commands.argument("targets", EntityArgument.players())
                                    .executes(commandContext ->
                                            CommandHandler.clearCurse(
                                                    commandContext.getSource(),
                                                    EntityArgument.getPlayers(commandContext, "targets"))))))
        );

    }


}
