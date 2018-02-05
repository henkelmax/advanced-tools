package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Iterator;

public class ModCreativeTabs {
	
	public static final CreativeTabs TAB_ADVANCED_TOOLS = new CreativeTabs("advanced_tools") {
		@Override
		public ItemStack getTabIconItem() {
			ItemStack stack=new ItemStack(ModItems.PICKAXE);
            StackUtils.setMaterial(stack, AdvancedToolMaterial.DIAMOND);
			return stack;
		}
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		    for(AdvancedToolMaterial material: AdvancedToolMaterial.getAll()){
                ItemStack stack=new ItemStack(ModItems.PICKAXE);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            for(AdvancedToolMaterial material: AdvancedToolMaterial.getAll()){
                ItemStack stack=new ItemStack(ModItems.AXE);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            for(AdvancedToolMaterial material: AdvancedToolMaterial.getAll()){
                ItemStack stack=new ItemStack(ModItems.SHOVEL);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            Iterator<Enchantment> iterator=Enchantment.REGISTRY.iterator();
            while(iterator.hasNext()){
                Enchantment enchantment=iterator.next();
                ItemStack stack=new ItemStack(ModItems.ENCHANTMENT);
                ModItems.ENCHANTMENT.setEnchantment(stack, enchantment, enchantment.getMaxLevel());
                list.add(stack);
            }

			super.displayAllRelevantItems(list);
		}
		
	};
	
}
