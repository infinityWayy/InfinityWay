package huix.infinity.common.world.curse;

import net.neoforged.fml.common.asm.enumextension.ExtensionInfo;
import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CurseType implements IExtensibleEnum {
    none,
    unknown,
    equipment_decays_faster,
    cannot_hold_breath,
    cannot_run,
    cannot_eat_meats,
    cannot_eat_plants,
    cannot_drink,
    endermen_aggro,
    clumsiness,
    entanglement,
    cannot_wear_armor,
    cannot_open_chests,
    cannot_sleep,
    fear_of_spiders,
    fear_of_wolves,
    fear_of_creepers,
    fear_of_undead;

    private static final Map<String, CurseType> ID_MAP = Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(CurseType::name, (e) -> e));

    public static CurseType byName(String id) {
        return ID_MAP.get(id);
    }

    public static ExtensionInfo getExtensionInfo() {
        return ExtensionInfo.nonExtended(CurseType.class);
    }
}