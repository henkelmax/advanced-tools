package de.maxhenkel.advancedtools.integration.jei.category.upgrade;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.integration.jei.JEIPlugin;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class UpgradeRecipeCategory implements IRecipeCategory<UpgradeRecipeWrapper> {

	private IGuiHelper helper;

	public UpgradeRecipeCategory(IGuiHelper helper) {
		this.helper=helper;
	}
	
	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(Main.MODID,
				"textures/gui/jei_crafting.png"), 0, 0, 116, 54);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.upgrading").getFormattedText();
	}

	@Override
	public String getUid() {
		return JEIPlugin.CATEGORY_UPGRADE;
	}

	@Override
	public void setRecipe(IRecipeLayout layout, UpgradeRecipeWrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup group = layout.getItemStacks();
		
		group.init(0, true,  0,  0);
		ItemStack stack=new ItemStack(wrapper.getRecipe().getAbstractTool());
        StackUtils.setMaterial(stack, wrapper.getRecipe().getMaterialOld());
		group.set(0, stack);
		
		group.init(1, true, 18, 0);

        group.init(2, true, 36, 0);
        group.init(3, true, 0, 18);
        group.init(4, true, 18, 18);
        group.init(5, true, 36, 18);
        group.init(6, true, 0, 36);
        group.init(7, true, 18, 36);
        group.init(8, true, 36, 36);

        for(int i=1; i<=8&&i<=wrapper.getRecipe().getAbstractTool().getRepairCost(stack);i++){
            group.set(i, wrapper.getRecipe().getMaterialNew().getMatcher().getAll());
        }

        group.init(9, false, 94, 18);

		ItemStack stack1=new ItemStack(wrapper.getRecipe().getAbstractTool());
		StackUtils.setMaterial(stack1, wrapper.getRecipe().getMaterialNew());
		group.set(9, stack1);
	}

	@Override
	public String getModName() {
		return Main.MODID;
	}

}
