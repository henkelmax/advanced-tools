package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.maxhenkel.advancedtools.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class AdvancedSword extends AbstractTool {

    private static final Set<Material> EFFECTIVE_ON = Sets.newHashSet(Material.WEB, Material.VEGETABLE, Material.BAMBOO);

    public AdvancedSword() {
        setRegistryName(new ResourceLocation(Main.MODID, "sword"));
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.get("sword"));
    }

    @Override
    public boolean countBreakStats(ItemStack stack) {
        return false;
    }

    @Override
    public float getAttackDamage(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getAttackModifier() + 3;
        }
        return 0F;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return -2F;
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
    public Set<Material> getEffectiveMaterials(ItemStack stack) {
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
        return AdvancedToolMaterial.SWORD;
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
    public int getRepairCost(ItemStack stack, AdvancedToolMaterial material) {
        return material == AdvancedToolMaterial.NETHERITE ? 1 : 2;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        return state.getBlock() == Blocks.COBWEB;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.category.equals(EnchantmentType.DIGGER)) {
            return false;
        }
        return enchantment.category.equals(EnchantmentType.WEAPON) || super.canApplyAtEnchantingTable(stack, enchantment);
    }

}
