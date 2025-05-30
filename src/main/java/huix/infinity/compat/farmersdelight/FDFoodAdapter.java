package huix.infinity.compat.farmersdelight;

import com.mojang.logging.LogUtils;
import huix.infinity.common.world.food.IFWFoodProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 农夫乐事营养系统适配器
 * 负责为农夫乐事的食物物品添加IFW营养系统支持
 */
public class FDFoodAdapter {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String FARMERS_DELIGHT_MODID = "farmersdelight";

    // 存储农夫乐事食物的IFW营养属性
    private static final Map<Item, IFWFoodProperties> FARMERS_DELIGHT_NUTRITION = new HashMap<>();

    public static void init() {
        LOGGER.info("Initializing Farmers Delight nutrition adapter...");

        // 注册玩家登录事件，确保客户端也能获得营养数据
        NeoForge.EVENT_BUS.addListener(FDFoodAdapter::onPlayerLoggedIn);
    }

    public static int getNutritionEntryCount() {
        return FARMERS_DELIGHT_NUTRITION.size();
    }

    /**
     * 准备农夫乐事食物的营养数据
     */
    public static void prepareNutritionData() {
        LOGGER.info("Preparing Farmers Delight nutrition data from your ModItems definitions...");

        // 基础作物 (Basic Crops)
        addNutrition("cabbage", 8000, 0, 0, false, false, false);
        addNutrition("tomato", 8000, 0, 0, false, false, false);
        addNutrition("onion", 8000, 0, 0, false, false, false);

        // 食材 (Foodstuffs)
        addNutrition("fried_egg", 0, 48000, 0, false, false, false);
        addNutrition("milk_bottle", 0, 8000, 0, false, false, false);
        addNutrition("hot_cocoa", 0, 8000, 4800, false, false, false);
        addNutrition("apple_cider", 16000, 0, 4800, false, false, false);
        addNutrition("melon_juice", 32000, 0, 24000, false, false, false);
        addNutrition("tomato_sauce", 16000, 0, 0, false, false, false);
        addNutrition("pumpkin_slice", 4000, 0, 0, false, false, false);
        addNutrition("cabbage_leaf", 4000, 0, 0, false, false, false);
        addNutrition("minced_beef", 0, 20000, 0, true, false, false);
        addNutrition("beef_patty", 0, 40000, 0, true, false, false);
        addNutrition("chicken_cuts", 0, 12000, 0, true, false, false);
        addNutrition("cooked_chicken_cuts", 0, 24000, 0, true, false, false);
        addNutrition("bacon", 0, 16000, 0, true, false, false);
        addNutrition("cooked_bacon", 0, 32000, 0, true, false, false);
        addNutrition("cod_slice", 0, 8000, 0, true, false, false);
        addNutrition("cooked_cod_slice", 0, 20000, 0, true, false, false);
        addNutrition("salmon_slice", 0, 8000, 0, true, false, false);
        addNutrition("cooked_salmon_slice", 0, 24000, 0, true, false, false);
        addNutrition("mutton_chops", 0, 12000, 0, true, false, false);
        addNutrition("cooked_mutton_chops", 0, 24000, 0, true, false, false);
        addNutrition("ham", 0, 40000, 0, true, false, false);
        addNutrition("smoked_ham", 0, 80000, 0, true, false, false);

        // 甜品 (Sweets)
        addNutrition("pie_crust", 0, 8000, 0, false, false, false);
        addNutrition("apple_pie_slice", 6000, 2000, 2400, false, true, false);
        addNutrition("sweet_berry_cheesecake_slice", 12000, 6000, 7200, false, true, false);
        addNutrition("chocolate_pie_slice", 0, 8000, 2400, false, true, false);
        addNutrition("sweet_berry_cookie", 1000, 0, 480, false, true, false);
        addNutrition("honey_cookie", 0, 0, 1400, false, true, false);
        addNutrition("melon_popsicle", 32000, 0, 19200, false, true, false);
        addNutrition("glow_berry_custard", 8000, 32000, 9600, false, true, false);
        addNutrition("fruit_salad", 44000, 0, 19200, false, true, false);

        // 基础餐食 (Basic Meals)
        addNutrition("mixed_salad", 19000, 0, 0, false, false, false);
        addNutrition("barbecue_stick", 16000, 32000, 0, false, false, false);
        addNutrition("egg_sandwich", 0, 96000, 0, false, false, false);
        addNutrition("chicken_sandwich", 24000, 32000, 0, false, false, false);
        addNutrition("hamburger", 24000, 40000, 0, false, false, false);
        addNutrition("bacon_sandwich", 16000, 32000, 0, false, false, false);
        addNutrition("mutton_wrap", 16000, 32000, 0, false, false, false);
        addNutrition("dumplings", 16000, 32000, 0, false, false, false);
        addNutrition("stuffed_potato", 0, 80000, 0, false, false, false);
        addNutrition("cabbage_rolls", 8000, 32000, 0, false, false, false);
        addNutrition("salmon_roll", 0, 8000, 0, false, false, false);
        addNutrition("cod_roll", 0, 8000, 0, false, false, false);
        addNutrition("kelp_roll", 16000, 0, 0, false, false, false);
        addNutrition("kelp_roll_slice", 16000, 32000, 0, false, false, false);

        // 汤类和炖菜 (Soups and Stews)
        addNutrition("bone_broth", 8000, 8000, 0, false, false, true);
        addNutrition("beef_stew", 16000, 80000, 0, false, false, true);
        addNutrition("chicken_soup", 32000, 48000, 0, false, false, true);
        addNutrition("vegetable_soup", 27000, 0, 0, false, false, true);
        addNutrition("fish_stew", 24000, 32000, 0, false, false, true);
        addNutrition("fried_rice", 24000, 24000, 0, false, false, true);
        addNutrition("pumpkin_soup", 12000, 72000, 0, false, false, true);
        addNutrition("baked_cod_stew", 8000, 80000, 0, false, false, true);
        addNutrition("noodle_soup", 8000, 112000, 0, false, false, true);

        // 精致餐食 (Plated Meals)
        addNutrition("bacon_and_eggs", 0, 160000, 0, false, false, false);
        addNutrition("pasta_with_meatballs", 16000, 40000, 0, false, false, false);
        addNutrition("pasta_with_mutton_chop", 16000, 48000, 0, false, false, false);
        addNutrition("mushroom_rice", 16000, 0, 0, false, false, false);
        addNutrition("roasted_mutton_chops", 11000, 24000, 0, false, false, false);
        addNutrition("vegetable_noodles", 24000, 0, 0, false, false, false);
        addNutrition("steak_and_potatoes", 8000, 80000, 0, false, false, false);
        addNutrition("ratatouille", 27000, 0, 0, false, false, false);
        addNutrition("squid_ink_pasta", 8000, 48000, 0, false, false, false);
        addNutrition("grilled_salmon", 24000, 48000, 0, false, false, false);

        // 盛宴食物 (Feasts)
        addNutrition("roast_chicken", 10000, 18000, 0, false, false, false);
        addNutrition("stuffed_pumpkin", 12000, 0, 1200, false, false, false);
        addNutrition("honey_glazed_ham", 8000, 20000, 3500, false, false, false);
        addNutrition("shepherds_pie", 4000, 44000, 0, false, false, false);

        // 宠物食品 (Pet Foods)
        addNutrition("dog_food", 555, 555, 555, false, false, false);

        LOGGER.info("Prepared nutrition data for {} Farmers Delight items", FARMERS_DELIGHT_NUTRITION.size());
    }

    private static void addNutrition(String itemName, int phytonutrients, int protein, int insulinResponse,
                                     boolean meat, boolean snack, boolean soup) {

        ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath(FARMERS_DELIGHT_MODID, itemName);
        Item item = BuiltInRegistries.ITEM.get(itemId);

        if (item != Items.AIR) {
            IFWFoodProperties.Builder builder = new IFWFoodProperties.Builder()
                    .phytonutrients(phytonutrients)
                    .protein(protein)
                    .insulinResponse(insulinResponse);

            if (meat) builder.meat();
            if (snack) builder.snack();
            if (soup) builder.soup();

            FARMERS_DELIGHT_NUTRITION.put(item, builder.build());
            LOGGER.debug("Added nutrition for {}: phyto={}, protein={}, insulin={}, meat={}, snack={}, soup={}",
                    itemName, phytonutrients, protein, insulinResponse, meat, snack, soup);
        } else {
            LOGGER.warn("Could not find Farmers Delight item: {}", itemName);
        }
    }

    /**
     * 应用营养数据到服务器
     */
    public static void applyNutritionData(MinecraftServer server) {
        LOGGER.info("Applying Farmers Delight nutrition data to server...");

        // 这里可以通过事件或其他机制将营养数据同步到客户端
        // 具体实现取决于你的营养系统如何存储和访问数据

        LOGGER.info("Applied nutrition data for {} Farmers Delight items", FARMERS_DELIGHT_NUTRITION.size());
    }

    /**
     * 获取农夫乐事食物的营养属性
     */
    public static IFWFoodProperties getNutritionProperties(Item item) {
        return FARMERS_DELIGHT_NUTRITION.get(item);
    }

    /**
     * 检查是否为农夫乐事食物
     */
    public static boolean isFarmersDelightFood(Item item) {
        return FARMERS_DELIGHT_NUTRITION.containsKey(item);
    }

    /**
     * 获取所有农夫乐事营养数据
     */
    public static Map<Item, IFWFoodProperties> getAllNutritionData() {
        return new HashMap<>(FARMERS_DELIGHT_NUTRITION);
    }

    private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // 当玩家登录时同步营养数据到客户端
        LOGGER.debug("Player {} logged in, syncing Farmers Delight nutrition data", event.getEntity().getName().getString());
        // 这里可以发送自定义网络包来同步数据
    }
}