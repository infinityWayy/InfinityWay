package huix.infinity.common.world.inventory;

import huix.infinity.common.world.block.IFWAnvilBlock;
import huix.infinity.common.world.block.entity.AnvilBlockEntity;
import huix.infinity.common.world.item.RepairableItem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class IFWAnvilMenu extends ItemCombinerMenu {
    public int repairItemCountCost;
    @Nullable
    private String itemName;
    private int durabilityCost;
    private int maxDurability = -1;
    private int currentDurability = 0;
    private final DataSlot repairLevel = DataSlot.standalone();

    protected final ContainerData anvilData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> IFWAnvilMenu.this.maxDurability;
                case 1 -> IFWAnvilMenu.this.currentDurability;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> IFWAnvilMenu.this.maxDurability = value;
                case 1 -> IFWAnvilMenu.this.currentDurability = value;

            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public IFWAnvilMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public IFWAnvilMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(IFWMenuType.anvil_menu.get(), containerId, playerInventory, access);
        this.addDataSlots(this.anvilData);
        this.addDataSlot(this.repairLevel);
        access.execute((level, pos) -> {
            Block block = level.getBlockState(pos).getBlock();
            if (block instanceof IFWAnvilBlock anvilBlock) {
                this.maxDurability = anvilBlock.maxDurability();
                this.repairLevel.set(anvilBlock.repairLevel());
            }

            BlockEntity tileEntity = level.getBlockEntity(pos);
            if (tileEntity instanceof AnvilBlockEntity anvilBlockEntity) {
                this.currentDurability = this.maxDurability - anvilBlockEntity.damage();
            }

            this.sendAllDataToRemote();
        });
    }

    @NotNull
    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(0, 27, 47, stack -> true)
                .withSlot(1, 76, 47, stack -> true)
                .withResultSlot(2, 134, 47).build();
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(BlockTags.ANVIL);
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return true;
    }

    @Override
    protected void onTake(Player player, @NotNull ItemStack stack) {
        this.inputSlots.setItem(0, ItemStack.EMPTY);
        if (this.repairItemCountCost > 0) {
            ItemStack itemstack = this.inputSlots.getItem(1);
            if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
                itemstack.shrink(this.repairItemCountCost);
                this.inputSlots.setItem(1, itemstack);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }

        this.access.execute((level, pos) -> {
            BlockState blockstate = level.getBlockState(pos);
            if (!player.getAbilities().instabuild && blockstate.is(BlockTags.ANVIL)) {
                BlockEntity oldEntity = level.getBlockEntity(pos);
                BlockState resultBlock = ((IFWAnvilBlock) blockstate.getBlock()).ifw_damage(this.durabilityCost, oldEntity);
                if (resultBlock == null) {
                    level.removeBlock(pos, false);
                    level.levelEvent(1029, pos, 0);
                } else {
                    level.setBlock(pos, resultBlock, 2);
                    BlockEntity entity = level.getBlockEntity(pos);
                    if (entity instanceof AnvilBlockEntity newEntity) {
                        this.currentDurability = this.maxDurability - ((AnvilBlockEntity)oldEntity).damage();
                        newEntity.damage(this.maxDurability - this.currentDurability);
                        this.sendAllDataToRemote();
                    }

                    level.levelEvent(1030, pos, 0);
                }
            } else {
                level.levelEvent(1030, pos, 0);
            }
        });
    }

    @Override
    public void createResult() {
        ItemStack tool = this.inputSlots.getItem(0);
        int i = 0;
        if (!tool.isEmpty() && EnchantmentHelper.canStoreEnchantments(tool)) {
            ItemStack input = tool.copy();
            ItemStack ingredient = this.inputSlots.getItem(1);
            ItemEnchantments.Mutable itemenchantments$mutable = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(input));
            this.repairItemCountCost = 0;
            boolean flag = ingredient.has(DataComponents.STORED_ENCHANTMENTS);
            if (!ingredient.isEmpty()) {
                if (input.isDamageableItem() && input.getItem().isValidRepairItem(tool, ingredient)) {
                    if (input.getItem() instanceof RepairableItem toolItem) {
                        int repairDurability = toolItem.getRepairCost();
                        if ((toolItem.getRepairLevel() > this.repairLevel()) || (repairDurability <= 0)) {
                            this.resultSlots.setItem(2, ItemStack.EMPTY);
                            return;
                        }

                        if (tool.getDamageValue() <= repairDurability) {
                            input.setDamageValue(0);
                            this.repairItemCountCost = 1;
                            this.durabilityCost = repairDurability;
                            this.resultSlots.setItem(1, input);
                            return;
                        }

                        int materialCost = Math.min((tool.getDamageValue() - tool.getDamageValue() % repairDurability) / repairDurability, ingredient.getCount());
                        input.setDamageValue(input.getDamageValue() - materialCost * repairDurability);
                        this.resultSlots.setItem(1, input);
                        this.durabilityCost = materialCost * repairDurability;
                        this.repairItemCountCost = materialCost;
                        return;
                    }
                } else {
                    if (!flag && (!input.is(ingredient.getItem()) || !input.isDamageableItem())) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        return;
                    }

                    if (input.isDamageableItem() && !flag) {
                        int l = tool.getMaxDamage() - tool.getDamageValue();
                        int i1 = ingredient.getMaxDamage() - ingredient.getDamageValue();
                        int j1 = i1 + input.getMaxDamage() * 12 / 100;
                        int k1 = l + j1;
                        int l1 = input.getMaxDamage() - k1;
                        if (l1 < 0) {
                            l1 = 0;
                        }

                        if (l1 < input.getDamageValue()) {
                            input.setDamageValue(l1);
                            i += 2;
                        }
                    }

                    //rebuild enchantment
                    if (!input.getTagEnchantments().isEmpty()) {
                        return;
                    }
                    ItemEnchantments itemenchantments = EnchantmentHelper.getEnchantmentsForCrafting(ingredient);
                    boolean flag2 = false;
                    boolean flag3 = false;
                    for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemenchantments.entrySet()) {
                        Holder<Enchantment> holder = entry.getKey();
                        int i2 = itemenchantments$mutable.getLevel(holder);
                        int j2 = entry.getIntValue();
                        j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                        Enchantment enchantment = holder.value();
                        // Neo: Respect IItemExtension#supportsEnchantment - we also delegate the logic for Enchanted Books to this method.
                        // Though we still allow creative players to combine any item with any enchantment in the anvil here.
                        boolean flag1 = tool.supportsEnchantment(holder);
                        if (this.player.getAbilities().instabuild) {
                            flag1 = true;
                        }

                        for (Holder<Enchantment> holder1 : itemenchantments$mutable.keySet()) {
                            if (!holder1.equals(holder) && !Enchantment.areCompatible(holder, holder1)) {
                                flag1 = false;
                                i++;
                            }
                        }

                        if (!flag1) {
                            flag3 = true;
                        } else {
                            flag2 = true;
                            if (j2 > enchantment.getMaxLevel()) {
                                j2 = enchantment.getMaxLevel();
                            }

                            itemenchantments$mutable.set(holder, j2);
                            int l3 = enchantment.getAnvilCost();
                            if (flag) {
                                l3 = Math.max(1, l3 / 2);
                            }

                            i += l3 * j2;
                            if (tool.getCount() > 1) {
                                i = 40;
                            }
                        }
                    }

                    if (flag3 && !flag2) {
                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        return;
                    }
                }
            }

            if (this.itemName != null && !StringUtil.isBlank(this.itemName)) {
                if (!this.itemName.equals(tool.getHoverName().getString())) {
                    i += 1;
                    input.set(DataComponents.CUSTOM_NAME, Component.literal(this.itemName));
                }
            } else if (tool.has(DataComponents.CUSTOM_NAME)) {
                i += 1;
                input.remove(DataComponents.CUSTOM_NAME);
            }
            if (flag && !input.isBookEnchantable(ingredient)) input = ItemStack.EMPTY;

            if (i <= 0) {
                input = ItemStack.EMPTY;
            }

            if (!input.isEmpty()) {
                input.set(DataComponents.REPAIR_COST, 1);
                EnchantmentHelper.setEnchantments(input, itemenchantments$mutable.toImmutable());
            }

            this.resultSlots.setItem(0, input);
            this.broadcastChanges();
        } else {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        }
    }

    public boolean setItemName(String itemName) {
        String s = validateName(itemName);
        if (s != null && !s.equals(this.itemName)) {
            this.itemName = s;
            if (this.getSlot(2).hasItem()) {
                ItemStack itemstack = this.getSlot(2).getItem();
                if (StringUtil.isBlank(s)) {
                    itemstack.remove(DataComponents.CUSTOM_NAME);
                } else {
                    itemstack.set(DataComponents.CUSTOM_NAME, Component.literal(s));
                }
            }

            this.createResult();
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    private static String validateName(String itemName) {
        String s = StringUtil.filterText(itemName);
        return s.length() <= 50 ? s : null;
    }

    public int currentDurability() {
        return this.currentDurability;
    }

    public int maxDurability() {
        return this.maxDurability;
    }

    public int repairLevel() {
        return this.repairLevel.get();
    }


}
