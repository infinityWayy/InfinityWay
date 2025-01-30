package huix.infinity.mixin.world.inventory;

import huix.infinity.common.world.block.entity.LevelFurnaceBlockEntity;
import huix.infinity.common.world.inventory.IFWFurnaceMenu;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( FurnaceFuelSlot.class )
public class FurnaceFuelSlotMixin {

//    @Shadow
//    @Final
//    private AbstractFurnaceMenu menu;
//
//    @Overwrite
//    public boolean mayPlace(ItemStack stack) {
//        boolean var0 = this.menu.isFuel(stack) || isBucket(stack);
//        if (this.menu instanceof IFWFurnaceMenu menu) {
//            System.out.println(menu.furnaceLevel() >= stack.cookingLevel());
//            menu.
//            return var0 && menu.furnaceLevel() >= stack.cookingLevel();
//        }
//
//        return this.menu.isFuel(stack) || isBucket(stack);
//    }
//
//    @Shadow
//    public static boolean isBucket(ItemStack stack) {
//        return false;
//    }

}
