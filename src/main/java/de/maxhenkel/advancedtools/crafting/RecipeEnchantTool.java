package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeEnchantTool extends SpecialRecipe {

    private RecipeHelper.RecipeIngredient[] ingredients;

    public RecipeEnchantTool(ResourceLocation id) {
        super(id);
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemEnchantment.class, 1),
                new RecipeHelper.RecipeIngredient(AbstractTool.class, 1)
        };
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        ItemStack tool = null;
        ItemStack enchantment = null;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.getItem() instanceof AbstractTool) {
                if (tool != null) {
                    return ItemStack.EMPTY;
                }
                tool = stack;
            } else if (stack.getItem() instanceof ItemEnchantment) {
                if (enchantment != null) {
                    return ItemStack.EMPTY;
                }
                enchantment = stack;
            } else {
                if (!stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (tool == null || enchantment == null) {
            return ItemStack.EMPTY;
        }
        return ((AbstractTool) tool.getItem()).applyEnchantment(tool, enchantment);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_ENCHANT_TOOL;
    }

}
