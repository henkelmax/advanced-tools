package de.maxhenkel.advancedtools.integration.jei;
/*
import de.maxhenkel.advancedtools.crafting.RecipeConvertTool;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICustomCraftingCategoryExtension;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Size2i;

import java.util.Arrays;

public class ConvertToolExtension<T extends RecipeConvertTool> implements ICustomCraftingCategoryExtension {
    protected final T recipe;

    public ConvertToolExtension(T recipe) {
        this.recipe = recipe;
    }

    @Override
    public void setIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(recipe.getInputTool().getMatchingStacks()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getId();
    }

    @Override
    public Size2i getSize() {
        return new Size2i(recipe.getRecipeWidth(), recipe.getRecipeHeight());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.set(0, recipe.getRecipeOutput());
        guiItemStacks.set(1, Arrays.asList(recipe.getInputTool().getMatchingStacks()));
    }
}*/