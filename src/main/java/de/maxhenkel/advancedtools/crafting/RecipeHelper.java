package de.maxhenkel.advancedtools.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeHelper {

    public static boolean matchesRecipe(CraftingInventory inv, RecipeIngredient... ingredients) {
        int[] counts = new int[ingredients.length];
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            boolean flag = false;
            for (int j = 0; j < counts.length; j++) {
                RecipeIngredient ingredient = ingredients[j];
                if (ingredient.itemClass.isInstance(stack.getItem())) {
                    counts[j]++;
                    flag = true;
                    break;
                }
            }
            if (!flag && !stack.isEmpty()) {
                return false;
            }
        }

        for (int j = 0; j < counts.length; j++) {
            RecipeIngredient ingredient = ingredients[j];
            int amount = counts[j];

            if (amount <= 0) {
                return false;
            } else if (ingredient.amount < 0) {
                continue;
            } else if (amount == ingredient.amount) {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    public static class RecipeIngredient {
        private Class<? extends Item> itemClass;
        private int amount;

        public RecipeIngredient(Class<? extends Item> itemClass) {
            this.itemClass = itemClass;
            this.amount = -1;
        }

        public RecipeIngredient(Class<? extends Item> itemClass, int amount) {
            this.itemClass = itemClass;
            this.amount = amount;
        }
    }

}
