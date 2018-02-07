package de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class CombineEnchantmentRecipeWrapper implements IRecipeWrapper{

    private Enchantment recipe;

    public CombineEnchantmentRecipeWrapper(Enchantment recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack enchantment=new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantment, recipe, recipe.getMaxLevel()-1);

        ItemStack enchantmentOut=new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantmentOut, recipe, recipe.getMaxLevel());
        ingredients.setOutput(ItemStack.class, enchantmentOut);
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

    public Enchantment getRecipe() {
        return recipe;
    }
}
