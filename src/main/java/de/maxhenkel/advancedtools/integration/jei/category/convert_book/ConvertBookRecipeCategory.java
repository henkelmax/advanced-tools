package de.maxhenkel.advancedtools.integration.jei.category.convert_book;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConvertBookRecipeCategory implements IRecipeCategory<ConvertBookRecipe> {

    private IGuiHelper helper;

    public ConvertBookRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID,
                "textures/gui/jei_converting.png"), 0, 0, 186, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(Items.ENCHANTED_BOOK));
    }

    @Override
    public void setIngredients(ConvertBookRecipe wrapper, IIngredients iIngredients) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        for (EnchantmentData data : wrapper.getEnchantments()) {
            EnchantedBookItem.addEnchantment(stack, data);
        }
        iIngredients.setInput(VanillaTypes.ITEM, stack);

        List<ItemStack> outputs = new ArrayList<>();
        Iterator<EnchantmentData> iterator = wrapper.getEnchantments().iterator();
        for (int i = 10; i <= 18 && iterator.hasNext(); i++) {
            EnchantmentData data = iterator.next();
            ItemStack ench = new ItemStack(ModItems.ENCHANTMENT);
            ModItems.ENCHANTMENT.setEnchantment(ench, data.enchantment, data.enchantmentLevel);
            outputs.add(ench);
        }
        iIngredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.converting").getFormattedText();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_BOOK_CONVERTING;
    }

    @Override
    public Class<? extends ConvertBookRecipe> getRecipeClass() {
        return ConvertBookRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, ConvertBookRecipe wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 0);
        group.init(1, true, 18, 0);
        group.init(2, true, 36, 0);
        group.init(3, true, 0, 18);
        group.init(4, true, 18, 18);
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        for (EnchantmentData data : wrapper.getEnchantments()) {
            EnchantedBookItem.addEnchantment(stack, data);
        }
        group.set(4, stack);

        group.init(5, true, 36, 18);
        group.init(6, true, 0, 36);
        group.init(7, true, 18, 36);
        group.init(8, true, 36, 36);

        group.init(9, false, 94, 18);
        group.set(9, new ItemStack(Items.BOOK));

        group.init(10, true, 132, 0);
        group.init(11, true, 150, 0);
        group.init(12, true, 168, 0);
        group.init(13, true, 132, 18);
        group.init(14, true, 150, 18);
        group.init(15, true, 168, 18);
        group.init(16, true, 132, 36);
        group.init(17, true, 150, 36);
        group.init(18, true, 168, 36);

        Iterator<EnchantmentData> iterator = wrapper.getEnchantments().iterator();

        for (int i = 10; i <= 18 && iterator.hasNext(); i++) {
            EnchantmentData data = iterator.next();
            ItemStack ench = new ItemStack(ModItems.ENCHANTMENT);
            ModItems.ENCHANTMENT.setEnchantment(ench, data.enchantment, data.enchantmentLevel);
            group.set(i, ench);
        }
    }


}