package huix.infinity.mixin.inventory;

import huix.infinity.util.IFWEnchantmentHelper;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import huix.infinity.init.event.IFWSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.stream.Stream;

@Mixin( EnchantmentMenu.class )
public abstract class EnchantmentMenuMixin extends AbstractContainerMenu {

    @Unique
    private int ifw_enchantingModify = 2;

    @Overwrite
    public void slotsChanged(Container inventory) {
        if (inventory == this.enchantSlots) {
            ItemStack itemstack = inventory.getItem(0);
            if (!itemstack.isEmpty() && (itemstack.isEnchantable() || itemstack.ifw_hasEncRecipe())) {
                this.access.execute((level, pos) -> {
                    float bookShelves = 0.0F;
                    for (BlockPos blockpos : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
                        if (EnchantingTableBlock.isValidBookShelf(level, pos, blockpos))
                            bookShelves += level.getBlockState(pos.offset(blockpos)).getEnchantPowerBonus(level, pos.offset(blockpos));
                    }

                    this.enchantmentSeed.set(this.random.nextInt());

                    int l;
                    for(l= 0; l < 3; ++l) {
                        this.costs[l] = IFWEnchantmentHelper.getExperienceCost(IFWEnchantmentHelper.calculateRequiredExperienceLevel(random, l, (int) bookShelves, itemstack, this.ifw_enchantingModify));
                        if (this.costs[l] < l + 1) this.costs[l] = 0;

                        this.costs[l] = EventHooks.onEnchantmentLevelSet(level, pos, l, (int)bookShelves, itemstack, this.costs[l]);
                    }

                    for(l = 0; l < 3; ++l)
                        if (this.costs[l] > 0) this.getEnchantmentList(level.registryAccess(), itemstack, l, this.costs[l]);
                        else if (itemstack.ifw_hasEncRecipe()) this.costs[l] = itemstack.ifw_encRecipeXP();

                    this.broadcastChanges();
                });
            } else {
                for(int i = 0; i < 3; ++i)
                    this.costs[i] = 0;
            }
        }

    }

    @Unique
    private boolean ifw_canClick(Player player, int id, ItemStack enchantItem) {
        boolean flag = false;
        if (enchantItem.ifw_hasEncRecipe()) flag = player.totalExperience >= enchantItem.ifw_encRecipeXP();
        return this.costs[id] > 0 && !enchantItem.isEmpty() && ((player.totalExperience >= this.costs[id] || flag) || player.getAbilities().instabuild);
    }

    @Overwrite
    public boolean clickMenuButton(Player player, int id) {
        if (id >= 0 && id < this.costs.length) {
            ItemStack itemstack = this.enchantSlots.getItem(0);
            int i = id + 1;
            if (ifw_canClick(player, id, itemstack)) {
                this.access.execute((level, pos) -> {
                    ItemStack result = itemstack.copy();
                    if (itemstack.ifw_hasEncRecipe()) {
                        result = itemstack.ifw_encRecipeResult();
                        player.onEnchantmentPerformedPoints(itemstack, itemstack.ifw_encRecipeXP());
                        //must copy
                        this.enchantSlots.setItem(0, result.copy());
                    } else {
                        List<EnchantmentInstance> list = this.getEnchantmentList(level.registryAccess(), itemstack, id, this.costs[id]);
                        if (!list.isEmpty()) {
                            result = itemstack.getItem().applyEnchantments(itemstack, list);
                            CommonHooks.onPlayerEnchantItem(player, result, list);
                            player.onEnchantmentPerformedPoints(itemstack, this.costs[id]);
                            this.enchantSlots.setItem(0, result);
                        }
                    }
                    ifw_enchantEnd(level, pos, player, i, result);
                });
                return true;
            } else return false;
        } else {
            Util.logAndPauseIfInIde(player.getName() + " pressed invalid button id: " + id);
            return false;
        }
    }

    @Unique
    private void ifw_enchantEnd(Level level, BlockPos pos, Player player, int id, ItemStack result) {
        player.awardStat(Stats.ENCHANT_ITEM);
        if (player instanceof ServerPlayer) CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, result, id);
        this.enchantSlots.setChanged();
        this.slotsChanged(this.enchantSlots);
        level.playSound(null, pos, IFWSoundEvents.CLASSIC_HURT.get(), SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;selectEnchantment(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/item/ItemStack;ILjava/util/stream/Stream;)Ljava/util/List;"),
            method = "getEnchantmentList")
    private List<EnchantmentInstance> redirectEnchantmentList(RandomSource randomSource, ItemStack stack, int cost, Stream<Holder<Enchantment>> stream) {
        return IFWEnchantmentHelper.selectEnchantment(randomSource, stack, cost, stream);
    }

    @Shadow
    @Final
    public int[] costs;

    @Shadow @Final private Container enchantSlots;

    @Shadow protected abstract List<EnchantmentInstance> getEnchantmentList(RegistryAccess registryAccess, ItemStack stack, int slot, int cost);

    @Shadow @Final private ContainerLevelAccess access;

    @Shadow @Final private RandomSource random;

    @Shadow @Final private DataSlot enchantmentSeed;

    protected EnchantmentMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

}
