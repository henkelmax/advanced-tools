package de.maxhenkel.advancedtools.integration.jei.category.smithing;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.integration.jei.JEIPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class SmithungRecipeCategory implements IRecipeCategory<SmithingRecipe> {

    private IGuiHelper helper;

    public SmithungRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID, "textures/gui/jei_smithing.png"), 0, 0, 125, 18);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(Blocks.SMITHING_TABLE));
    }

    @Override
    public void setIngredients(SmithingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, ImmutableList.of(recipe.getInput(), recipe.getMaterial()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.smithing").getString();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_SMITHING;
    }

    @Override
    public Class<? extends SmithingRecipe> getRecipeClass() {
        return SmithingRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, SmithingRecipe wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 0);
        group.set(0, wrapper.getInput());

        group.init(1, true, 49, 0);
        group.set(1, wrapper.getMaterial());


        group.init(2, false, 107, 0);
        group.set(2, wrapper.getOutput());
    }

}