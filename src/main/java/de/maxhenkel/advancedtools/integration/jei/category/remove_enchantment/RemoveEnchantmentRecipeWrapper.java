package de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.EnchantmentRecipe;
import de.maxhenkel.advancedtools.items.tools.EnchantmentTools;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class RemoveEnchantmentRecipeWrapper implements IRecipeWrapper{

    private EnchantmentRemoveRecipe recipe;

    public RemoveEnchantmentRecipeWrapper(EnchantmentRemoveRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack enchantment=new ItemStack(ModItems.ENCHANTMENT_REMOVER);
        ModItems.ENCHANTMENT_REMOVER.setEnchantment(enchantment, recipe.getEnchantment());
        ItemStack tool=new ItemStack(recipe.getAbstractTool());
        StackUtils.setMaterial(tool, recipe.getMaterial());
        StackUtils.addEnchantment(tool, recipe.getEnchantment(), recipe.getEnchantment().getMaxLevel());
        ingredients.setInputs(ItemStack.class, ImmutableList.of(enchantment, tool));
        ItemStack tool2=recipe.getAbstractTool().removeEnchantment(tool, enchantment);
        ingredients.setOutput(ItemStack.class, tool2);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public EnchantmentRemoveRecipe getRecipe() {
        return recipe;
    }
}
