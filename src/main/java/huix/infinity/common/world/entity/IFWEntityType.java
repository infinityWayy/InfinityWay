package huix.infinity.common.world.entity;

import huix.infinity.common.world.entity.animal.IFWChicken;
import huix.infinity.common.world.entity.animal.IFWCow;
import huix.infinity.common.world.entity.animal.IFWPig;
import huix.infinity.common.world.entity.animal.IFWSheep;
import huix.infinity.common.world.entity.monster.IFWZombie;
import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWEntityType {
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

    public static final DeferredHolder<EntityType<?>, EntityType<IFWSheep>> SHEEP =
            ENTITIES.register("sheep",
                    () -> EntityType.Builder.of(IFWSheep::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.3F)
                            .eyeHeight(1.235F)
                            .passengerAttachments(1.2375F)
                            .clientTrackingRange(10)
                            .build("sheep")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWPig>> PIG =
            ENTITIES.register("pig",
                    () -> EntityType.Builder.of(IFWPig::new, MobCategory.CREATURE)
                            .sized(0.9F, 0.9F)
                            .passengerAttachments(0.86875F)
                            .clientTrackingRange(10)
                            .build("pig")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWCow>> COW =
            ENTITIES.register("cow",
                    () -> EntityType.Builder.of(IFWCow::new, MobCategory.CREATURE)
                            .sized(0.9F, 1.4F)
                            .eyeHeight(1.3F)
                            .passengerAttachments(1.36875F)
                            .clientTrackingRange(10)
                            .build("cow")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWZombie>> ZOMBIE =
            ENTITIES.register("zombie",
                    () -> EntityType.Builder.<IFWZombie>of(IFWZombie::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .passengerAttachments(2.0125F)
                            .ridingOffset(-0.7F)
                            .clientTrackingRange(8)
                            .build("zombie")
            );
}
