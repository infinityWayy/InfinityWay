package huix.infinity.common.world.curse;

import huix.infinity.common.core.tag.IFWItemTags;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.extension.func.PlayerExtension;
import huix.infinity.init.event.IFWSoundEvents;
import huix.infinity.network.ClientBoundSetCursePayload;
import huix.infinity.util.ReflectHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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

    public static boolean shouldBlockSprint(Player player) {
        if (!(player instanceof PlayerExtension ext)) return false;
        if (ext.getCurse() == CurseType.cannot_run) {
            learnCurseEffect(ext);
            return true;
        }
        return false;
    }

    public static int getCursedMaxAirSupply(Player player, int vanillaMaxAir) {
        if (!(player instanceof PlayerExtension ext)) return vanillaMaxAir;
        if (ext.getCurse() == CurseType.cannot_hold_breath) {
            return 90;
        }
        return vanillaMaxAir;
    }

    public static int getCursedAirSupply(Player player, int vanillaAir) {
        if (!(player instanceof PlayerExtension ext)) return vanillaAir;
        if (ext.getCurse() == CurseType.cannot_hold_breath && player.isEyeInFluid(FluidTags.WATER)) {
            learnCurseEffect(ext);
            return 90;
        }
        return vanillaAir;
    }

    public static int getCursedBubbleMax(Player player, int vanillaBubbleMax, int maxAirSupply) {
        if (!(player instanceof PlayerExtension ext)) return vanillaBubbleMax;
        if (ext.getCurse() == CurseType.cannot_hold_breath) {
            return Math.max(1, maxAirSupply / 30);
        }
        return vanillaBubbleMax;
    }

    public static boolean handleChestCurse(Player player, Block block) {
        if (!(player instanceof PlayerExtension ext)) return false;
        if (ext.getCurse() == CurseType.cannot_open_chests) {
            Item item = Item.BY_BLOCK.get(block);
            if (item != null) {
                Holder<Item> holder = BuiltInRegistries.ITEM.wrapAsHolder(item);
                if (holder.is(IFWItemTags.CHESTS)) {
                    learnCurseEffect(ext);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), IFWSoundEvents.CHEST_LOCKED.get(), SoundSource.BLOCKS, 0.2F, 1.0F);
                    return true;
                }
            }
        }
        return false;
    }

    public static void handleEndermanAggro(EnderMan enderman) {
        PlayerExtension nearestExt = null;
        Player nearestPlayer = null;
        double nearestDist = Double.MAX_VALUE;
        for (Player player : enderman.level().players()) {
            if (player instanceof PlayerExtension ext && ext.getCurse() == CurseType.endermen_aggro) {
                double dist = enderman.distanceTo(player);
                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearestPlayer = player;
                    nearestExt = ext;
                }
            }
        }
        if (nearestPlayer != null) {
            enderman.setTarget(nearestPlayer);
            if (enderman.getTarget() == nearestPlayer) {
                learnCurseEffect(nearestExt);
            }
        }
    }

    public static boolean shouldBlockSleep(Player player) {
        if (!(player instanceof PlayerExtension ext)) return false;
        if (ext.getCurse() == CurseType.cannot_sleep) {
            learnCurseEffect(ext);
            return true;
        }
        return false;
    }
}