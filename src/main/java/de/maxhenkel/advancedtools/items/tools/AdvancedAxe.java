package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.maxhenkel.advancedtools.Main;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class AdvancedAxe extends AbstractTool {

    private static final Set<Material> EFFECTIVE_ON = Sets.newHashSet(Material.WOOD, Material.field_237214_y_);

    public AdvancedAxe() {
        setRegistryName(new ResourceLocation(Main.MODID, "axe"));
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.AXE);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (isBroken(context.getItem())) {
            return ActionResultType.PASS;
        }

        ActionResultType result = Items.DIAMOND_AXE.onItemUse(context);
        if (result.equals(ActionResultType.SUCCESS)) {
            StackUtils.incrementToolStat(context.getItem(), StackUtils.Stat.STAT_LOGS_STRIPPED, 1);
        }
        return result;
    }

    @Override
    public float getAttackDamage(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getAttackModifier() * 2.5F;
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
    public int getRepairCost(ItemStack stack, AdvancedToolMaterial material) {
        return material == AdvancedToolMaterial.NETHERITE ? 1 : 3;
    }
}
