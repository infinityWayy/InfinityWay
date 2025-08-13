package huix.infinity.mixin.world.block.entity;

import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.world.block.IFWFurnaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends BaseContainerBlockEntity {

    /**
     * @author Inf1nlty
     * @reason Overwrite to ensure the custom furnace tick logic is properly integrated, reflecting new rules for input validation and burning items.
     */
    @Overwrite
    public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        boolean flag = blockEntity.isLit();
        boolean flag1 = false;
        if (blockEntity.isLit()) {
            blockEntity.litTime--;
        } else
            ifw_cookingLevel(0, blockEntity);

        ItemStack fuel = blockEntity.items.get(1);
        ItemStack itemstack1 = blockEntity.items.get(0);
        boolean flag2 = !itemstack1.isEmpty();
        boolean flag3 = !fuel.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2) {
            RecipeHolder<?> recipeholder;
            if (flag2) {
                recipeholder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(itemstack1), level).orElse(null);
            } else {
                recipeholder = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() && ifw_levelEnoughFromItem(blockEntity, state) && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                blockEntity.litTime = blockEntity.getBurnDuration(fuel);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    ifw_cookingLevel(fuel.ifw_cookingLevel(), blockEntity);
                    flag1 = true;
                    if (fuel.hasCraftingRemainingItem())
                        blockEntity.items.set(1, fuel.getCraftingRemainingItem());
                    else
                    if (flag3) {
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            blockEntity.items.set(1, fuel.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && ifw_levelEnough(blockEntity) && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                blockEntity.cookingProgress++;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = getTotalCookTime(level, blockEntity);
                    if (burn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                        blockEntity.setRecipeUsed(recipeholder);
                    }

                    flag1 = true;
                }
            } else {
                blockEntity.cookingProgress = 0;
            }
        } else if (!blockEntity.isLit() && blockEntity.cookingProgress > 0) {
            blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
        }

        if (flag != blockEntity.isLit()) {
            flag1 = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    }

    @Unique
    private static boolean ifw_levelEnoughFromItem(AbstractFurnaceBlockEntity blockEntity, BlockState state) {

        if (state.getBlock() == Blocks.FURNACE) {
            return false;
        }
        if (state.getBlock() == Blocks.BLAST_FURNACE) {
            return true;
        }
        if (state.getBlock() instanceof IFWFurnaceBlock block) {
            return block.furnaceLevel() >= blockEntity.getItem(1).ifw_cookingLevel()
                    && blockEntity.getItem(1).ifw_cookingLevel() >= ifw_recipeCookingLevel(blockEntity);
        }
        return false;
    }
    @Unique
    private static boolean ifw_levelEnough(AbstractFurnaceBlockEntity blockEntity) {

        if (blockEntity.getBlockState().getBlock() == Blocks.FURNACE) {
            return false;
        }
        if (blockEntity.getBlockState().getBlock() == Blocks.BLAST_FURNACE) {
            return true;
        }
        return ifw_cookingLevel(blockEntity) >= ifw_recipeCookingLevel(blockEntity);
    }
    @Unique
    private static int ifw_cookingLevel(AbstractFurnaceBlockEntity blockEntity) {
        return blockEntity.getData(IFWAttachments.cooking_level.get());
    }
    @Unique
    private static void ifw_cookingLevel(int level, AbstractFurnaceBlockEntity blockEntity) {
        blockEntity.setData(IFWAttachments.cooking_level.get(), level);
    }
    @Unique
    private static int ifw_recipeCookingLevel(AbstractFurnaceBlockEntity blockEntity) {
        return blockEntity.getItem(0).ifw_beCookingLevel();
    }
    protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    /**
     * @author Inf1nlty
     * @reason Implement different burning rules for different furnace types:
     * - Vanilla furnaces: cannot burn anything
     * - Custom furnaces: can burn non-equipment items only
     * - Blast furnaces: can burn only equipment items, such as armor, weapons, and tools.
     */
    @Overwrite
    public static boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize, AbstractFurnaceBlockEntity furnace) {
        ItemStack input = inventory.getFirst();
        Item item = input.getItem();
        ItemStack fuel = inventory.get(1);

        if (furnace.getBlockState().getBlock() == Blocks.FURNACE) {
            return false;
        }
        if (furnace.getBlockState().getBlock() == Blocks.BLAST_FURNACE) {
            if (!(item instanceof ArmorItem || item instanceof TieredItem)) {
                return false;
            }
            int equipLevel = input.ifw_beCookingLevel();
            int fuelLevel = fuel.ifw_cookingLevel();
            if (fuelLevel < equipLevel) {
                return false;
            }
            if (recipe == null) return false;
            ItemStack output = recipe.value().getResultItem(registryAccess);
            if (output.isEmpty()) return false;
            ItemStack outputSlot = inventory.get(2);
            if (outputSlot.isEmpty()) return true;
            if (!outputSlot.is(output.getItem())) return false;
            int resultCount = outputSlot.getCount() + output.getCount();
            return resultCount <= outputSlot.getMaxStackSize() && resultCount <= maxStackSize;
        }
        if (item instanceof ArmorItem || item instanceof TieredItem) {
            return false;
        }
        if (recipe == null) return false;
        ItemStack output = recipe.value().getResultItem(registryAccess);
        if (output.isEmpty()) return false;
        ItemStack outputSlot = inventory.get(2);
        if (outputSlot.isEmpty()) return true;
        if (!outputSlot.is(output.getItem())) return false;
        int resultCount = outputSlot.getCount() + output.getCount();
        return resultCount <= outputSlot.getMaxStackSize() && resultCount <= maxStackSize;
    }

    @Shadow
    public static int getTotalCookTime(Level level, AbstractFurnaceBlockEntity blockEntity) {
        return 0;
    }

    @Shadow
    public static boolean burn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize, AbstractFurnaceBlockEntity furnace) {
        return false;
    }
}