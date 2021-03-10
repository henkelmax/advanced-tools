package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.maxhenkel.advancedtools.Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class AdvancedPickaxe extends AbstractTool {

    private static final Set<Material> EFFECTIVE_ON = Sets.newHashSet(Material.HEAVY_METAL, Material.ICE, Material.METAL, Material.ICE_SOLID, Material.STONE);

    public AdvancedPickaxe() {
        setRegistryName(new ResourceLocation(Main.MODID, "pickaxe"));
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.PICKAXE);
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
            return -2.8F;
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
        return AdvancedToolMaterial.PICKAXE;
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
        return material == AdvancedToolMaterial.NETHERITE ? 1 : 3;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        int i = getTier().getLevel();
        if (state.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= state.getHarvestLevel();
        }
        Material material = state.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
    }

}
