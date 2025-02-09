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

import java.util.*;
import java.util.stream.Stream;

public class IFWEnchantmentHelper {

    public static int getExperienceCost(final int enchantmentLevels) {
        return enchantmentLevels * 100;
    }

    public static int getExperienceLevel(final int enchantmentCost) {
        return enchantmentCost / 100;
    }

    public static int calculateRequiredExperienceLevel(final RandomSource random, final int slot, int bookshelfCount, final ItemStack stack, final int enchantingMultiplier) {
        final Item item = stack.getItem();
        if (item.getEnchantmentValue() <= 0) return 0;

        bookshelfCount = Math.min(bookshelfCount, 24);
        final int enchantmentTablePower = (bookshelfCount + 1) * 2 * enchantingMultiplier;
        final int enchantmentLevels = enchantmentLevelsByItem(enchantmentTablePower, item);

        float fraction = (slot + 1) / 3.0F;
        if (slot < 2) fraction += (random.nextFloat() - 0.5F) * 0.2F;

        return Math.max(Math.round(enchantmentLevels * fraction), 1);
    }

    private static int enchantmentLevelsByItem(final int enchantment_table_power, final Item item) {
        int value = item.getEnchantmentValue();
        if (value < 1) return 0;
        else if (enchantment_table_power <= value) return enchantment_table_power;
        else {
            int startA = value + 1;
            int endA = Math.min(2 * value, enchantment_table_power);
            int countA = endA >= startA ? endA - startA + 1 : 0;

            int startB = 2 * value + 1;
            int endB = Math.min(3 * value, enchantment_table_power);
            int countB = endB >= startB ? endB - startB + 1 : 0;

            return Math.round(value + 0.5f * countA + 0.25f * countB);
        }
    }

    public static List<EnchantmentInstance> selectEnchantment(final RandomSource random, final ItemStack stack, int cost, final Stream<Holder<Enchantment>> possibleEnchantments) {
        if (stack.getEnchantmentValue() <= 0) return Collections.emptyList();
        List<EnchantmentInstance> selectedEnchantments = new ArrayList<>();

        float levelFactor = 1.0F + (random.nextFloat() - 0.5F) * 0.5F;
        cost = getExperienceLevel(cost);
        cost = Mth.clamp(Math.round((float) cost * levelFactor), 1, Integer.MAX_VALUE);

        final List<Holder<Enchantment>> candidateEnchantments = possibleEnchantments.toList();

        while (cost > 0 && selectedEnchantments.size() < 3) {
            List<EnchantmentInstance> availableEnchantments = getAvailableEnchantmentResults(cost, stack, candidateEnchantments.stream());

            for (EnchantmentInstance selected : selectedEnchantments) EnchantmentHelper.filterCompatibleEnchantments(availableEnchantments, selected);

            if (availableEnchantments.isEmpty()) break;

            Optional<EnchantmentInstance> optionalEnchantment = WeightedRandom.getRandomItem(random, availableEnchantments);
            if (optionalEnchantment.isPresent()) {
                EnchantmentInstance chosen = optionalEnchantment.get();
                Holder<Enchantment> enchantmentHolder = chosen.enchantment;

                if (selectedEnchantments.size() < 2 && availableEnchantments.size() > 1 && enchantmentHolder.value().ifw_hasLevels() && random.nextInt(2) == 0)
                    chosen.level = random.nextInt(chosen.level) + 1;

                selectedEnchantments.add(chosen);

                int costReduction = enchantmentHolder.value().ifw_hasLevels() ? enchantmentHolder.value().getMinCost(chosen.level) : enchantmentHolder.value().getMinCost(1) + 5;
                cost -= costReduction;
            }

            if (cost < 5) break;
        }
        return selectedEnchantments;
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

    public static float getProtectionFactor(ItemStack itemStack) {
        if (!itemStack.isDamageableItem()) return 1.0F;

        final int maxDamage = itemStack.getMaxDamage();
        final int damage = itemStack.getDamageValue();

        if (damage >= maxDamage - 1) return 0.0F;

        float factor = 2.0F * (1.0F - ((float) damage / maxDamage));
        return Math.min(factor, 1.0F);
    }
}
