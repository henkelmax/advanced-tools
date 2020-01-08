package de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.integration.jei.JEIPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class CombineEnchantmentRecipeCategory implements IRecipeCategory<EnchantmentData> {

    private IGuiHelper helper;

    public CombineEnchantmentRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID,
                "textures/gui/jei_crafting.png"), 0, 0, 116, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(ModItems.ENCHANTMENT));
    }

    @Override
    public void setIngredients(EnchantmentData recipe, IIngredients ingredients) {
        ItemStack enchantment = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantment, recipe.enchantment, recipe.enchantmentLevel - 1);
        ingredients.setInput(VanillaTypes.ITEM, enchantment);

        ItemStack enchantmentOut = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantmentOut, recipe.enchantment, recipe.enchantmentLevel);
        ingredients.setOutput(VanillaTypes.ITEM, enchantmentOut);
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.enchantment_combining").getFormattedText();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_ENCHANTMENT_COMBINING;
    }

    @Override
    public Class<? extends EnchantmentData> getRecipeClass() {
        return EnchantmentData.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, EnchantmentData wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 0);
        ItemStack stack = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(stack, wrapper.enchantment, wrapper.enchantmentLevel - 1);
        group.set(0, stack);

        group.init(1, true, 18, 0);
        group.set(1, stack.copy());

        group.init(2, true, 36, 0);
        group.init(3, true, 0, 18);
        group.init(4, true, 18, 18);
        group.init(5, true, 36, 18);
        group.init(6, true, 0, 36);
        group.init(7, true, 18, 36);
        group.init(8, true, 36, 36);

        group.init(9, false, 94, 18);
        ItemStack stack1 = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(stack1, wrapper.enchantment, wrapper.enchantmentLevel);
        group.set(9, stack1);
    }

}