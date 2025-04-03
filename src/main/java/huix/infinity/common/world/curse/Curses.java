package huix.infinity.common.world.curse;

import huix.infinity.common.core.registries.IFWRegistries;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Curses {

    public static final DeferredRegister<Curse> CURSES = DeferredRegister.create(IFWRegistries.CURSE_REGISTRY, InfinityWay.MOD_ID);

    public static final Supplier<Curse> equipment_decay = CURSES.register("equipment_decay", Curse::new);
}
