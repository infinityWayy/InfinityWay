package huix.infinity.util;

import huix.infinity.common.world.block.entity.PrivateChestBlockEntity;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

import static net.minecraft.client.renderer.Sheets.*;

public class RenderHelper {
    public static final Material copper_chest_location = chestMaterial("copper_private_chest");

    public static Material chooseMaterial(BlockEntity blockEntity, ChestType chestType, boolean holiday) {
        if (blockEntity instanceof PrivateChestBlockEntity) {
            return copper_chest_location;
        }

        if (blockEntity instanceof EnderChestBlockEntity) {
            return ENDER_CHEST_LOCATION;
        } else if (holiday) {
            return chooseMaterial(chestType, CHEST_XMAS_LOCATION, CHEST_XMAS_LOCATION_LEFT, CHEST_XMAS_LOCATION_RIGHT);
        } else {
            return blockEntity instanceof TrappedChestBlockEntity ? chooseMaterial(chestType, CHEST_TRAP_LOCATION, CHEST_TRAP_LOCATION_LEFT, CHEST_TRAP_LOCATION_RIGHT) : chooseMaterial(chestType, CHEST_LOCATION, CHEST_LOCATION_LEFT, CHEST_LOCATION_RIGHT);
        }
    }

    private static Material chooseMaterial(ChestType chestType, Material doubleMaterial, Material leftMaterial, Material rightMaterial) {
        return switch (chestType) {
            case LEFT -> leftMaterial;
            case RIGHT -> rightMaterial;
            default -> doubleMaterial;
        };
    }

    public static Material chestMaterial(String chestName) {
        return new Material(CHEST_SHEET, ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "entity/chest/" + chestName));
    }
}
