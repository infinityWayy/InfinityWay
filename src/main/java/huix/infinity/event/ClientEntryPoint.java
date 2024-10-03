package huix.infinity.event;

import huix.infinity.InfinityWay;
import huix.infinity.gameobjs.block.IFWBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;

@EventBusSubscriber(modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ClientEntryPoint {

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    private static void clientSetup(FMLClientSetupEvent event) {
        RenderType cutout = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.adamantium_door.get(), ChunkRenderTypeSet.of(cutout));
        ItemBlockRenderTypes.setRenderLayer(IFWBlocks.adamantium_bars.get(), ChunkRenderTypeSet.of(cutout));
    }
}
