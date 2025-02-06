package huix.infinity.common.world.entity;

import huix.infinity.common.world.entity.animal.IFWChicken;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, InfinityWay.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<IFWChicken>> CHICKEN =
            ENTITIES.register("chicken",
                    () -> EntityType.Builder.of(IFWChicken::new, MobCategory.CREATURE)
                            .sized(0.4F, 0.7F)
                            .eyeHeight(0.644F)
                            .passengerAttachments(new Vec3(0.0, 0.7, -0.1))
                            .clientTrackingRange(10)
                            .build("chicken")
            );

}
