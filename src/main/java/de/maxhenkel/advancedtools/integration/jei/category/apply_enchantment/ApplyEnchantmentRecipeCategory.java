package de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.integration.jei.JEIPlugin;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ApplyEnchantmentRecipeCategory implements IRecipeCategory<EnchantmentRecipe> {

    private IGuiHelper helper;

    public ApplyEnchantmentRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID, "textures/gui/jei_crafting.png"), 0, 0, 116, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(ModItems.ENCHANTMENT));
    }

    @Override
    public void setIngredients(EnchantmentRecipe recipe, IIngredients ingredients) {
        ItemStack enchantment = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantment, recipe.getEnchantment().enchantment, recipe.getEnchantment().enchantmentLevel);
        ItemStack tool = new ItemStack(recipe.getAbstractTool());
        StackUtils.setMaterial(tool, recipe.getMaterial());
        ingredients.setInputs(VanillaTypes.ITEM, ImmutableList.of(enchantment, tool));
        ItemStack tool2 = recipe.getAbstractTool().applyEnchantment(tool, enchantment);
        ingredients.setOutput(VanillaTypes.ITEM, tool2);
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.enchanting").getFormattedText();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_ENCHANT;
    }

    @Override
    public Class<? extends EnchantmentRecipe> getRecipeClass() {
        return EnchantmentRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, EnchantmentRecipe wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 0);
        ItemStack stack = new ItemStack(wrapper.getAbstractTool());
        StackUtils.setMaterial(stack, wrapper.getMaterial());
        group.set(0, stack);

        group.init(1, true, 18, 0);
        ItemStack stack1 = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(stack1, wrapper.getEnchantment().enchantment, wrapper.getEnchantment().enchantmentLevel);
        group.set(1, stack1);

        group.init(2, true, 36, 0);
        group.init(3, true, 0, 18);
        group.init(4, true, 18, 18);
        group.init(5, true, 36, 18);
        group.init(6, true, 0, 36);
        group.init(7, true, 18, 36);
        group.init(8, true, 36, 36);

        group.init(9, false, 94, 18);
        group.set(9, wrapper.getAbstractTool().applyEnchantment(stack, stack1));
    }

}