package huix.infinity.init;

import com.mojang.logging.LogUtils;
import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.command.arguments.IFWCommandArguments;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.levelgen.structure.IFWStructureTypes;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.effect.IFWMobEffects;
import huix.infinity.common.world.entity.IFWAttributes;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.entity.IFWEntityType;
import huix.infinity.common.world.inventory.IFWMenuTypes;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.crafting.IFWRecipeSerializers;
import huix.infinity.common.world.item.crafting.IFWRecipeTypes;
import huix.infinity.common.world.item.group.IFWItemGroups;
import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import huix.infinity.common.world.loot.IFWLootModifiers;
import huix.infinity.init.event.IFWSoundEvents;
import huix.infinity.compat.farmersdelight.FDCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(InfinityWay.MOD_ID)
public final class InfinityWay {
    public static final String MOD_ID = "ifw";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String FARMERS_DELIGHT_MODID = "farmersdelight";
    public static boolean FarmersDelightLoaded = false;

    public InfinityWay(final IEventBus modBus) {
        final IEventBus eventBus = NeoForge.EVENT_BUS;

        // 基础系统注册
        IFWAttachments.ATTACHMENT_TYPES.register(modBus);
        IFWDataComponents.DATA_COMPONENTS.register(modBus);
        IFWAttributes.ATTRIBUTES.register(modBus);

        // 内容注册
        IFWBlocks.BLOCKS.register(modBus);
        IFWItems.ITEMS.register(modBus);
        IFWEntityType.ENTITIES.register(modBus);
        IFWItemGroups.CREATIVE_TABS.register(modBus);
        IFWArmorMaterials.ARMOR_MATERIALS.register(modBus);

        // 游戏机制注册
        IFWMobEffects.MOB_EFFECTS.register(modBus);
        IFWMenuTypes.MENUS.register(modBus);
        IFWBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modBus);
        IFWRecipeTypes.RECIPES.register(modBus);
        IFWRecipeSerializers.RECIPE_SERIALIZERS.register(modBus);

        // 世界生成注册 - 确保只注册一次
        IFWStructureTypes.STRUCTURE_TYPES.register(modBus);
        IFWBiomeModifierTypes.BIOME_MODIFIER_SERIALIZERS.register(modBus);

        // 其他系统
        IFWLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modBus);
        IFWSoundEvents.register(modBus);
        IFWCommandArguments.COMMAND_ARGUMENT_TYPE.register(modBus);

        // 农夫乐事兼容性检查和初始化
        if (ModList.get().isLoaded(FARMERS_DELIGHT_MODID)) {
            FarmersDelightLoaded = true;
            LOGGER.info("Farmers Delight detected! Initializing compatibility module...");
            FDCompat.init(eventBus);
        }

        // 事件注册
        IFWEvents.init(eventBus);

        LOGGER.info("InfinityWay mod initialized successfully");
    }
}