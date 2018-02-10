package de.maxhenkel.advancedtools.integration.jei.category.convert_enchantment;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.ModItems;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ConvertEnchantmentRecipeWrapper implements IRecipeWrapper{

    private EnchantmentData recipe;

    public ConvertEnchantmentRecipeWrapper(EnchantmentData recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack enchantment=new ItemStack(Items.ENCHANTED_BOOK);

        ingredients.setOutput(ItemStack.class, enchantment);

        ItemStack ench=new ItemStack(ModItems.ENCHANTMENT);
        ItemStack book=new ItemStack(Items.BOOK);
        ingredients.setInputs(ItemStack.class, ImmutableList.of(ench, book));
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

    public EnchantmentData getRecipe() {
        return recipe;
    }
}
