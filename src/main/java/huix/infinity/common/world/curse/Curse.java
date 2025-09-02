package huix.infinity.common.world.curse;

import java.util.UUID;

public class Curse {
    public final UUID playerUuid;
    public final UUID witchUuid;
    public final String witchName;
    public final CurseType curseType;
    public final long realizeTime; // ms
    public boolean realized = false;
    public boolean effectKnown = false;

    public Curse(UUID playerUuid, UUID witchUuid, String witchName, CurseType curseType, long realizeTime) {
        this.playerUuid = playerUuid;
        this.witchUuid = witchUuid;
        this.witchName = witchName;
        this.curseType = curseType;
        this.realizeTime = realizeTime;
    }
}