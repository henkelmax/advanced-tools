package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantmentRemover;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.EnchantmentTools;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeRemoveEnchantment extends SpecialRecipe {

    private RecipeHelper.RecipeIngredient[] ingredients;

    public RecipeRemoveEnchantment(ResourceLocation id) {
        super(id);
        ingredients = new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemEnchantmentRemover.class, 1),
                new RecipeHelper.RecipeIngredient(AbstractTool.class, 1)
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
        ItemStack tool = null;
        ItemStack enchantment = null;
        int toolSlot = -1;
        int removerSlot = -1;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);

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
                if (!stack.isEmpty()) {
                    return null;
                }
            }
        }

        if (tool == null || enchantment == null) {
            return null;
        }

        NonNullList<ItemStack> list = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

        list.set(removerSlot, enchantment.copy());
        Enchantment en = ModItems.ENCHANTMENT_REMOVER.getEnchantment(enchantment);
        if (en != null) {
            ItemStack e = new ItemStack(ModItems.ENCHANTMENT);
            for (EnchantmentData data : EnchantmentTools.getEnchantments(tool)) {
                if (data.enchantment.equals(en)) {
                    ModItems.ENCHANTMENT.setEnchantment(e, en, data.level);
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
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        RecipeResult res = doCrafting(inv);
        if (res == null) {
            return NonNullList.create();
        }
        return res.remaining;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_EREMOVE_ENCHANTMENT;
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
