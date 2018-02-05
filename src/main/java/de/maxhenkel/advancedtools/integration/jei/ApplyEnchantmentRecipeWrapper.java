package de.maxhenkel.advancedtools.integration.jei;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import java.util.Collections;
import java.util.List;

public class ApplyEnchantmentRecipeWrapper implements IRecipeWrapper{

    private EnchantmentRecipe recipe;

    public ApplyEnchantmentRecipeWrapper(EnchantmentRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack enchantment=new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantment, recipe.getEnchantment(), recipe.getEnchantment().getMaxLevel());
        ItemStack tool=new ItemStack(recipe.getAbstractTool());
        StackUtils.setMaterial(tool, recipe.getMaterial());
        ingredients.setInputs(ItemStack.class, ImmutableList.of(enchantment, tool));
        ItemStack tool2=recipe.getAbstractTool().applyEnchantment(tool, enchantment);
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

    public EnchantmentRecipe getRecipe() {
        return recipe;
    }
}
