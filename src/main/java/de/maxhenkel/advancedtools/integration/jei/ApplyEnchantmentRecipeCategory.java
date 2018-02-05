package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
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

public class ApplyEnchantmentRecipeCategory implements IRecipeCategory<ApplyEnchantmentRecipeWrapper> {

	private IGuiHelper helper;

	public ApplyEnchantmentRecipeCategory(IGuiHelper helper) {
		this.helper=helper;
	}
	
	@Override
	public IDrawable getBackground() {
		return helper.createDrawable(new ResourceLocation(Main.MODID,
				"textures/gui/jei_crafting.png"), 0, 0, 116, 54);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.enchanting").getFormattedText();
	}

	@Override
	public String getUid() {
		return JEIPlugin.CATEGORY_ENCHANT;
	}

	@Override
	public void setRecipe(IRecipeLayout layout, ApplyEnchantmentRecipeWrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup group = layout.getItemStacks();
		
		group.init(0, true,  1,  1);
		ItemStack stack=new ItemStack(wrapper.getRecipe().getAbstractTool());
        StackUtils.setMaterial(stack, wrapper.getRecipe().getMaterial());
		group.set(0, stack);
		
		group.init(1, true, 19, 1);
		ItemStack stack1=new ItemStack(ModItems.ENCHANTMENT);
		ModItems.ENCHANTMENT.setEnchantment(stack1, wrapper.getRecipe().getEnchantment(), wrapper.getRecipe().getEnchantment().getMaxLevel());
		group.set(1, stack1);

        group.init(2, true, 37, 1);
        group.init(3, true, 1, 19);
        group.init(4, true, 19, 19);
        group.init(5, true, 37, 19);
        group.init(6, true, 1, 1);
        group.init(7, true, 19, 37);
        group.init(8, true, 37, 37);

        group.init(9, false, 95, 19);
        group.set(9, wrapper.getRecipe().getAbstractTool().applyEnchantment(stack, stack1));
	}

	@Override
	public String getModName() {
		return Main.MODID;
	}

}
