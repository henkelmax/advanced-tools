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

public class UpgradeRecipeWrapper implements IRecipeWrapper{

    private UpgradeRecipe recipe;

    public UpgradeRecipeWrapper(UpgradeRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack tool=new ItemStack(recipe.getAbstractTool());
        StackUtils.setMaterial(tool, recipe.getMaterialOld());
        ingredients.setInputs(ItemStack.class, ImmutableList.of(tool, recipe.getMaterialNew().getMatcher().getAll()));

        ItemStack tool2=new ItemStack(recipe.getAbstractTool());
        StackUtils.setMaterial(tool2, recipe.getMaterialNew());
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

    public UpgradeRecipe getRecipe() {
        return recipe;
    }
}
