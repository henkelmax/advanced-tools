package de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment;

import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.EnchantmentRecipe;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class RemoveEnchantmentRecipeWrapperFactory implements IRecipeWrapperFactory<EnchantmentRemoveRecipe>{
    @Override
    public IRecipeWrapper getRecipeWrapper(EnchantmentRemoveRecipe recipe) {
        return new RemoveEnchantmentRecipeWrapper(recipe);
    }
}
