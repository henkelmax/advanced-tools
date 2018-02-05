package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdvancedAxe extends AbstractTool {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE);

    public AdvancedAxe() {
        setUnlocalizedName("axe");
        setRegistryName(new ResourceLocation(Main.MODID, "axe"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

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
        return ImmutableSet.of(AdvancedToolMaterial.AXE);
    }

    @Override
    public float getAttackDamage(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getAttackModifier();
        }
        return 0F;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getAttackSpeedModifier();
        }
        return 0F;
    }

    @Override
    public float getEfficiency(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
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
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getHarvestLevel();
        }
        return 0;
    }

    @Override
    public String getPrimaryToolType() {
        return AdvancedToolMaterial.AXE;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getMaxDamage();
        }
        return 0;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return ChatFormatting.WHITE + mat.getLocalizedName() + " " + new TextComponentTranslation("tool.axe.name").getUnformattedText();
        }
        return ChatFormatting.WHITE + new TextComponentTranslation("tool.axe.name").getFormattedText();
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
                    || data.enchantment == Enchantments.UNBREAKING
                    || data.enchantment == Enchantments.SHARPNESS
                    || data.enchantment == Enchantments.SMITE
                    || data.enchantment == Enchantments.BANE_OF_ARTHROPODS) {

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
