package de.maxhenkel.advancedtools.integration.jei.category.upgrade;

import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;

public class UpgradeRecipe {

    private AbstractTool abstractTool;
    private AdvancedToolMaterial materialOld;
    private AdvancedToolMaterial materialNew;

    public UpgradeRecipe(AbstractTool abstractTool, AdvancedToolMaterial materialOld, AdvancedToolMaterial materialNew) {
        this.abstractTool = abstractTool;
        this.materialOld = materialOld;
        this.materialNew = materialNew;
    }

    public AbstractTool getAbstractTool() {
        return abstractTool;
    }

    public AdvancedToolMaterial getMaterialOld() {
        return materialOld;
    }

    public AdvancedToolMaterial getMaterialNew() {
        return materialNew;
    }
}
