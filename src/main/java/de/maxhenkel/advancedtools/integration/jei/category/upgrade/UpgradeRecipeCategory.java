package de.maxhenkel.advancedtools.integration.jei.category.upgrade;
/*
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.integration.jei.JEIPlugin;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpgradeRecipeCategory implements IRecipeCategory<UpgradeRecipe> {

    private IGuiHelper helper;

    public UpgradeRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(new ResourceLocation(Main.MODID,
                "textures/gui/jei_crafting.png"), 0, 0, 116, 54);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(Items.DIAMOND));
    }

    @Override
    public void setIngredients(UpgradeRecipe recipe, IIngredients ingredients) {
        ItemStack tool = new ItemStack(recipe.getAbstractTool());
        StackUtils.setMaterial(tool, recipe.getMaterialOld());
        List<ItemStack> items = new ArrayList<>(Arrays.asList(recipe.getMaterialNew().getIngredient().getMatchingStacks()));
        items.add(tool);
        ingredients.setInputs(VanillaTypes.ITEM, items);

        ItemStack tool2 = new ItemStack(recipe.getAbstractTool());
        StackUtils.setMaterial(tool2, recipe.getMaterialNew());
        ingredients.setOutput(VanillaTypes.ITEM, tool2);
    }


    @Override
    public String getTitle() {
        return new TranslationTextComponent("jei.upgrading").getFormattedText();
    }

    @Override
    public ResourceLocation getUid() {
        return JEIPlugin.CATEGORY_UPGRADE;
    }

    @Override
    public Class<? extends UpgradeRecipe> getRecipeClass() {
        return UpgradeRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, UpgradeRecipe wrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();

        group.init(0, true, 0, 0);
        ItemStack stack = new ItemStack(wrapper.getAbstractTool());
        StackUtils.setMaterial(stack, wrapper.getMaterialOld());
        group.set(0, stack);

        group.init(1, true, 18, 0);

        group.init(2, true, 36, 0);
        group.init(3, true, 0, 18);
        group.init(4, true, 18, 18);
        group.init(5, true, 36, 18);
        group.init(6, true, 0, 36);
        group.init(7, true, 18, 36);
        group.init(8, true, 36, 36);

        for (int i = 1; i <= 8 && i <= wrapper.getAbstractTool().getRepairCost(stack); i++) {
            group.set(i, Arrays.asList(wrapper.getMaterialNew().getIngredient().getMatchingStacks()));
        }

        group.init(9, false, 94, 18);

        ItemStack stack1 = new ItemStack(wrapper.getAbstractTool());
        StackUtils.setMaterial(stack1, wrapper.getMaterialNew());
        group.set(9, stack1);
    }

}
*/