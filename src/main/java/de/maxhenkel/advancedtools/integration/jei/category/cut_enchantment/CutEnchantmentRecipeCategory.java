package de.maxhenkel.advancedtools.integration.jei.category.cut_enchantment;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.integration.jei.JEIPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class CutEnchantmentRecipeCategory implements IRecipeCategory<EnchantmentData> {

    private IGuiHelper helper;

    public CutEnchantmentRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID, "textures/gui/jei_converting.png"), 0, 0, 186, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(ModItems.ENCHANTMENT_REMOVER));
    }

    @Override
    public void setIngredients(EnchantmentData recipe, IIngredients ingredients) {
        ItemStack plier = new ItemStack(ModItems.PLIER);
        ItemStack enchantment = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantment, recipe.enchantment, recipe.level);
        ingredients.setInputs(VanillaTypes.ITEM, ImmutableList.of(enchantment, plier));
        ItemStack brokenEnchantment = new ItemStack(ModItems.BROKEN_ENCHANTMENT, 2);
        ModItems.ENCHANTMENT.setEnchantment(brokenEnchantment, recipe.enchantment, recipe.level);
        ingredients.setOutput(VanillaTypes.ITEM, brokenEnchantment);
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.enchantment_cutting").getString();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_ENCHANTMENT_CUTTING;
    }

    @Override
    public Class<? extends EnchantmentData> getRecipeClass() {
        return EnchantmentData.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, EnchantmentData wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 0);
        ItemStack plier = new ItemStack(ModItems.PLIER);
        group.set(0, plier);

        group.init(1, true, 18, 0);
        ItemStack enchantment = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(enchantment, wrapper.enchantment, wrapper.level);
        group.set(1, enchantment);

        group.init(2, true, 36, 0);
        group.init(3, true, 0, 18);
        group.init(4, true, 18, 18);
        group.init(5, true, 36, 18);
        group.init(6, true, 0, 36);
        group.init(7, true, 18, 36);
        group.init(8, true, 36, 36);

        group.init(9, false, 94, 18);
        ItemStack brokenEnchantment = new ItemStack(ModItems.BROKEN_ENCHANTMENT, 2);
        ModItems.ENCHANTMENT.setEnchantment(brokenEnchantment, wrapper.enchantment, wrapper.level);
        group.set(9, brokenEnchantment);

        group.init(10, true, 132, 0);
        group.init(11, true, 150, 0);
        group.init(12, true, 168, 0);
        group.init(13, true, 132, 18);
        group.init(14, true, 150, 18);
        group.init(15, true, 168, 18);
        group.init(16, true, 132, 36);
        group.init(17, true, 150, 36);
        group.init(18, true, 168, 36);

        ItemStack plierUsed = new ItemStack(ModItems.PLIER);
        plierUsed.setDamageValue(1);
        group.set(10, plierUsed);
    }

}