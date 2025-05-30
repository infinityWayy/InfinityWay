package huix.infinity.compat.farmersdelight;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

/**
 * 农夫乐事MOD兼容性管理器
 * 负责初始化和管理与农夫乐事MOD的所有集成功能
 */
public class FarmersDelightCompat {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void init(IEventBus eventBus) {
        LOGGER.info("Initializing Farmers Delight compatibility...");

        // 注册营养系统适配器
        FarmersDelightNutritionAdapter.init();

        // 注册服务器启动事件来应用营养数据
        eventBus.addListener(FarmersDelightCompat::onServerStarting);
        eventBus.addListener(FarmersDelightCompat::onServerStarted);

        LOGGER.info("Farmers Delight compatibility initialized successfully! Ready to register {} nutrition entries",
                FarmersDelightNutritionAdapter.getNutritionEntryCount());
    }

    private static void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting - preparing Farmers Delight integration...");
        FarmersDelightNutritionAdapter.prepareNutritionData();
    }

    private static void onServerStarted(ServerStartedEvent event) {
        LOGGER.info("Server started - applying Farmers Delight integration...");
        FarmersDelightNutritionAdapter.applyNutritionData(event.getServer());
        LOGGER.info("Farmers Delight integration fully applied!");
    }

    /**
     * 检查农夫乐事MOD是否已加载
     * @return 如果农夫乐事MOD已加载则返回true
     */
    public static boolean isFarmersDelightLoaded() {
        try {
            Class.forName("vectorwing.farmersdelight.FarmersDelight");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取兼容性状态信息
     * @return 兼容性状态字符串
     */
    public static String getCompatibilityStatus() {
        if (isFarmersDelightLoaded()) {
            return "农夫乐事MOD已加载，兼容功能已启用";
        } else {
            return "农夫乐事MOD未加载，兼容功能已禁用";
        }
    }
}