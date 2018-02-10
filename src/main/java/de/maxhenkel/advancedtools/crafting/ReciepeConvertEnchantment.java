package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReciepeConvertEnchantment implements IRecipe {

    private ResourceLocation resourceLocation;

    private RecipeHelper.RecipeIngredient[] ingredients;

    public ReciepeConvertEnchantment() {
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemEnchantment.class, 1),
                new RecipeHelper.RecipeIngredient(ItemBook.class, 1)
        };
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return doCrafting(inv);
    }

    public ItemStack doCrafting(InventoryCrafting inv) {
        ItemStack book = null;
        ItemStack enchantment = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof ItemBook) {
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
        ItemEnchantedBook.addEnchantment(retStack, data);
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
    public IRecipe setRegistryName(ResourceLocation name) {
        this.resourceLocation = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return resourceLocation;
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }
}
