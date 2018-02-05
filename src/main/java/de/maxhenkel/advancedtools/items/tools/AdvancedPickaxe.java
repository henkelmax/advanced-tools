package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.advancedtools.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.CommandEnchant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdvancedPickaxe extends AbstractTool {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);

    public AdvancedPickaxe() {
        setRegistryName("pickaxe");
        setUnlocalizedName("pickaxe");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        //DEFAULT
        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat == null) {
            StackUtils.setMaterial(stack, de.maxhenkel.advancedtools.items.tools.ToolMaterial.DIAMOND);
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (!enchantments.isEmpty()) {
            tooltip.add(new TextComponentTranslation("tooltips.enchantments").getFormattedText());
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                tooltip.add("  - " + entry.getKey().getTranslatedName(entry.getValue()));
            }
        }
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("pickaxe");
    }

    @Override
    public float getAttackDamage(ItemStack stack) {
        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getAttackModifier();
        }
        return 0F;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getAttackSpeedModifier();
        }
        return 0F;
    }

    @Override
    public float getEfficiency(ItemStack stack) {
        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getEfficiency();
        }
        return 1F;
    }

    @Override
    public Set<Block> getEffectiveBlocks(ItemStack stack) {
        return EFFECTIVE_ON;
    }

    @Override
    public int getHarvestLevel(ItemStack stack) {
        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getHarvestLevel();
        }
        return 0;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getMaxDamage();
        }
        return 0;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return ChatFormatting.WHITE + mat.getLocalizedName() + " " + new TextComponentTranslation("tool.pickaxe.name").getUnformattedText();
        }
        return ChatFormatting.WHITE + new TextComponentTranslation("tool.pickaxe.name").getFormattedText();
    }

    @Override
    public int getRepairCost(ItemStack stack) {
        return 3;
    }

    @Override
    public ItemStack applyEnchantment(ItemStack tool, ItemStack enchantment) {
        ItemStack newTool = tool.copy();
        EnchantmentData data = ModItems.ENCHANTMENT.getEnchantment(enchantment);
        if (data != null) {
            if (data.enchantment == Enchantments.EFFICIENCY
                    || data.enchantment == Enchantments.FORTUNE
                    || data.enchantment == Enchantments.SILK_TOUCH
                    || data.enchantment == Enchantments.UNBREAKING) {

                List<EnchantmentData> enchantments = EnchantmentTools.getEnchantments(newTool);
                EnchantmentHelper.removeIncompatible(enchantments, data);
                enchantments.add(data);
                //newTool.addEnchantment(data.enchantment, data.enchantmentLevel);
                EnchantmentTools.setEnchantments(enchantments, newTool);
                return newTool;
            }
        }
        return ItemStack.EMPTY;
    }

}
