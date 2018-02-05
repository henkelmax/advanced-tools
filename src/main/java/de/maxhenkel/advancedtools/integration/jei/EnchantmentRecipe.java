package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.ToolMaterial;
import net.minecraft.enchantment.Enchantment;

public class EnchantmentRecipe {

    private Enchantment enchantment;
    private AbstractTool abstractTool;
    private ToolMaterial material;

    public EnchantmentRecipe(Enchantment enchantment, AbstractTool abstractTool, ToolMaterial material) {
        this.enchantment = enchantment;
        this.abstractTool = abstractTool;
        this.material = material;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public AbstractTool getAbstractTool() {
        return abstractTool;
    }

    public ToolMaterial getMaterial() {
        return material;
    }
}
