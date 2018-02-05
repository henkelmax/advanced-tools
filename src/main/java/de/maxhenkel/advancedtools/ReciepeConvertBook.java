package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.*;

public class ReciepeConvertBook implements IRecipe {

    private ResourceLocation resourceLocation;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return doCrafting(inv)!=null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return new ItemStack(Items.BOOK);
    }

    public NonNullList<ItemStack> doCrafting(InventoryCrafting inv){
        ItemStack book = null;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof ItemEnchantedBook) {
                if (book != null) {
                    return null;
                }
                book = stack;
            }else{
                if(!StackUtils.isEmpty(stack)){
                    return null;
                }
            }
        }

        if (book == null) {
            return null;
        }

        Map<Enchantment, Integer> enchantments=EnchantmentHelper.getEnchantments(book);

        if(enchantments.size()>inv.getSizeInventory()&&inv.getSizeInventory()<9){
            return null;
        }

        NonNullList<ItemStack> list=NonNullList.create();
        List<Map.Entry<Enchantment, Integer>> entries= new ArrayList<>(enchantments.entrySet());
        for(int i=0; i<inv.getSizeInventory(); i++){
            if(i>=entries.size()){
                list.add(ItemStack.EMPTY);
                continue;
            }
            Map.Entry<Enchantment, Integer> entry=entries.get(i);

            ItemStack stack=new ItemStack(ModItems.ENCHANTMENT);
            if(entry.getKey()!=null&&entry.getValue()!=null){
                ModItems.ENCHANTMENT.setEnchantment(stack, entry.getKey(), entry.getValue());
            }
            list.add(stack);
        }
        //TODO remaining enchs

        return list;
    }

    // /give @p enchanted_book 1 0 {StoredEnchantments:[{id:34,lvl:1},{id:33,lvl:1},{id:32,lvl:1},{id:22,lvl:1},{id:21,lvl:1}]}

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return doCrafting(inv);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(Items.BOOK);
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
