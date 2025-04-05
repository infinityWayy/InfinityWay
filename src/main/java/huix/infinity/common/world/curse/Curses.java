package huix.infinity.common.world.curse;

import huix.infinity.common.core.registries.IFWRegistries;
import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Curses {

    public static final DeferredRegister<PersistentEffect> CURSES = DeferredRegister.create(IFWRegistries.persistent_eff_registry, InfinityWay.MOD_ID);

    public static final Holder<PersistentEffect> none = CURSES.register("none", curse -> new Curse("none"));
    public static final Holder<PersistentEffect> equipment_decays_faster = CURSES.register("equipment_decays_faster", curse -> new Curse("equipment_decays_faster"));
    public static final Holder<PersistentEffect> cannot_hold_breath = CURSES.register("cannot_hold_breath", curse -> new Curse("cannot_hold_breath"));
    public static final Holder<PersistentEffect> cannot_run = CURSES.register("cannot_run", curse -> new Curse("cannot_run"));
    public static final Holder<PersistentEffect> cannot_eat_meats = CURSES.register("cannot_eat_meats", curse -> new Curse("cannot_eat_meats"));
    public static final Holder<PersistentEffect> cannot_eat_plants = CURSES.register("cannot_eat_plants", curse -> new Curse("cannot_eat_plants"));
    public static final Holder<PersistentEffect> cannot_drink = CURSES.register("cannot_drink", curse -> new Curse("cannot_drink"));
    public static final Holder<PersistentEffect> endermen_aggro = CURSES.register("endermen_aggro", curse -> new Curse("endermen_aggro"));
    public static final Holder<PersistentEffect> clumsiness = CURSES.register("clumsiness", curse -> new Curse("clumsiness"));
    public static final Holder<PersistentEffect> entanglement = CURSES.register("entanglement", curse -> new Curse("entanglement"));
    public static final Holder<PersistentEffect> cannot_wear_armor = CURSES.register("cannot_wear_armor", curse -> new Curse("cannot_wear_armor"));
    public static final Holder<PersistentEffect> cannot_open_chests = CURSES.register("cannot_open_chests", curse -> new Curse("cannot_open_chests"));
    public static final Holder<PersistentEffect> cannot_sleep = CURSES.register("cannot_sleep", curse -> new Curse("cannot_sleep"));
    public static final Holder<PersistentEffect> fear_of_spiders = CURSES.register("fear_of_spiders", curse -> new Curse("fear_of_spiders"));
    public static final Holder<PersistentEffect> fear_of_wolves = CURSES.register("fear_of_wolves", curse -> new Curse("fear_of_wolves"));
    public static final Holder<PersistentEffect> fear_of_creepers = CURSES.register("fear_of_creepers", curse -> new Curse("fear_of_creepers"));
    public static final Holder<PersistentEffect> fear_of_undead = CURSES.register("fear_of_undead", curse -> new Curse("fear_of_undead"));


}
