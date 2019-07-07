package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BookItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ReciepeConvertEnchantment extends SpecialRecipe {

    private RecipeHelper.RecipeIngredient[] ingredients;

    public ReciepeConvertEnchantment(ResourceLocation id) {
        super(id);
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemEnchantment.class, 1),
                new RecipeHelper.RecipeIngredient(BookItem.class, 1)
        };
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return doCrafting(inv);
    }

    public ItemStack doCrafting(CraftingInventory inv) {
        ItemStack book = null;
        ItemStack enchantment = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof BookItem) {
                if (book != null) {
                    return null;
                }
                book = stack;
            } else if (stack.getItem() instanceof ItemEnchantment) {
                if (enchantment != null) {
                    return null;
                }
                enchantment = stack;
            } else {
                if (!StackUtils.isEmpty(stack)) {
                    return null;
                }
            }
        }

        if (book == null || enchantment == null) {
            return null;
        }

        ItemStack retStack=new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentData data=ModItems.ENCHANTMENT.getEnchantment(enchantment);
        if(data==null){
            return ItemStack.EMPTY;
        }
        EnchantedBookItem.addEnchantment(retStack, data);
        return retStack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_CONVERT_ENCHANTMENT;
    }

}
