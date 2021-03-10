package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.maxhenkel.advancedtools.Main;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class AdvancedHoe extends AbstractTool {

    private static final Set<Material> EFFECTIVE_ON = Sets.newHashSet(Material.LEAVES, Material.SPONGE, Material.GRASS);

    public AdvancedHoe() {
        setRegistryName(new ResourceLocation(Main.MODID, "hoe"));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
        if (isBroken(stack)) {
            return ActionResultType.PASS;
        }

        ActionResultType result = Items.DIAMOND_HOE.useOn(context);
        if (result.consumesAction()) {
            StackUtils.incrementToolStat(stack, StackUtils.Stat.STAT_HOED, 1);
        }
        return result;
    }

    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return ImmutableSet.of(ToolType.HOE);
    }

    @Override
    public float getAttackDamage(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return mat.getAttackModifier() + 1;
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
        return AdvancedToolMaterial.HOE;
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, livingEntity -> {
        });
        return true;
    }
}
