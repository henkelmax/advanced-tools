package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantmentRemover;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.EnchantmentTools;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ReciepeRemoveEnchantment implements IRecipe {

    private ResourceLocation resourceLocation;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return doCrafting(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        RecipeResult res = doCrafting(inv);

        if (res == null) {
            return ItemStack.EMPTY;
        }

        return res.result;
    }

    private RecipeResult doCrafting(InventoryCrafting inv) {
        ItemStack tool = null;
        ItemStack enchantment = null;
        int toolSlot = -1;
        int removerSlot = -1;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof AbstractTool) {
                if (tool != null) {
                    return null;
                }
                tool = stack;
                toolSlot = i;
            } else if (stack.getItem() instanceof ItemEnchantmentRemover) {
                if (enchantment != null) {
                    return null;
                }
                enchantment = stack;
                removerSlot = i;
            } else {
                if (!StackUtils.isEmpty(stack)) {
                    return null;
                }
            }
        }

        if (tool == null || enchantment == null) {
            return null;
        }

        NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        list.set(removerSlot, enchantment.copy());
        Enchantment en = ModItems.ENCHANTMENT_REMOVER.getEnchantment(enchantment);
        if (en != null) {
            ItemStack e = new ItemStack(ModItems.ENCHANTMENT);
            for (EnchantmentData data : EnchantmentTools.getEnchantments(tool)) {
                if (data.enchantment.equals(en)) {
                    ModItems.ENCHANTMENT.setEnchantment(e, en, data.enchantmentLevel);
                    list.set(toolSlot, e);
                    break;
                }
            }

        }

        ItemStack result = ((AbstractTool) tool.getItem()).removeEnchantment(tool, enchantment);

        if (result.equals(ItemStack.EMPTY)) {
            return null;
        }

        RecipeResult recipeResult = new RecipeResult();
        recipeResult.remaining = list;
        recipeResult.result = result;

        return recipeResult;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        RecipeResult res = doCrafting(inv);
        if (res == null) {
            return NonNullList.create();
        }
        return res.remaining;
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

    class RecipeResult {
        private NonNullList<ItemStack> remaining;
        private ItemStack result;
    }
}
