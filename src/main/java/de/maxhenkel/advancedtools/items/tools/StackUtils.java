package de.maxhenkel.advancedtools.items.tools;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StackUtils {

    //  /give @p advancedtools:pickaxe 1 255 {"tool_data": {"material": "iron"}}
    //  /give @p advancedtools:enchantment 1 0 {"enchantment": {"name": "minecraft:unbreaking", "level": 5}}

    public static void addEnchantment(ItemStack stack, Enchantment enchantment, int level) {

        stack.addEnchantment(enchantment, level);
    }

    public static void updateFlags(ItemStack stack){
        NBTTagCompound compound = getStackCompound(stack);
        if (!compound.hasKey("HideFlags")) {
            compound.setInteger("HideFlags", 0);
        }

        compound.setInteger("HideFlags", compound.getInteger("HideFlags") | 1 );
    }

    public static AdvancedToolMaterial getMaterial(ItemStack stack) {
        return AdvancedToolMaterial.byName(getStackToolData(stack).getString("material"));
    }

    public static ItemStack setMaterial(ItemStack stack, AdvancedToolMaterial material) {

        NBTTagCompound toolData = getStackToolData(stack);

        toolData.setString("material", material.getName());

        return stack;
    }

    public static boolean isEmpty(ItemStack stack) {
        if (stack == null) {
            return true;
        }

        if (stack.getItem().equals(Items.AIR) || stack.getItem().equals(Blocks.AIR)) {
            return true;
        }

        return false;
    }

    public static NBTTagCompound getStackCompound(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    public static NBTTagCompound getStackToolData(ItemStack stack) {
        NBTTagCompound compound = getStackCompound(stack);
        if (!compound.hasKey("tool_data")) {
            compound.setTag("tool_data", new NBTTagCompound());
        }

        return compound.getCompoundTag("tool_data");
    }

}
