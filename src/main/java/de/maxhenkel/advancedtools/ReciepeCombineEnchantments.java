package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.EnchantmentTools;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ReciepeCombineEnchantments implements IRecipe {

    private ResourceLocation resourceLocation;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return !getCraftingResult(inv).equals(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack enchantment1 = null;
        ItemStack enchantment2 = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof ItemEnchantment) {
                if (enchantment1 != null && enchantment2 != null) {
                    return ItemStack.EMPTY;
                }
                if(enchantment1==null){
                    enchantment1 = stack;
                }else{
                    enchantment2 = stack;
                }
            }else{
                if(!StackUtils.isEmpty(stack)){
                    return ItemStack.EMPTY;
                }
            }
        }

        if (enchantment2 == null||enchantment1==null) {
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
