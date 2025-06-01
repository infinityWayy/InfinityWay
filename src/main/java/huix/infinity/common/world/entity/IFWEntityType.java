package huix.infinity.common.world.entity;

import huix.infinity.common.world.entity.animal.IFWChicken;
import huix.infinity.common.world.entity.animal.IFWCow;
import huix.infinity.common.world.entity.animal.IFWPig;
import huix.infinity.common.world.entity.animal.IFWSheep;
import huix.infinity.common.world.entity.monster.*;
import huix.infinity.common.world.entity.monster.arachnid.*;
import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import huix.infinity.common.world.entity.monster.digger.Revenant;
import huix.infinity.common.world.entity.monster.gelatinous.*;
import huix.infinity.common.world.entity.projectile.IFWWebProjectileEntity;
import huix.infinity.common.world.entity.projectile.ThrownSlimeBall;
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

    public static final DeferredHolder<EntityType<?>, EntityType<Revenant>> REVENANT =
            ENTITIES.register("revenant",
                    () -> EntityType.Builder.of(Revenant::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .passengerAttachments(2.0125F)
                            .ridingOffset(-0.7F)
                            .clientTrackingRange(8)
                            .build("revenant")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Ghoul>> GHOUL =
            ENTITIES.register("ghoul",
                    () -> EntityType.Builder.<Ghoul>of(Ghoul::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .clientTrackingRange(8)
                            .build("ghoul")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Wight>> WIGHT =
            ENTITIES.register("wight",
                    () -> EntityType.Builder.<Wight>of(Wight::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .clientTrackingRange(8)
                            .build("wight")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<InvisibleStalker>> INVISIBLE_STALKER =
            ENTITIES.register("invisible_stalker",
                    () -> EntityType.Builder.of(InvisibleStalker::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .clientTrackingRange(8)
                            .build("invisible_stalker")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Shadow>> SHADOW =
            ENTITIES.register("shadow",
                    () -> EntityType.Builder.<Shadow>of(Shadow::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .eyeHeight(1.74F)
                            .clientTrackingRange(8)
                            .build("shadow")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<InfernoCreeper>> INFERNO_CREEPER =
            ENTITIES.register("inferno_creeper",
                    () -> EntityType.Builder.of(InfernoCreeper::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.7F)
                            .eyeHeight(1.7F)
                            .clientTrackingRange(8)
                            .build("inferno_creeper")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWSpider>> SPIDER =
            ENTITIES.register("spider",
                    () -> EntityType.Builder.of(IFWSpider::new, MobCategory.MONSTER)
                            .sized(1.4F, 0.9F)
                            .eyeHeight(0.65F)
                            .clientTrackingRange(8)
                            .build("spider")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWWebProjectileEntity>> WEB_PROJECTILE =
            ENTITIES.register("web_projectile",
                    () -> EntityType.Builder.<IFWWebProjectileEntity>of(IFWWebProjectileEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(8)
                            .updateInterval(10)
                            .build("web_projectile")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWWoodSpider>> WOOD_SPIDER =
            ENTITIES.register("wood_spider",
                    () -> EntityType.Builder.of(IFWWoodSpider::new, MobCategory.MONSTER)
                            .sized(0.84F, 0.54F)
                            .eyeHeight(0.65F)
                            .clientTrackingRange(8)
                            .build("wood_spider")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWBlackWidowSpider>> BLACK_WIDOW_SPIDER =
            ENTITIES.register("black_widow_spider",
                    () -> EntityType.Builder.of(IFWBlackWidowSpider::new, MobCategory.MONSTER)
                            .sized(0.84F, 0.54F)
                            .eyeHeight(0.65F)
                            .clientTrackingRange(8)
                            .build("black_widow_spider")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWPhaseSpider>> PHASE_SPIDER =
            ENTITIES.register("phase_spider",
                    () -> EntityType.Builder.of(IFWPhaseSpider::new, MobCategory.MONSTER)
                            .sized(0.84F, 0.54F)
                            .eyeHeight(0.65F)
                            .clientTrackingRange(8)
                            .build("phase_spider")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWCaveSpider>> CAVE_SPIDER =
            ENTITIES.register("cave_spider",
                    () -> EntityType.Builder.of(IFWCaveSpider::new, MobCategory.MONSTER)
                            .sized(0.7F, 0.5F)
                            .eyeHeight(0.45F)
                            .clientTrackingRange(8)
                            .build("cave_spider")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<IFWDemonSpider>> DEMON_SPIDER =
            ENTITIES.register("demon_spider",
                    () -> EntityType.Builder.of(IFWDemonSpider::new, MobCategory.MONSTER)
                            .sized(1.4F, 0.9F)
                            .eyeHeight(0.65F)
                            .fireImmune()
                            .clientTrackingRange(8)
                            .build("demon_spider")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Slime>> SLIME =
            ENTITIES.register("slime",
                    () -> EntityType.Builder.of(Slime::new, MobCategory.MONSTER)
                            .sized(0.52F, 0.52F)
                            .eyeHeight(0.325F)
                            .clientTrackingRange(10)
                            .build("slime")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Jelly>> JELLY =
            ENTITIES.register("jelly",
                    () -> EntityType.Builder.of(Jelly::new, MobCategory.MONSTER)
                            .sized(0.52F, 0.52F)
                            .eyeHeight(0.325F)
                            .clientTrackingRange(10)
                            .build("jelly")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Blob>> BLOB =
            ENTITIES.register("blob",
                    () -> EntityType.Builder.of(Blob::new, MobCategory.MONSTER)
                            .sized(0.52F, 0.52F)
                            .eyeHeight(0.325F)
                            .clientTrackingRange(10)
                            .build("blob")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Pudding>> PUDDING =
            ENTITIES.register("pudding",
                    () -> EntityType.Builder.of(Pudding::new, MobCategory.MONSTER)
                            .sized(0.52F, 0.52F)
                            .eyeHeight(0.325F)
                            .clientTrackingRange(10)
                            .build("pudding")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<Ooze>> OOZE =
            ENTITIES.register("ooze",
                    () -> EntityType.Builder.of(Ooze::new, MobCategory.MONSTER)
                            .sized(0.52F, 0.52F)
                            .eyeHeight(0.325F)
                            .clientTrackingRange(10)
                            .build("ooze")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<MagmaCube>> MAGMA_CUBE =
            ENTITIES.register("magma_cube",
                    () -> EntityType.Builder.of(MagmaCube::new, MobCategory.MONSTER)
                            .sized(0.52F, 0.52F)
                            .eyeHeight(0.325F)
                            .fireImmune()
                            .clientTrackingRange(10)
                            .build("magma_cube")
            );

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownSlimeBall>> THROWN_SLIME_BALL =
            ENTITIES.register("thrown_slime_ball", () -> EntityType.Builder.<ThrownSlimeBall>of(ThrownSlimeBall::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("thrown_slime_ball"));

    public static final DeferredHolder<EntityType<?>, EntityType<HellHound>> HELL_HOUND =
            ENTITIES.register("hell_hound",
                    () -> EntityType.Builder.of(HellHound::new, MobCategory.MONSTER)
                            .sized(0.6F, 0.8F)
                            .eyeHeight(0.68F)
                            .fireImmune()
                            .clientTrackingRange(8)
                            .build("hell_hound"));
}