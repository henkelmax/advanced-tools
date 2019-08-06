package de.maxhenkel.advancedtools.items.enchantments;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModCreativeTabs;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class ItemEnchantmentRemover extends Item {

    public ItemEnchantmentRemover() {
        super(new Item.Properties().group(ModCreativeTabs.TAB_ADVANCED_TOOLS));
        setRegistryName(new ResourceLocation(Main.MODID, "enchantment_remover"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.enchantment_remover").applyTextStyle(TextFormatting.GRAY));
        Enchantment data = getEnchantment(stack);
        if (data != null) {
            tooltip.add(new TranslationTextComponent(data.getName()).applyTextStyle(TextFormatting.GRAY));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        Enchantment enchantment = getEnchantment(stack);

        Iterator<Enchantment> iterator = ForgeRegistries.ENCHANTMENTS.iterator();
        while (iterator.hasNext()) {
            Enchantment e = iterator.next();

            if (enchantment == null) {
                applyEnchantment(playerIn, stack, e);
                break;
            }

            if (e.equals(enchantment)) {
                if (!iterator.hasNext()) {
                    iterator = ForgeRegistries.ENCHANTMENTS.iterator();
                    if (!iterator.hasNext()) {
                        break;
                    }
                }
                e = iterator.next();

                applyEnchantment(playerIn, stack, e);
                break;
            }
        }

        return ActionResult.newResult(ActionResultType.SUCCESS, stack);
    }

    private void applyEnchantment(PlayerEntity player, ItemStack stack, Enchantment e) {
        setEnchantment(stack, e);
        player.sendStatusMessage(new TranslationTextComponent("statusbar.enchantment_remover", new TranslationTextComponent(e.getName())), true);
    }

    public void setEnchantment(ItemStack stack, Enchantment enchantment) {
        CompoundNBT compound = StackUtils.getStackCompound(stack);
        compound.putString("enchantment", enchantment.getRegistryName().toString());
    }

    public Enchantment getEnchantment(ItemStack stack) {
        CompoundNBT compound = StackUtils.getStackCompound(stack);

        if (!compound.contains("enchantment")) {
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
        return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(split[0], split[1]));
    }

}
