package huix.infinity.compat.farmersdelight;

import com.mojang.logging.LogUtils;
import huix.infinity.common.world.food.IFWFoodProperties;
import huix.infinity.extension.func.FoodDataExtension;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

/**
 * 农夫乐事事件处理器
 * 处理与农夫乐事食物相关的游戏事件
 */
public class FDEventHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void register() {
        NeoForge.EVENT_BUS.register(new FDEventHandler());
        LOGGER.info("Farmers Delight event handler registered");
    }

    /**
     * 处理玩家食用农夫乐事食物事件
     */
    @SubscribeEvent
    public void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack itemStack = event.getItem();
        Item item = itemStack.getItem();

        if (FDFoodAdapter.isFarmersDelightFood(item)) {
            IFWFoodProperties ifwProps = FDFoodAdapter.getNutritionProperties(item);

            if (ifwProps != null) {
                applyIFWNutrition(player, ifwProps);
                LOGGER.debug("Applied IFW nutrition to player {} for Farmers Delight food: {}",
                        player.getName().getString(), BuiltInRegistries.ITEM.getKey(item));
            }
        }
    }

    /**
     * 应用IFW营养系统到玩家
     */
    private void applyIFWNutrition(Player player, IFWFoodProperties ifwProps) {
        FoodData foodData = player.getFoodData();

        if (foodData instanceof FoodDataExtension extension) {
            // 添加植物营养
            if (ifwProps.phytonutrients() > 0) {
                int currentPhyto = extension.ifw_phytonutrients();
                extension.ifw_phytonutrients(Math.min(currentPhyto + ifwProps.phytonutrients(), 160000));
            }

            // 添加蛋白质
            if (ifwProps.protein() > 0) {
                int currentProtein = extension.ifw_protein();
                extension.ifw_protein(Math.min(currentProtein + ifwProps.protein(), 160000));
            }

            // 添加胰岛素反应
            if (ifwProps.insulinResponse() > 0) {
                int currentInsulin = extension.ifw_insulinResponse();
                extension.ifw_insulinResponse(Math.min(currentInsulin + ifwProps.insulinResponse(), 192000));
            }

            LOGGER.debug("Updated player nutrition - Phyto: +{}, Protein: +{}, Insulin: +{}",
                    ifwProps.phytonutrients(), ifwProps.protein(), ifwProps.insulinResponse());
        }
    }

    /**
     * 玩家登录时同步农夫乐事营养数据
     */
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            // 服务器端：可以在这里发送营养数据包到客户端
            LOGGER.debug("Player {} logged in, Farmers Delight nutrition system ready",
                    player.getName().getString());
        }
    }
}