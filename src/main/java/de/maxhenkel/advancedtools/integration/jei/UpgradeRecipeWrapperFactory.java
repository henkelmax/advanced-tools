package de.maxhenkel.advancedtools.integration.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class UpgradeRecipeWrapperFactory implements IRecipeWrapperFactory<UpgradeRecipe>{
    @Override
    public IRecipeWrapper getRecipeWrapper(UpgradeRecipe recipe) {
        return new UpgradeRecipeWrapper(recipe);
    }
}
