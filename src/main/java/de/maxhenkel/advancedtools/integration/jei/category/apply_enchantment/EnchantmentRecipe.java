package de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment;

import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import net.minecraft.enchantment.EnchantmentData;

public class EnchantmentRecipe {

    private EnchantmentData enchantment;
    private AbstractTool abstractTool;
    private AdvancedToolMaterial material;

    public EnchantmentRecipe(EnchantmentData enchantment, AbstractTool abstractTool, AdvancedToolMaterial material) {
        this.enchantment = enchantment;
        this.abstractTool = abstractTool;
        this.material = material;
    }

    public EnchantmentData getEnchantment() {
        return enchantment;
    }

    public AbstractTool getAbstractTool() {
        return abstractTool;
    }

    public AdvancedToolMaterial getMaterial() {
        return material;
    }
}
