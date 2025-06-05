package huix.infinity.common.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import huix.infinity.util.WorldHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class IFWWorldRenderer {
    // 血月基础颜色
    private static final Vector3f BLOOD_MOON_COLOR = new Vector3f(0.6f, 0.0f, 0.0f);
    // 蓝月基础颜色
    private static final Vector3f BLUE_MOON_COLOR = new Vector3f(0.0f, 0.0f, 0.6f);
    // 血月天空颜色
    private static final Vector3f BLOOD_SKY_COLOR = new Vector3f(0.3f, 0.0f, 0.0f);
    // 血雨颜色
    private static final Vector3f BLOOD_RAIN_COLOR = new Vector3f(1.0f, 0.0f, 0.0f);
    // 光晕颜色
    private static final Vector3f HALO_COLOR = new Vector3f(0.4f, 0.0f, 0.0f);
    // 血月大小倍数
    private static final float BLOOD_MOON_SIZE = 1.5f;
    // 光晕大小倍数（相对于月亮大小）
    private static final float HALO_SIZE_MULTIPLIER = 2.0f;

    public static void renderColorMoon(float partialTicks) {
        // 获取当前客户端世界
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        // 保留雨天效果
        float rainStrength = 1.0F - level.getRainLevel(partialTicks);

        if (isBloodMoonNight(level)) {
            RenderSystem.setShaderColor(
                    BLOOD_MOON_COLOR.x(),
                    BLOOD_MOON_COLOR.y(),
                    BLOOD_MOON_COLOR.z(),
                    rainStrength
            );
        } else if (isBlueMoonNight(level)) {
            RenderSystem.setShaderColor(
                    BLUE_MOON_COLOR.x(),
                    BLUE_MOON_COLOR.y(),
                    BLUE_MOON_COLOR.z(),
                    rainStrength
            );
        }
    }

    public static Vector3f getRainColor() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null || !isBloodMoonNight(level)) {
            return null; // 返回null表示使用默认颜色
        }
        return BLOOD_RAIN_COLOR;
    }

    public static float getBloodMoonSize(float defaultSize) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null || !isBloodMoonNight(level)) {
            return defaultSize;
        }
        return defaultSize * BLOOD_MOON_SIZE;
    }

    public static boolean isBloodMoonNight(Level level) {
        // 简化的血月判定：满月且晚上
        return WorldHelper.isBloodMoon(level);
    }

    public static boolean isBlueMoonNight(Level level) {
        // 简化的血月判定：满月且晚上
        return WorldHelper.isBlueMoon(level);
    }
}
