package de.maxhenkel.advancedtools.integration.jei.category.convert_book;

import com.google.common.collect.ImmutableList;
import net.minecraft.enchantment.EnchantmentData;

public class ConvertBookRecipe {

    private EnchantmentData[] enchantments;

    public ConvertBookRecipe(EnchantmentData[] enchantments) {
        this.enchantments = enchantments;
    }

    public ImmutableList<EnchantmentData> getEnchantments() {
        return ImmutableList.copyOf(enchantments);
    }
}