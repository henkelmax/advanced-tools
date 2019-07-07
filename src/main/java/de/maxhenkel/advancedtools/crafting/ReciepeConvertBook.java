package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.*;

public class ReciepeConvertBook extends SpecialRecipe {

    private RecipeHelper.RecipeIngredient[] ingredients;

    public ReciepeConvertBook(ResourceLocation id) {
        super(id);
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(EnchantedBookItem.class, 1)
        };
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return new ItemStack(Items.BOOK);
    }

    public NonNullList<ItemStack> doCrafting(CraftingInventory inv) {
        ItemStack book = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof EnchantedBookItem) {
                if (book != null) {
                    return null;
                }
                book = stack;
            } else {
                if (!StackUtils.isEmpty(stack)) {
                    return null;
                }
            }
        }

        if (book == null) {
            return null;
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(book);

        if (enchantments.size() > inv.getSizeInventory() && inv.getSizeInventory() < 9) {
            return null;
        }

        NonNullList<ItemStack> list = NonNullList.create();
        List<Map.Entry<Enchantment, Integer>> entries = new ArrayList<>(enchantments.entrySet());
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            if (i >= entries.size()) {
                list.add(ItemStack.EMPTY);
                continue;
            }
            Map.Entry<Enchantment, Integer> entry = entries.get(i);

            ItemStack stack = new ItemStack(ModItems.ENCHANTMENT);
            if (entry.getKey() != null && entry.getValue() != null) {
                ModItems.ENCHANTMENT.setEnchantment(stack, entry.getKey(), entry.getValue());
            }
            list.add(stack);
        }
        //TODO remaining enchs

        return list;
    }

    // /give @p enchanted_book 1 0 {StoredEnchantments:[{id:34,lvl:1},{id:33,lvl:1},{id:32,lvl:1},{id:22,lvl:1},{id:21,lvl:1}]}

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        return doCrafting(inv);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_CONVERT_BOOK;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.BOOK);
    }

}
