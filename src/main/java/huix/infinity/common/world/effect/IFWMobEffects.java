package huix.infinity.common.world.effect;

import huix.infinity.init.InfinityWay;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IFWMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, InfinityWay.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> insulin_resistance = MOB_EFFECTS.register("insulin_resistance",
            () -> new UnClearEffect(MobEffectCategory.BENEFICIAL, 12171723));
    public static final DeferredHolder<MobEffect, MobEffect> witch_curse = MOB_EFFECTS.register("witch_curse",
            () -> new UnClearEffect(MobEffectCategory.BENEFICIAL, 12171723));
}
