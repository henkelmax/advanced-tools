package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ReciepeEnchantTool implements IRecipe {

    private ResourceLocation resourceLocation;

    private RecipeHelper.RecipeIngredient[] ingredients;

    public ReciepeEnchantTool(){
        ingredients=new RecipeHelper.RecipeIngredient[]{
                new RecipeHelper.RecipeIngredient(ItemEnchantment.class, 1),
                new RecipeHelper.RecipeIngredient(AbstractTool.class, 1)
        };
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return RecipeHelper.matchesRecipe(inv, ingredients);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack tool = null;
        ItemStack enchantment = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof AbstractTool) {
                if (tool != null) {
                    return ItemStack.EMPTY;
                }
                tool = stack;
            } else if(stack.getItem() instanceof ItemEnchantment){
                if (enchantment != null) {
                    return ItemStack.EMPTY;
                }
                enchantment = stack;
            }else{
                if(!StackUtils.isEmpty(stack)){
                    return ItemStack.EMPTY;
                }
            }
        }

        if (tool == null||enchantment==null) {
            return ItemStack.EMPTY;
        }
        return ((AbstractTool) tool.getItem()).applyEnchantment(tool, enchantment);
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
}
