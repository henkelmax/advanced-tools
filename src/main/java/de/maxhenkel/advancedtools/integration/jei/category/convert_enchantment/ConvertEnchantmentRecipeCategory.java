package de.maxhenkel.advancedtools.integration.jei.category.convert_enchantment;

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
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ConvertEnchantmentRecipeCategory implements IRecipeCategory<EnchantmentData> {

    private IGuiHelper helper;

    public ConvertEnchantmentRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID,
                "textures/gui/jei_crafting.png"), 0, 0, 116, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(ModItems.ENCHANTMENT));
    }

    @Override
    public void setIngredients(EnchantmentData wrapper, IIngredients ingredients) {
        ItemStack enchantment = new ItemStack(Items.ENCHANTED_BOOK);

        ingredients.setOutput(VanillaTypes.ITEM, enchantment);

        ItemStack ench = new ItemStack(ModItems.ENCHANTMENT);
        ItemStack book = new ItemStack(Items.BOOK);
        ingredients.setInputs(VanillaTypes.ITEM, ImmutableList.of(ench, book));
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.enchantment_converting").getFormattedText();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_ENCHANTMENT_CONVERTING;
    }

    @Override
    public Class<? extends EnchantmentData> getRecipeClass() {
        return EnchantmentData.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, EnchantmentData wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 0);
        ItemStack stack = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(stack, wrapper.enchantment, wrapper.enchantmentLevel);
        group.set(0, stack);

        group.init(1, true, 18, 0);
        group.set(1, new ItemStack(Items.BOOK));

        group.init(2, true, 36, 0);
        group.init(3, true, 0, 18);
        group.init(4, true, 18, 18);
        group.init(5, true, 36, 18);
        group.init(6, true, 0, 36);
        group.init(7, true, 18, 36);
        group.init(8, true, 36, 36);

        group.init(9, false, 94, 18);
        ItemStack stack1 = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(stack1, wrapper);
        group.set(9, stack1);
    }


}
