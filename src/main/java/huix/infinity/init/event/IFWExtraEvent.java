package huix.infinity.init.event;

import huix.infinity.common.core.registries.IFWRegistries;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.entity.animal.IFWChicken;
import huix.infinity.common.world.entity.animal.IFWCow;
import huix.infinity.common.world.entity.animal.IFWPig;
import huix.infinity.common.world.entity.animal.IFWSheep;
import huix.infinity.common.world.entity.monster.*;
import huix.infinity.common.world.entity.monster.arachnid.*;
import huix.infinity.common.world.entity.monster.digger.IFWZombie;
import huix.infinity.common.world.entity.monster.digger.Revenant;
import huix.infinity.common.world.entity.monster.gelatinous.GelatinousCube;
import huix.infinity.init.InfinityWay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class IFWExtraEvent {
    @SubscribeEvent
    public static void onRegisterAttributes(final EntityAttributeCreationEvent event) {
        // 动物实体属性注册
        event.put(IFWEntityType.CHICKEN.get(), IFWChicken.createAttributes().build());
        event.put(IFWEntityType.SHEEP.get(), IFWSheep.createAttributes().build());
        event.put(IFWEntityType.PIG.get(), IFWPig.createAttributes().build());
        event.put(IFWEntityType.COW.get(), IFWCow.createAttributes().build());

        // 怪物实体属性注册
        event.put(IFWEntityType.ZOMBIE.get(), IFWZombie.createAttributes().build());
        event.put(IFWEntityType.REVENANT.get(), Revenant.createAttributes().build());
        event.put(IFWEntityType.GHOUL.get(), Ghoul.createAttributes().build());
        event.put(IFWEntityType.WIGHT.get(), Wight.createAttributes().build());
        event.put(IFWEntityType.SHADOW.get(), Shadow.createAttributes().build());
        event.put(IFWEntityType.INVISIBLE_STALKER.get(), InvisibleStalker.createAttributes().build());
        event.put(IFWEntityType.INFERNO_CREEPER.get(), InfernoCreeper.createAttributes().build());
        event.put(IFWEntityType.HELL_HOUND.get(), HellHound.createAttributes().build());

        event.put(IFWEntityType.SPIDER.get(), IFWSpider.createAttributes().build());
        event.put(IFWEntityType.WOOD_SPIDER.get(), IFWWoodSpider.createAttributes().build());
        event.put(IFWEntityType.BLACK_WIDOW_SPIDER.get(), IFWBlackWidowSpider.createAttributes().build());
        event.put(IFWEntityType.PHASE_SPIDER.get(), IFWPhaseSpider.createAttributes().build());
        event.put(IFWEntityType.CAVE_SPIDER.get(), IFWCaveSpider.createAttributes().build());
        event.put(IFWEntityType.DEMON_SPIDER.get(), IFWDemonSpider.createAttributes().build());

        event.put(IFWEntityType.SLIME.get(), GelatinousCube.createAttributes().build());
        event.put(IFWEntityType.JELLY.get(), GelatinousCube.createAttributes().build());
        event.put(IFWEntityType.BLOB.get(), GelatinousCube.createAttributes().build());
        event.put(IFWEntityType.PUDDING.get(), GelatinousCube.createAttributes().build());
        event.put(IFWEntityType.OOZE.get(), GelatinousCube.createAttributes().build());
        event.put(IFWEntityType.MAGMA_CUBE.get(), GelatinousCube.createAttributes().build());

    }

    @SubscribeEvent
    static void registerRegistries(NewRegistryEvent event) {
        event.register(IFWRegistries.persistent_eff_registry);
    }
}