package huix.infinity.util;

import com.google.common.collect.Lists;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class IFWEnchantmentHelper {

    public static int getExperienceCost(final int enchantmentLevels) {
        return enchantmentLevels * 100;
    }

    public static int getExperienceLevel(final int enchantmentCost) {
        return enchantmentCost / 100;
    }

    public static int calculateRequiredExperienceLevel(final RandomSource random, final int slot,
                                                       int bookshelfCount, final ItemStack stack, final int enchantingMultiplier) {
        Item item = stack.getItem();
        int i = item.getEnchantmentValue();
        if (i <= 0) {
            return 0;
        } else {
            if (bookshelfCount > 24) {
                bookshelfCount = 24;
            }

            int enchantment_table_power = (1 + bookshelfCount) * 2 * enchantingMultiplier;
            int enchantment_levels = getEnchantmentLevelsAlteredByItem(enchantment_table_power, item);
            float fraction = (1.0F + (float)slot) / 3.0F;
            if (slot < 2) {
                fraction += (random.nextFloat() - 0.5F) * 0.2F;
            }

            return Math.max(Math.round((float)enchantment_levels * fraction), 1);
        }
    }

    public static int getEnchantmentLevelsAlteredByItem(final int enchantment_table_power, final Item item) {
        int enchantability = item.getEnchantmentValue();
        if (enchantability < 1) {
            return 0;
        } else if (enchantment_table_power <= enchantability) {
            return enchantment_table_power;
        } else {
            float enchantmentLevelsFloat = (float) enchantability;

            for(int i = enchantability + 1; i <= enchantment_table_power; ++i) {
                if (i <= enchantability * 2) {
                    enchantmentLevelsFloat += 0.5F;
                } else {
                    if (i > enchantability * 3) {
                        break;
                    }

                    enchantmentLevelsFloat += 0.25F;
                }
            }

            return Math.round(enchantmentLevelsFloat);
        }
    }

    public static List<EnchantmentInstance> selectEnchantment(final RandomSource random, final ItemStack stack, int cost,
                                                              final Stream<Holder<Enchantment>> possibleEnchantments) {
        List<EnchantmentInstance> enchantmentsToAdd = Lists.newArrayList();
        int i = stack.getEnchantmentValue();
        if (i > 0) {
            float levelFactor = 1.0F + (random.nextFloat() - 0.5F) * 0.5F;
            cost = getExperienceLevel(cost);
            List<Holder<Enchantment>> streamList = possibleEnchantments.toList();
            List<EnchantmentInstance>  possibleInstances = getAvailableEnchantmentResults(cost, stack, streamList.stream());
            cost = Mth.clamp(Math.round((float)cost * levelFactor), 1, Integer.MAX_VALUE);

            while(cost > 0 && enchantmentsToAdd.size() <= 2 && !possibleInstances.isEmpty()) {
                possibleInstances = getAvailableEnchantmentResults(cost, stack, streamList.stream());

                Iterator<EnchantmentInstance> iterator = enchantmentsToAdd.iterator();
                EnchantmentInstance e;
                while(iterator.hasNext()) {
                    e = iterator.next();
                    EnchantmentHelper.filterCompatibleEnchantments(possibleInstances, e);
                }

                if (possibleInstances.isEmpty()) {
                    break;
                }

                Optional<EnchantmentInstance> instance = WeightedRandom.getRandomItem(random, possibleInstances);
                if (instance.isPresent()) {
                    e = instance.get();
                    if (enchantmentsToAdd.size() < 2 && possibleInstances.size() > 1 && e.enchantment.value().ifw_hasLevels() && random.nextInt(2) == 0) {
                        e.level = random.nextInt(e.level) + 1;
                    }


                    enchantmentsToAdd.add(e);
                    cost -= e.enchantment.value().ifw_hasLevels() ? e.enchantment.value().getMinCost(e.level) : e.enchantment.value().getMinCost(1) + 5;
                }

                if (cost < 5) {
                    break;
                }
            }
        }

        return enchantmentsToAdd;
    }

    public static List<EnchantmentInstance> getAvailableEnchantmentResults(int level, ItemStack stack, Stream<Holder<Enchantment>> possibleEnchantments) {
        List<EnchantmentInstance> list = Lists.newArrayList();
        // Neo: Rewrite filter logic to call isPrimaryItemFor instead of hardcoded vanilla logic.
        // The original logic is recorded in the default implementation of IItemExtension#isPrimaryItemFor.
        possibleEnchantments.filter(stack::isPrimaryItemFor).forEach(enchantmentInstance -> {
            Enchantment enchantment = enchantmentInstance.value();

            for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                if (level >= enchantment.getMinCost(i)) {
                    list.add(new EnchantmentInstance(enchantmentInstance, i));
                    break;
                }
            }
        });
        return list;
    }
}
