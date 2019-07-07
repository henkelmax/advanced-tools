package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.tools.EnchantmentTools;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ReciepeCombineEnchantments extends SpecialRecipe {

    private RecipeHelper.RecipeIngredient[] ingredients;

    public ReciepeCombineEnchantments(ResourceLocation id) {
        super(id);
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemEnchantment.class, 2)
        };
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack enchantment1 = null;
        ItemStack enchantment2 = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof ItemEnchantment) {
                if (enchantment1 != null && enchantment2 != null) {
                    return ItemStack.EMPTY;
                }
                if (enchantment1 == null) {
                    enchantment1 = stack;
                } else {
                    enchantment2 = stack;
                }
            } else {
                if (!StackUtils.isEmpty(stack)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (enchantment2 == null || enchantment1 == null) {
            return ItemStack.EMPTY;
        }
        return EnchantmentTools.combineEnchantments(enchantment1, enchantment2);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_COMBINE_ENCHANTMENTS;
    }

}
