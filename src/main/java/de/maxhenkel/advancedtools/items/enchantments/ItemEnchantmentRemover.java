package de.maxhenkel.advancedtools.items.enchantments;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModCreativeTabs;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class ItemEnchantmentRemover extends Item {

    public ItemEnchantmentRemover() {
        setUnlocalizedName("enchantment_remover");
        setRegistryName(new ResourceLocation(Main.MODID, "enchantment_remover"));
        setCreativeTab(ModCreativeTabs.TAB_ADVANCED_TOOLS);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("tooltip.enchantment_remover").getFormattedText());
        Enchantment data = getEnchantment(stack);
        if (data != null) {
            tooltip.add(new TextComponentTranslation(data.getName()).getFormattedText());
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        Enchantment enchantment = getEnchantment(stack);

        Iterator<Enchantment> iterator = Enchantment.REGISTRY.iterator();
        while (iterator.hasNext()) {
            Enchantment e = iterator.next();

            if (enchantment == null) {
                applyEnchantment(playerIn, stack, e);
                break;
            }

            if (e.equals(enchantment)) {
                if (!iterator.hasNext()) {
                    iterator = Enchantment.REGISTRY.iterator();
                    if (!iterator.hasNext()) {
                        break;
                    }
                }
                e=iterator.next();

                applyEnchantment(playerIn, stack, e);
                break;
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    private void applyEnchantment(EntityPlayer player, ItemStack stack, Enchantment e) {
        setEnchantment(stack, e);
        player.sendStatusMessage(new TextComponentTranslation("statusbar.enchantment_remover", new TextComponentTranslation(e.getName()).getUnformattedText()), true);
    }

    public void setEnchantment(ItemStack stack, Enchantment enchantment) {
        NBTTagCompound compound = StackUtils.getStackCompound(stack);
        compound.setString("enchantment", enchantment.getRegistryName().toString());
    }

    public Enchantment getEnchantment(ItemStack stack) {
        NBTTagCompound compound = StackUtils.getStackCompound(stack);

        if (!compound.hasKey("enchantment")) {
            return null;
        }

        String name = compound.getString("enchantment");

        if (name == null) {
            return null;
        }

        String[] split = name.split(":");

        if (split.length < 2) {
            return null;
        }
        return Enchantment.REGISTRY.getObject(new ResourceLocation(split[0], split[1]));
    }

}
