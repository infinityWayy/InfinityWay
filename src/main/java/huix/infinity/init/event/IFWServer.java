package huix.infinity.init.event;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = InfinityWay.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class IFWServer {




}
