package huix.infinity.common.core.registries;

import huix.infinity.common.world.curse.Curse;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class IFWRegistries {

    public static final ResourceKey<Registry<Curse>> CURSE_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "curse"));
    public static final Registry<Curse> CURSE_REGISTRY = new RegistryBuilder<>(CURSE_REGISTRY_KEY)
            .sync(true)
            .maxId(2048)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "empty"))
            .create();

}
