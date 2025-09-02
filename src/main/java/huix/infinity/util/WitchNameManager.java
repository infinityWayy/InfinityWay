package huix.infinity.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WitchNameManager {

    private static final String[] PREFIXES = {
            "Az", "Mor", "Hex", "Um", "Lil", "Ven", "Thorn", "Cinder", "Blight", "Gloom", "Nyx", "Grim", "Sel", "Umbra",
            "Morg", "Vile", "Rav", "Shade", "Sor", "Dark", "Blood", "Dus", "Cree", "Rot", "Wraith", "Crow", "Drak", "Ash",
            "Sil", "Necro", "Void", "Frost", "Night", "Ebon", "Ghoul", "Rune", "Plague", "Bane", "Scorn", "Mist", "Sable",
            "Shadow", "Eclipse", "Star", "Moon", "Wisp", "Phantom", "Sable", "Spirit", "Hag", "Wretch", "Dire"
    };

    private static final String[] SUFFIXES = {
            "rina", "maw", "shade", "elda", "alia", "root", "ene", "ra", "ith", "song", "hex", "thorn", "veil", "gloom",
            "bane", "mire", "dust", "lash", "grave", "spire", "wisp", "chant", "grim", "gaze", "curse", "rot", "wrath",
            "star", "moon", "vex", "witch", "soul", "shroud", "loom", "dusk", "dread", "rune", "frost", "scorn", "blight",
            "raven", "plague", "sorrow", "mourne", "black", "hollow", "doom", "lurk", "night", "echo", "haze"
    };

    private static final Set<String> usedNames = ConcurrentHashMap.newKeySet();
    private static final Random random = new Random();

    public static synchronized String getUniqueName() {
        int max = PREFIXES.length * SUFFIXES.length;
        if (usedNames.size() >= max) {
            String base = PREFIXES[random.nextInt(PREFIXES.length)] + SUFFIXES[random.nextInt(SUFFIXES.length)];
            String unique = base + "_" + UUID.randomUUID().toString().substring(0, 6);
            usedNames.add(unique);
            return unique;
        }
        String name;
        int tryCount = 0;
        do {
            name = PREFIXES[random.nextInt(PREFIXES.length)] + SUFFIXES[random.nextInt(SUFFIXES.length)];
            tryCount++;
            if (tryCount > 1000) {
                name += "_" + UUID.randomUUID().toString().substring(0, 6);
                break;
            }
        } while (usedNames.contains(name));
        usedNames.add(name);
        return name;
    }

    public static void releaseName(String name) {
        usedNames.remove(name);
    }

    public static int getUsedNameCount() {
        return usedNames.size();
    }
}