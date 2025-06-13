package huix.infinity.util;

import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.block.entity.PrivateChestBlockEntity;
import huix.infinity.init.InfinityWay;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;

import static net.minecraft.client.renderer.Sheets.*;

public class RenderHelper {

    public static final Material COPPER_CHEST_LOCATION = chestMaterial("copper_private_chest");
    public static final Material COPPER_CHEST_LOCATION_LEFT = chestMaterial("copper_private_chest_left");
    public static final Material COPPER_CHEST_LOCATION_RIGHT = chestMaterial("copper_private_chest_right");

    public static final Material SILVER_CHEST_LOCATION = chestMaterial("silver_private_chest");
    public static final Material SILVER_CHEST_LOCATION_LEFT = chestMaterial("silver_private_chest_left");
    public static final Material SILVER_CHEST_LOCATION_RIGHT = chestMaterial("silver_private_chest_right");

    public static final Material GOLD_CHEST_LOCATION = chestMaterial("gold_private_chest");
    public static final Material GOLD_CHEST_LOCATION_LEFT = chestMaterial("gold_private_chest_left");
    public static final Material GOLD_CHEST_LOCATION_RIGHT = chestMaterial("gold_private_chest_right");

    public static final Material IRON_CHEST_LOCATION = chestMaterial("iron_private_chest");
    public static final Material IRON_CHEST_LOCATION_LEFT = chestMaterial("iron_private_chest_left");
    public static final Material IRON_CHEST_LOCATION_RIGHT = chestMaterial("iron_private_chest_right");

    public static final Material ANCIENT_METAL_CHEST_LOCATION = chestMaterial("ancient_metal_private_chest");
    public static final Material ANCIENT_METAL_CHEST_LOCATION_LEFT = chestMaterial("ancient_metal_private_chest_left");
    public static final Material ANCIENT_METAL_CHEST_LOCATION_RIGHT = chestMaterial("ancient_metal_private_chest_right");

    public static final Material MITHRIL_CHEST_LOCATION = chestMaterial("mithril_private_chest");
    public static final Material MITHRIL_CHEST_LOCATION_LEFT = chestMaterial("mithril_private_chest_left");
    public static final Material MITHRIL_CHEST_LOCATION_RIGHT = chestMaterial("mithril_private_chest_right");

    public static final Material ADAMANTIUM_CHEST_LOCATION = chestMaterial("adamantium_private_chest");
    public static final Material ADAMANTIUM_CHEST_LOCATION_LEFT = chestMaterial("adamantium_private_chest_left");
    public static final Material ADAMANTIUM_CHEST_LOCATION_RIGHT = chestMaterial("adamantium_private_chest_right");

    public static Material chooseMaterial(BlockEntity blockEntity, ChestType chestType, boolean holiday) {
        if (blockEntity instanceof PrivateChestBlockEntity privateChest) {
            Block block = privateChest.getBlockState().getBlock();

            if (block == IFWBlocks.copper_private_chest.get()) {
                return chooseMaterial(chestType, COPPER_CHEST_LOCATION, COPPER_CHEST_LOCATION_LEFT, COPPER_CHEST_LOCATION_RIGHT);
            } else if (block == IFWBlocks.silver_private_chest.get()) {
                return chooseMaterial(chestType, SILVER_CHEST_LOCATION, SILVER_CHEST_LOCATION_LEFT, SILVER_CHEST_LOCATION_RIGHT);
            } else if (block == IFWBlocks.gold_private_chest.get()) {
                return chooseMaterial(chestType, GOLD_CHEST_LOCATION, GOLD_CHEST_LOCATION_LEFT, GOLD_CHEST_LOCATION_RIGHT);
            } else if (block == IFWBlocks.iron_private_chest.get()) {
                return chooseMaterial(chestType, IRON_CHEST_LOCATION, IRON_CHEST_LOCATION_LEFT, IRON_CHEST_LOCATION_RIGHT);
            } else if (block == IFWBlocks.ancient_metal_private_chest.get()) {
                return chooseMaterial(chestType, ANCIENT_METAL_CHEST_LOCATION, ANCIENT_METAL_CHEST_LOCATION_LEFT, ANCIENT_METAL_CHEST_LOCATION_RIGHT);
            } else if (block == IFWBlocks.mithril_private_chest.get()) {
                return chooseMaterial(chestType, MITHRIL_CHEST_LOCATION, MITHRIL_CHEST_LOCATION_LEFT, MITHRIL_CHEST_LOCATION_RIGHT);
            } else if (block == IFWBlocks.adamantium_private_chest.get()) {
                return chooseMaterial(chestType, ADAMANTIUM_CHEST_LOCATION, ADAMANTIUM_CHEST_LOCATION_LEFT, ADAMANTIUM_CHEST_LOCATION_RIGHT);
            }

            return chooseMaterial(chestType, COPPER_CHEST_LOCATION, COPPER_CHEST_LOCATION_LEFT, COPPER_CHEST_LOCATION_RIGHT);
        }

        if (blockEntity instanceof EnderChestBlockEntity) {
            return ENDER_CHEST_LOCATION;
        } else if (holiday) {
            return chooseMaterial(chestType, CHEST_XMAS_LOCATION, CHEST_XMAS_LOCATION_LEFT, CHEST_XMAS_LOCATION_RIGHT);
        } else {
            return blockEntity instanceof TrappedChestBlockEntity ?
                    chooseMaterial(chestType, CHEST_TRAP_LOCATION, CHEST_TRAP_LOCATION_LEFT, CHEST_TRAP_LOCATION_RIGHT) :
                    chooseMaterial(chestType, CHEST_LOCATION, CHEST_LOCATION_LEFT, CHEST_LOCATION_RIGHT);
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