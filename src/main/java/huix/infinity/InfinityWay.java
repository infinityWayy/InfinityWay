package huix.infinity;

import com.mojang.logging.LogUtils;
import huix.infinity.gameobjs.block.IFWBlocks;
import huix.infinity.gameobjs.item.IFWItems;
import huix.infinity.gameobjs.item.group.IFWItemGroup;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(InfinityWay.MOD_ID)
public final class InfinityWay {

    public static final String MOD_ID = "ifw";
    private static final Logger LOGGER = LogUtils.getLogger();


    public InfinityWay(ModContainer mod, IEventBus bus) {
        IFWBlocks.BLOCKS.register(bus);
        IFWItems.ITEMS.register(bus);
        IFWItemGroup.CREATIVE_TABS.register(bus);

        IFWEventHandler.init();
    }

}
