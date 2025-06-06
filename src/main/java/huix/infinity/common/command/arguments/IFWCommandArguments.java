package huix.infinity.common.command.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import huix.infinity.common.world.effect.UnClearEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;

import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.server.command.EnumArgument;

public class IFWCommandArguments {

    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPE = DeferredRegister.create(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, InfinityWay.MOD_ID);

    public static final DeferredHolder<ArgumentTypeInfo<?, ?>, ArgumentTypeInfo<?, ?>> curse = COMMAND_ARGUMENT_TYPE.register("curse",
            () -> ArgumentTypeInfos.registerByClass(CurseArgument.class, SingletonArgumentInfo.contextFree(CurseArgument::curse)));
}
