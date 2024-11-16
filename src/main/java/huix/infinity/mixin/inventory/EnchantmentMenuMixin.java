package huix.infinity.mixin.inventory;

import huix.infinity.util.IFWEnchantmentHelper;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.EnchantingTableBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Mixin( EnchantmentMenu.class )
public class EnchantmentMenuMixin extends AbstractContainerMenu {

    private EnchantmentMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    private final int enchantingMultiplier = 2;

    @Shadow
    @Final
    private Container enchantSlots;
    @Shadow
    @Final
    private ContainerLevelAccess access;
    @Shadow
    @Final
    private RandomSource random;
    @Shadow
    @Final
    private DataSlot enchantmentSeed;
    @Shadow
    @Final
    public int[] costs;
    @Shadow
    @Final
    public int[] enchantClue;
    @Shadow
    @Final
    public int[] levelClue;

    @Overwrite
    public void slotsChanged(Container inventory) {
        if (inventory == this.enchantSlots) {
            ItemStack itemstack = inventory.getItem(0);
            if (!itemstack.isEmpty() && itemstack.isEnchantable()) {
                this.access.execute((level, blockPos) -> {
                    IdMap<Holder<Enchantment>> idmap = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).asHolderIdMap();
                    int bookshelfCount = 0;

                    for (BlockPos blockpos : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
                        if (EnchantingTableBlock.isValidBookShelf(level, blockPos, blockpos)) {
                            bookshelfCount += (int) level.getBlockState(blockPos.offset(blockpos)).getEnchantPowerBonus(level, blockPos.offset(blockpos));
                        }
                    }

                    this.random.setSeed(this.enchantmentSeed.get());

                    for (int k = 0; k < 3; k++) {
                        this.costs[k] = IFWEnchantmentHelper.getExperienceCost(
                                IFWEnchantmentHelper.calculateRequiredExperienceLevel(this.random, k, bookshelfCount, itemstack, this.enchantingMultiplier));;
                        this.enchantClue[k] = -1;
                        this.levelClue[k] = -1;
                        if (IFWEnchantmentHelper.getExperienceLevel(this.costs[k]) < k + 1) {
                            this.costs[k] = 0;
                        }

                        this.costs[k] = net.neoforged.neoforge.event.EventHooks.onEnchantmentLevelSet(level, blockPos, k, bookshelfCount, itemstack, costs[k]);
                    }

                    for (int l = 0; l < 3; l++) {
                        if (this.costs[l] > 0) {
                            List<EnchantmentInstance> list = this.getEnchantmentList(level.registryAccess(), itemstack, l, this.costs[l]);
                            if (list != null && !list.isEmpty()) {
                                EnchantmentInstance enchantmentinstance = list.get(this.random.nextInt(list.size()));
                                this.enchantClue[l] = idmap.getId(enchantmentinstance.enchantment);
                                this.levelClue[l] = enchantmentinstance.level;
                            }
                        }
                    }

                    this.broadcastChanges();
                });
            } else {
                for (int i = 0; i < 3; i++) {
                    this.costs[i] = 0;
                    this.enchantClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }
    }

    @Overwrite
    private List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost) {
        this.random.setSeed(this.enchantmentSeed.get() + slot);
        Optional<HolderSet.Named<Enchantment>> optional = registryAccess.registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE);
        if (optional.isEmpty()) {
            return List.of();
        } else {
            List<EnchantmentInstance> list = IFWEnchantmentHelper.selectEnchantment(this.random, stack, cost, optional.get().stream());
            if (stack.is(Items.BOOK) && list.size() > 1) {
                list.remove(this.random.nextInt(list.size()));
            }

            return list;
        }
    }

    @Shadow
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Shadow
    public boolean stillValid(Player player) {
        return false;
    }
}
