package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.ToolMaterial;
import net.minecraft.enchantment.Enchantment;

public class UpgradeRecipe {

    private AbstractTool abstractTool;
    private ToolMaterial materialOld;
    private ToolMaterial materialNew;

    public UpgradeRecipe(AbstractTool abstractTool, ToolMaterial materialOld, ToolMaterial materialNew) {
        this.abstractTool = abstractTool;
        this.materialOld = materialOld;
        this.materialNew = materialNew;
    }

    public AbstractTool getAbstractTool() {
        return abstractTool;
    }

    public ToolMaterial getMaterialOld() {
        return materialOld;
    }

    public ToolMaterial getMaterialNew() {
        return materialNew;
    }
}
