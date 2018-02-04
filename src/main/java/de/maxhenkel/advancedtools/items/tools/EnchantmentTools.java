package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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
        NBTTagList nbttaglist = new NBTTagList();

        for (EnchantmentData data : enchantments) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setShort("id", (short) Enchantment.getEnchantmentID(data.enchantment));
            nbttagcompound.setShort("lvl", (short) data.enchantmentLevel);
            nbttaglist.appendTag(nbttagcompound);
        }

        if (nbttaglist.hasNoTags()) {
            if (stack.hasTagCompound()) {
                stack.getTagCompound().removeTag("ench");
            }
        } else {
            stack.setTagInfo("ench", nbttaglist);
        }
    }
}
