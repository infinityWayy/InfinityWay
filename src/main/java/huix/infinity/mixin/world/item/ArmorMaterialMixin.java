package huix.infinity.mixin.world.item;

import huix.infinity.extension.func.ArmorMaterialExtension;
import huix.infinity.util.ReflectHelper;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(ArmorMaterial.class)
public class ArmorMaterialMixin implements ArmorMaterialExtension {

    @Unique
    private int acidResistance;
    @Unique
    private float magicResistance;
    @Unique
    private int repairLevel;
    @Unique
    private Map<ArmorItem.Type, Float> ifw_defense;

    @Unique
    public float float_defense(ArmorItem.Type type) {
        return this.ifw_defense().getOrDefault(type, 0.0F);
    }

    @Override
    public Map<ArmorItem.Type, Float> ifw_defense() {
        return this.ifw_defense;
    }

    @Override
    public ArmorMaterial ifw_defense(Map<ArmorItem.Type, Float> defense) {
        this.ifw_defense = defense;
        return ReflectHelper.dyCast(this);
    }

    @Override
    public ArmorMaterial magicResistance(float magicResistance) {
        this.magicResistance = magicResistance;
        return ReflectHelper.dyCast(this);
    }

    @Override
    public ArmorMaterial acidResistance(int acidResistance) {
        this.acidResistance = acidResistance;
        return ReflectHelper.dyCast(this);
    }

    @Override
    public float magicResistance() {
        return this.magicResistance;
    }

    @Override
    public int acidResistance() {
        return this.acidResistance;
    }

    @Override
    public int repairLevel() {
        return this.repairLevel;
    }

    @Override
    public ArmorMaterial repairLevel(int repairLevel) {
        this.repairLevel = repairLevel;
        return ReflectHelper.dyCast(this);
    }
}
