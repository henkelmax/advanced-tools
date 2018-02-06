package de.maxhenkel.advancedtools.integration.jei.category.convert_book;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class ConvertBookRecipeWrapperFactory implements IRecipeWrapperFactory<ConvertBookRecipe>{
    @Override
    public IRecipeWrapper getRecipeWrapper(ConvertBookRecipe recipe) {
        return new ConvertBookRecipeWrapper(recipe);
    }
}
