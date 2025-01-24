package huix.infinity.common.world.entity;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, InfinityWay.MOD_ID);

//    public static final Holder<Attribute> ifw_block_interaction_range = ATTRIBUTES.register("player.ifw_block_interaction_range",
//            () -> new RangedAttribute("attribute.name.player.ifw_block_interaction_range", 2.75F, 0.0F, 64.0F).setSyncable(true));
//
//    public static final Holder<Attribute> ifw_entity_interaction_range = ATTRIBUTES.register("player.ifw_entity_interaction_range",
//            () -> new RangedAttribute("attribute.name.player.ifw_entity_interaction_range", 1.5F, 0.0F, 64.0F).setSyncable(true));

    public static final Holder<Attribute> ifw_player_base_healthy = ATTRIBUTES.register("player.ifw_player_base_healthy",
            () -> new RangedAttribute("attribute.name.player.ifw_player_base_healthy", 6.0F, 6.0F, 512.0F).setSyncable(true));
}
