package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public abstract class AbstractTool extends ItemTool {

    public AbstractTool() {
        super(ToolMaterial.DIAMOND, null);
        setMaxDamage(100);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        StackUtils.updateFlags(stack);

        if(isBroken(stack)){
            tooltip.add(new TextComponentTranslation("tooltip.broken").getFormattedText());
        }

        de.maxhenkel.advancedtools.items.tools.ToolMaterial mat=StackUtils.getMaterial(stack);
        if(mat!=null){
            tooltip.add(new TextComponentTranslation("tooltip.material", mat.getLocalizedName()).getFormattedText());
            if(!flagIn.isAdvanced()){
                tooltip.add(new TextComponentTranslation("tooltip.durability_left", getMaxDamage(stack)-stack.getItemDamage()).getFormattedText());
            }
        }
    }

    @Override
    public abstract Set<String> getToolClasses(ItemStack stack);

    public abstract float getAttackDamage(ItemStack stack);

    public abstract float getAttackSpeed(ItemStack stack);

    public abstract float getEfficiency(ItemStack stack);

    public abstract Set<Block> getEffectiveBlocks(ItemStack stack);

    public abstract int getHarvestLevel(ItemStack stack);

    @Override
    public abstract int getMaxDamage(ItemStack stack);

    @Override
    public abstract String getItemStackDisplayName(ItemStack stack);

    public abstract ItemStack repair(ItemStack in, de.maxhenkel.advancedtools.items.tools.ToolMaterial material, int count);

    public abstract ItemStack applyEnchantment(ItemStack tool, ItemStack enchantment);

    @Override
    public int getMaxDamage() {
        new IllegalArgumentException("getMaxDamage() called without ItemStack").printStackTrace();
        return 100;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return 0;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public String getToolMaterialName() {
        return "";
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND) {
            if(!isBroken(stack)){
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double) getAttackDamage(stack), 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) getAttackSpeed(stack), 0));
            }
        }

        return multimap;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if(isBroken(stack)){
            return 0F;//TODO fix normal speed?
        }
        for (String type : getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state))
                return getEfficiency(stack);
        }
        return getEffectiveBlocks(stack).contains(state.getBlock()) ? getEfficiency(stack) : 1.0F;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
        return getHarvestLevel(stack);
    }

    public boolean isBroken(ItemStack stack) {
        return getDamage(stack) >= getMaxDamage(stack);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        int maxDamage = getMaxDamage(stack);

        if (damage > maxDamage) {
            return;
        } else {
            super.setDamage(stack, damage);
        }
    }
}
