package huix.infinity.common.world.curse;

import net.neoforged.fml.common.asm.enumextension.ExtensionInfo;
import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CurseType implements IExtensibleEnum {

    none("none"),
    equipment_decays_faster("equipment_decays_faster"),
    cannot_hold_breath("cannot_hold_breath"),
    cannot_run("cannot_run"),
    cannot_eat_meats("cannot_eat_meats"),
    cannot_eat_plants("cannot_eat_plants"),
    cannot_drink("cannot_drink"),
    endermen_aggro("endermen_aggro"),
    clumsiness("equipment_decays_faster"),
    entanglement("equipment_decays_faster"),
    cannot_wear_armor("cannot_hold_breath"),
    cannot_open_chests("cannot_run"),
    cannot_sleep("cannot_eat_meats"),
    fear_of_spiders("cannot_eat_plants"),
    fear_of_wolves("cannot_drink"),
    fear_of_creepers("endermen_aggro"),
    fear_of_undead("equipment_decays_faster");

    private static final Map<String, CurseType> ID_MAP = Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(CurseType::name, (e) -> e));

    private final String name;
    CurseType(String name) {
        this.name = name;
    }

    public static CurseType byName(String id) {
        return ID_MAP.get(id);
    }



    public static ExtensionInfo getExtensionInfo() {
        return ExtensionInfo.nonExtended(CurseType.class);
    }
}
