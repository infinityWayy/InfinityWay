package huix.infinity.init;

import com.mojang.logging.LogUtils;
import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.levelgen.structure.IFWStructureTypes;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.curse.Curses;
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
import huix.infinity.datagen.worldgen.IFWBiomeModifiers;
import huix.infinity.init.event.IFWSoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(InfinityWay.MOD_ID)
public final class InfinityWay {
    public static final String MOD_ID = "ifw";
    public static final Logger LOGGER = LogUtils.getLogger();


    public InfinityWay(final IEventBus modBus) {
        final IEventBus eventBus = NeoForge.EVENT_BUS;
        IFWAttachments.ATTACHMENT_TYPES.register(modBus);
        IFWRecipeTypes.RECIPES.register(modBus);
        IFWMenuTypes.MENUS.register(modBus);
        IFWStructureTypes.STRUCTURE_TYPES.register(modBus);
        IFWLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(modBus);
        IFWBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modBus);
        IFWRecipeSerializers.RECIPE_SERIALIZERS.register(modBus);
        IFWArmorMaterials.ARMOR_MATERIALS.register(modBus);
        IFWDataComponents.DATA_COMPONENTS.register(modBus);
        IFWItemGroups.CREATIVE_TABS.register(modBus);
        IFWAttributes.ATTRIBUTES.register(modBus);
        IFWMobEffects.MOB_EFFECTS.register(modBus);
        IFWItems.ITEMS.register(modBus);
        IFWBlocks.BLOCKS.register(modBus);
        IFWEntityType.ENTITIES.register(modBus);
        IFWBiomeModifiers.BIOME_MODIFIERS.register(modBus);
        IFWSoundEvents.register(modBus);
        Curses.CURSES.register(modBus);

        IFWEvents.init(eventBus);
    }
}
