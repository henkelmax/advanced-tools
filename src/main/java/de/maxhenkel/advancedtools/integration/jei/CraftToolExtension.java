package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.crafting.RecipeToolMaterial;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICustomCraftingCategoryExtension;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Size2i;

public class CraftToolExtension<T extends RecipeToolMaterial> implements ICustomCraftingCategoryExtension {
    protected final T recipe;
    protected CraftingGridHelper helper;

    public CraftToolExtension(T recipe) {
        this.recipe = recipe;
        helper = new CraftingGridHelper();
    }

    @Override
    public void setIngredients(IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
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

        guiItemStacks.set(0, recipe.getResultItem());

        helper.setInputs(recipeLayout.getItemStacks(), iIngredients.getInputs(VanillaTypes.ITEM), recipe.getRecipeWidth(), recipe.getRecipeHeight());
    }
}