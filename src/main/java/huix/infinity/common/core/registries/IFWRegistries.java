package huix.infinity.common.core.registries;

import huix.infinity.common.world.effect.PersistentEffect;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class IFWRegistries {

    public static final ResourceKey<Registry<PersistentEffect>> persistent_eff_registry_key = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "persistent_eff"));
    public static final Registry<PersistentEffect> persistent_eff_registry = new RegistryBuilder<>(persistent_eff_registry_key)
            .sync(true)
            .maxId(2048)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(InfinityWay.MOD_ID, "empty"))
            .create();

}
