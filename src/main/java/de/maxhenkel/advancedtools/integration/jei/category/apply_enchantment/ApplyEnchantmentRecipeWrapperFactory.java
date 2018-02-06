package de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class ApplyEnchantmentRecipeWrapperFactory implements IRecipeWrapperFactory<EnchantmentRecipe>{
    @Override
    public IRecipeWrapper getRecipeWrapper(EnchantmentRecipe recipe) {
        return new ApplyEnchantmentRecipeWrapper(recipe);
    }
}