package de.maxhenkel.advancedtools.items.enchantments;

import de.maxhenkel.advancedtools.ModCreativeTabs;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.List;

public class ItemEnchantment extends Item {

    public ItemEnchantment() {
        setUnlocalizedName("enchantment");
        setRegistryName("enchantment");
        setCreativeTab(ModCreativeTabs.TAB_ADVANCED_TOOLS);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
       EnchantmentData data=getEnchantment(stack);
        if(data!=null){
            tooltip.add(data.enchantment.getTranslatedName(data.enchantmentLevel));
        }
    }

    public void setEnchantment(ItemStack stack, Enchantment enchantment, int level) {
        NBTTagCompound compound = StackUtils.getStackCompound(stack);

        NBTTagCompound ench = new NBTTagCompound();
        ench.setInteger("level", level);
        ench.setString("name", enchantment.getRegistryName().toString());

        compound.setTag("enchantment", ench);
    }

    public EnchantmentData getEnchantment(ItemStack stack) {
        NBTTagCompound compound = StackUtils.getStackCompound(stack);

        if (!compound.hasKey("enchantment")) {
            return null;
        }

        NBTTagCompound ench = compound.getCompoundTag("enchantment");

        if (!ench.hasKey("name")) {
            return null;
        }

        int level = 0;
        if (ench.hasKey("level")) {
            level = ench.getInteger("level");
        }

        String name = ench.getString("name");

        if (name == null) {
            return null;
        }

        String[] split = name.split(":");

        if (split.length < 2) {
            return null;
        }
        return new EnchantmentData(Enchantment.REGISTRY.getObject(new ResourceLocation(split[0], split[1])), level);
    }

}
