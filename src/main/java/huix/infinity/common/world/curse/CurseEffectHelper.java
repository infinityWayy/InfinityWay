package huix.infinity.common.world.curse;

import huix.infinity.common.world.item.IFWItems;
import huix.infinity.extension.func.PlayerExtension;
import huix.infinity.network.ClientBoundSetCursePayload;
import huix.infinity.util.ReflectHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class CurseEffectHelper {

    public static boolean isIngestionForbiddenByCurse(Player player, ItemStack itemStack) {
        if (!(player instanceof PlayerExtension ext)) return false;
        CurseType curse = ext.getCurse();

        if (itemStack.ifw_isEdible()) {
            if (itemStack.ifw_isAnimalProduct() && curse == CurseType.cannot_eat_meats) {
                learnCurseEffect(ext);
                return true;
            }
            if (itemStack.ifw_isPlant() && curse == CurseType.cannot_eat_plants) {
                learnCurseEffect(ext);
                return true;
            }
        }
        if (itemStack.ifw_isDrinkable() && curse == CurseType.cannot_drink) {
            if (itemStack.getItem() != IFWItems.bottle_of_disenchanting.get()) {
                learnCurseEffect(ext);
                return true;
            }
        }
        return false;
    }

    public static boolean canWearArmor(Player player) {
        if (!(player instanceof PlayerExtension ext)) return true;
        if (ext.getCurse() == CurseType.cannot_wear_armor) {
            learnCurseEffect(ext);
            return false;
        }
        return true;
    }

    public static void learnCurseEffect(PlayerExtension ext) {
        if (!ext.knownCurse()) {
            ext.setKnownCurse(true);
            if (ext instanceof ServerPlayer sp) {
                PacketDistributor.sendToPlayer(ReflectHelper.dyCast(sp), new ClientBoundSetCursePayload(sp.getCurse().ordinal(), true));
            }
        }
    }
}