package huix.infinity.gameobjs.item.tool.interfaces;

import huix.infinity.gameobjs.item.tier.IIFWTier;
import huix.infinity.util.DamageableItemHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class IFWTieredItem extends Item {
    private final IIFWTier tier;
    private final int numComponents;

    public IFWTieredItem(IIFWTier tier, int numComponents, Properties properties) {
        super(properties.durability(DamageableItemHelper.getMultipliedDurability(numComponents, tier.getDurability())));
        this.tier = tier;
        this.numComponents = numComponents;
    }

    public Tier getTier() {
        return this.tier;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.tier.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    public int getRepairCost() {
        return this.numComponents * 2;
    }
}
