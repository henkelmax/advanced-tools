package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Set;

public class AdvancedShovel extends AbstractTool {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);
    private static final ImmutableList<Enchantment> VALID_ENCHANTMENTS = ImmutableList.of(Enchantments.EFFICIENCY, Enchantments.FORTUNE, Enchantments.SILK_TOUCH, Enchantments.UNBREAKING, Enchantments.MENDING);

    public AdvancedShovel() {
        setUnlocalizedName("shovel");
        setRegistryName(new ResourceLocation(Main.MODID, "shovel"));
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of(AdvancedToolMaterial.SHOVEL);
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
            return -3F;
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
        return AdvancedToolMaterial.SHOVEL;
    }

    @Override
    public ImmutableList<Enchantment> getValidEnchantments(ItemStack stack) {
        return VALID_ENCHANTMENTS;
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
            return ChatFormatting.WHITE + mat.getLocalizedName() + " " + new TextComponentTranslation("tool.shovel.name").getUnformattedText();
        }
        return ChatFormatting.WHITE + new TextComponentTranslation("tool.shovel.name").getFormattedText();
    }

    @Override
    public int getRepairCost(ItemStack stack) {
        return 1;
    }
}
