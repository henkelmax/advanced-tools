package de.maxhenkel.advancedtools.integration.jei.category.convert_enchantment;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.enchantment.EnchantmentData;

public class ConvertEnchantmentRecipeWrapperFactory implements IRecipeWrapperFactory<EnchantmentData>{
    @Override
    public IRecipeWrapper getRecipeWrapper(EnchantmentData recipe) {
        return new ConvertEnchantmentRecipeWrapper(recipe);
    }
}
