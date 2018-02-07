package de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.enchantment.Enchantment;

public class CombineEnchantmentRecipeWrapperFactory implements IRecipeWrapperFactory<Enchantment>{
    @Override
    public IRecipeWrapper getRecipeWrapper(Enchantment recipe) {
        return new CombineEnchantmentRecipeWrapper(recipe);
    }
}
