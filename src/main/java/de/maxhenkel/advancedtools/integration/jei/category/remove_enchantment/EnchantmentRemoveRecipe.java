package de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment;

import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import net.minecraft.enchantment.Enchantment;

public class EnchantmentRemoveRecipe {

    private Enchantment enchantment;
    private AbstractTool abstractTool;
    private AdvancedToolMaterial material;

    public EnchantmentRemoveRecipe(Enchantment enchantment, AbstractTool abstractTool, AdvancedToolMaterial material) {
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

    public AdvancedToolMaterial getMaterial() {
        return material;
    }

}
