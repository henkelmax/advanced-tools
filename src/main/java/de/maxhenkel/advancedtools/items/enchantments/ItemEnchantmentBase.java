package de.maxhenkel.advancedtools.items.enchantments;

import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemEnchantmentBase extends Item {

    public ItemEnchantmentBase() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        EnchantmentData data = getEnchantment(stack);
        if (data != null) {
            tooltip.add(data.enchantment.getFullname(data.level));
        }

        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    public void setEnchantment(ItemStack stack, Enchantment enchantment, int level) {
        CompoundNBT compound = StackUtils.getStackCompound(stack);

        CompoundNBT ench = new CompoundNBT();
        ench.putInt("level", level);
        ench.putString("name", enchantment.getRegistryName().toString());

        compound.put("enchantment", ench);
    }

    public EnchantmentData getEnchantment(ItemStack stack) {
        CompoundNBT compound = StackUtils.getStackCompound(stack);

        if (!compound.contains("enchantment")) {
            return null;
        }

        CompoundNBT ench = compound.getCompound("enchantment");

        if (!ench.contains("name")) {
            return null;
        }

        int level = 0;
        if (ench.contains("level")) {
            level = ench.getInt("level");
        }

        String name = ench.getString("name");

        String[] split = name.split(":");

        if (split.length < 2) {
            return null;
        }
        return new EnchantmentData(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(split[0], split[1])), level);
    }

}
