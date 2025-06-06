package huix.infinity.common.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import huix.infinity.common.world.curse.CurseType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;


public class CurseArgument implements ArgumentType<CurseType> {

    private static final DynamicCommandExceptionType ERROR_INVALID = new DynamicCommandExceptionType(
            o -> Component.translatableEscape("argument.curse.invalid", o)
    );

    @Override
    public CurseType parse(StringReader reader) throws CommandSyntaxException {
        String s = reader.readUnquotedString();
        CurseType curseType = CurseType.byName(s);

        if (curseType == null) throw ERROR_INVALID.createWithContext(reader, s);
        else return curseType;
    }

    public static CurseType getInstance(CommandContext<CommandSourceStack> context, String argument) {
        return context.getArgument(argument, CurseType.class);
    }

    public static CurseArgument curse() {
        return new CurseArgument();
    }


    @Override
    public Collection<String> getExamples() {
        return Arrays.asList("equipment_decays_faster", "cannot_hold_breath");
    }
}
