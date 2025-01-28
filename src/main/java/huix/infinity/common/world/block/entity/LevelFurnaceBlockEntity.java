package huix.infinity.common.world.block.entity;

import huix.infinity.attachment.IFWAttachment;
import huix.infinity.common.world.item.crafting.LevelCookingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class LevelFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    private final int furnaceLevel;
    public final RecipeManager.CachedCheck<SingleRecipeInput, ? extends LevelCookingRecipe> ifw_quickCheck;

    public int furnaceLevel() {
        return furnaceLevel;
    }


    protected LevelFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, RecipeType<? extends LevelCookingRecipe> recipeType, int furnaceLevel) {
        super(type, pos, blockState, recipeType);
        this.furnaceLevel = furnaceLevel;
        this.ifw_quickCheck = RecipeManager.createCheck(recipeType);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, LevelFurnaceBlockEntity blockEntity) {
        boolean flag = blockEntity.isLit();
        boolean flag1 = false;
        if (blockEntity.isLit()) {
            blockEntity.litTime--;
        } else
            cookingLevel(0, blockEntity);

        ItemStack fuel = blockEntity.items.get(1);
        ItemStack itemstack1 = blockEntity.items.get(0);
        boolean flag2 = !itemstack1.isEmpty();
        boolean flag3 = !fuel.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2) {
            RecipeHolder<?> recipeholder;
            if (flag2) {
                recipeholder = blockEntity.ifw_quickCheck.getRecipeFor(new SingleRecipeInput(itemstack1), level).orElse(null);
            } else {
                recipeholder = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() && levelEnoughFromItem(blockEntity) && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
                blockEntity.litTime = blockEntity.getBurnDuration(fuel);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    cookingLevel(fuel.cookingLevel(), blockEntity);
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

            if (blockEntity.isLit() && levelEnough(blockEntity) && canBurn(level.registryAccess(), recipeholder, blockEntity.items, i, blockEntity)) {
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

    private static boolean levelEnoughFromItem(LevelFurnaceBlockEntity blockEntity) {
        return blockEntity.getItem(1).cookingLevel() >= recipeCookingLevel(blockEntity);
    }

    private static boolean levelEnough(LevelFurnaceBlockEntity blockEntity) {
        return cookingLevel(blockEntity) >= recipeCookingLevel(blockEntity);
    }

    private static int cookingLevel(LevelFurnaceBlockEntity blockEntity) {
        return blockEntity.getData(IFWAttachment.cooking_level.get());
    }

    private static void cookingLevel(int level, LevelFurnaceBlockEntity blockEntity) {
        blockEntity.setData(IFWAttachment.cooking_level.get(), level);
    }

    private static int recipeCookingLevel(LevelFurnaceBlockEntity blockEntity) {
        SingleRecipeInput singlerecipeinput = new SingleRecipeInput(blockEntity.getItem(0));
        return blockEntity.ifw_quickCheck.getRecipeFor(singlerecipeinput, blockEntity.getLevel())
                .map(holder -> holder.value().cookingLevel()).orElse(0);
    }


}
