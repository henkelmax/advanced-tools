package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.enchantments.ItemPliers;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeCutEnchantment extends SpecialRecipe {

    private RecipeHelper.RecipeIngredient[] ingredients;

    public RecipeCutEnchantment(ResourceLocation id) {
        super(id);
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemPliers.class, 1),
                new RecipeHelper.RecipeIngredient(ItemEnchantment.class, 1)
        };
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        RecipeResult res = doCrafting(inv);

        if (res == null) {
            return ItemStack.EMPTY;
        }

        return res.result;
    }

    private RecipeResult doCrafting(CraftingInventory inv) {
        ItemStack plier = null;
        ItemStack enchantment = null;
        int plierSlot = -1;
        int enchantmentSlot = -1;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

            if (stack.getItem() == ModItems.PLIER) {
                if (plier != null) {
                    return null;
                }
                plier = stack;
                plierSlot = i;
            } else if (stack.getItem() instanceof ItemEnchantment) {
                if (enchantment != null) {
                    return null;
                }
                enchantment = stack;
                enchantmentSlot = i;
            } else {
                if (!stack.isEmpty()) {
                    return null;
                }
            }
        }

        if (plier == null || enchantment == null) {
            return null;
        }

        NonNullList<ItemStack> list = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        if (plier.getMaxDamage() - plier.getDamageValue() > 1) {
            ItemStack p = plier.copy();
            p.setDamageValue(p.getDamageValue() + 1);
            list.set(plierSlot, p);
        }

        EnchantmentData en = ModItems.ENCHANTMENT.getEnchantment(enchantment);
        ItemStack result = new ItemStack(ModItems.BROKEN_ENCHANTMENT, 2);
        if (en != null) {
            ModItems.ENCHANTMENT.setEnchantment(result, en.enchantment, en.level);
        }

        RecipeResult recipeResult = new RecipeResult();
        recipeResult.remaining = list;
        recipeResult.result = result;

        return recipeResult;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        RecipeResult res = doCrafting(inv);
        if (res == null) {
            return NonNullList.create();
        }
        return res.remaining;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_CUT_ENCHANTMENT;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }


    class RecipeResult {
        private NonNullList<ItemStack> remaining;
        private ItemStack result;
    }
}
