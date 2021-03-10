package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.tools.EnchantmentTools;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeSplitEnchantments extends SpecialRecipe {

    private RecipeHelper.RecipeIngredient[] ingredients;

    public RecipeSplitEnchantments(ResourceLocation id) {
        super(id);
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemEnchantment.class, 1)
        };
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        ItemStack[] result = getResult(inv);
        if (result == null) {
            return ItemStack.EMPTY;
        }

        return result[0];
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        int index = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (!inv.getItem(i).isEmpty()) {
                index = i;
                break;
            }
        }
        NonNullList<ItemStack> items = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        ItemStack[] result = getResult(inv);
        if (result == null) {
            return items;
        }
        items.set(index, result[1]);
        return items;
    }

    private ItemStack[] getResult(CraftingInventory inv) {
        ItemStack enchantment = null;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.getItem() instanceof ItemEnchantment) {
                if (enchantment != null) {
                    return null;
                }
                enchantment = stack;
            } else {
                if (!stack.isEmpty()) {
                    return null;
                }
            }
        }

        if (enchantment == null) {
            return null;
        }
        return EnchantmentTools.splitEnchantments(enchantment);
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
        return Main.CRAFTING_SPLIT_ENCHANTMENTS;
    }

}
