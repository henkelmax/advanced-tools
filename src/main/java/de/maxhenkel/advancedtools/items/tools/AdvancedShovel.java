package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.maxhenkel.advancedtools.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class AdvancedShovel extends AbstractTool {

    private static final Set<Material> EFFECTIVE_ON = Sets.newHashSet(Material.CLAY, Material.SNOW, Material.SNOW_BLOCK, Material.EARTH, Material.SAND, Material.SNOW);

    public AdvancedShovel() {
        setRegistryName(new ResourceLocation(Main.MODID, "shovel"));
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.SHOVEL);
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
        return AdvancedToolMaterial.SHOVEL;
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
        return 1;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (isBroken(context.getItem())) {
            return ActionResultType.PASS;
        }

        ActionResultType result = Items.DIAMOND_SHOVEL.onItemUse(context);
        if (result.isSuccessOrConsume()) {
            StackUtils.incrementToolStat(context.getItem(), StackUtils.Stat.STAT_PATHS_CREATED, 1);
        }
        return result;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        return block == Blocks.SNOW_BLOCK || block == Blocks.SNOW;
    }

}
