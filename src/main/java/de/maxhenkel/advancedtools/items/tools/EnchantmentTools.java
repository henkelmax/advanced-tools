package de.maxhenkel.advancedtools.items.tools;

import de.maxhenkel.advancedtools.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchantmentTools {

    public static List<EnchantmentData> getEnchantments(ItemStack stack) {
        List<EnchantmentData> data = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(stack).entrySet()) {
            data.add(new EnchantmentData(entry.getKey(), entry.getValue()));
        }

        return data;
    }

    public static void setEnchantments(List<EnchantmentData> enchantments, ItemStack stack) {
        ListNBT nbttaglist = new ListNBT();

        for (EnchantmentData data : enchantments) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putString("id", data.enchantment.getRegistryName().toString());
            nbttagcompound.putShort("lvl", (short) data.enchantmentLevel);
            nbttaglist.add(nbttagcompound);
        }

        if (nbttaglist.isEmpty()) {
            if (stack.hasTag()) {
                stack.getTag().remove("Enchantments");
            }
        } else {
            stack.setTagInfo("Enchantments", nbttaglist);
        }
    }

    public static ItemStack combineEnchantments(ItemStack enchantment1, ItemStack enchantment2) {
        EnchantmentData enchantmentData1 = ModItems.ENCHANTMENT.getEnchantment(enchantment1);
        EnchantmentData enchantmentData2 = ModItems.ENCHANTMENT.getEnchantment(enchantment2);

        if (!enchantmentData1.enchantment.equals(enchantmentData2.enchantment)) {
            return ItemStack.EMPTY;
        }

        if (enchantmentData1.enchantmentLevel != enchantmentData2.enchantmentLevel) {
            return ItemStack.EMPTY;
        }

        if (enchantmentData1.enchantmentLevel >= enchantmentData1.enchantment.getMaxLevel()) {
            return ItemStack.EMPTY;
        }

        ItemStack combined = new ItemStack(ModItems.ENCHANTMENT);
        ModItems.ENCHANTMENT.setEnchantment(combined, enchantmentData1.enchantment, enchantmentData1.enchantmentLevel + 1);
        return combined;
    }
}
