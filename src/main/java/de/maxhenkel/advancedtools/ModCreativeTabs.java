package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Iterator;

public class ModCreativeTabs {

    public static final ItemGroup TAB_ADVANCED_TOOLS = new ItemGroup("advanced_tools") {
        @Override
        public ItemStack createIcon() {
            ItemStack stack = new ItemStack(ModItems.PICKAXE);
            StackUtils.setMaterial(stack, AdvancedToolMaterial.DIAMOND);
            return stack;
        }

        @Override
        public void fill(NonNullList<ItemStack> list) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                ItemStack stack = new ItemStack(ModItems.PICKAXE);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                ItemStack stack = new ItemStack(ModItems.AXE);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                ItemStack stack = new ItemStack(ModItems.SHOVEL);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                ItemStack stack = new ItemStack(ModItems.SWORD);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                ItemStack stack = new ItemStack(ModItems.HOE);
                StackUtils.setMaterial(stack, material);
                list.add(stack);
            }

            Iterator<Enchantment> iterator = ForgeRegistries.ENCHANTMENTS.iterator();
            while (iterator.hasNext()) {
                Enchantment enchantment = iterator.next();
                ItemStack stack = new ItemStack(ModItems.ENCHANTMENT);
                ModItems.ENCHANTMENT.setEnchantment(stack, enchantment, enchantment.getMaxLevel());
                list.add(stack);
            }
            super.fill(list);
        }

    };

}
