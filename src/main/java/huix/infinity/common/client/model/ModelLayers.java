package huix.infinity.common.client.model;

import huix.infinity.init.InfinityWay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayers {
    public static final ModelLayerLocation HELLHOUND = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "hellhound"), "main");

    public static final ModelLayerLocation DIRE_WOLF = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "dire_wolf"), "main");

    public static final ModelLayerLocation VAMPIRE_BAT = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "vampire_bat"), "main");

    public static final ModelLayerLocation GIANT_VAMPIRE_BAT = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "giant_vampire_bat"), "main");

}