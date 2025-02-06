package huix.infinity.init;

import com.mojang.logging.LogUtils;
import huix.infinity.attachment.IFWAttachments;
import huix.infinity.common.core.component.IFWDataComponents;
import huix.infinity.common.world.block.IFWBlocks;
import huix.infinity.common.world.effect.IFWMobEffects;
import huix.infinity.common.world.entity.IFWAttributes;
import huix.infinity.common.world.entity.IFWBlockEntityTypes;
import huix.infinity.common.world.entity.IFWEntity;
import huix.infinity.common.world.inventory.IFWMenuTypes;
import huix.infinity.common.world.item.IFWItems;
import huix.infinity.common.world.item.crafting.IFWRecipeSerializers;
import huix.infinity.common.world.item.crafting.IFWRecipeTypes;
import huix.infinity.common.world.item.group.IFWItemGroups;
import huix.infinity.common.world.item.tier.IFWArmorMaterials;
import huix.infinity.common.world.loot.IFWLootModifiers;
import huix.infinity.init.event.IFWEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(InfinityWay.MOD_ID)
public final class InfinityWay {
    public static final String MOD_ID = "ifw";
    private static final Logger LOGGER = LogUtils.getLogger();


    public InfinityWay(final ModContainer mod, final IEventBus bus) {
        IFWAttachments.ATTACHMENT_TYPES.register(bus);
        IFWRecipeTypes.RECIPES.register(bus);
        IFWMenuTypes.MENUS.register(bus);
        IFWLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
        IFWBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        IFWRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
        IFWArmorMaterials.ARMOR_MATERIALS.register(bus);
        IFWDataComponents.DATA_COMPONENTS.register(bus);
        IFWItemGroups.CREATIVE_TABS.register(bus);
        IFWAttributes.ATTRIBUTES.register(bus);
        IFWMobEffects.MOB_EFFECTS.register(bus);
        IFWItems.ITEMS.register(bus);
        IFWBlocks.BLOCKS.register(bus);
        IFWEntity.ENTITIES.register(bus);

        IFWEvent.init();
    }
}
