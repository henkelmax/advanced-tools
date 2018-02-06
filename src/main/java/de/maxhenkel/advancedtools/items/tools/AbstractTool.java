package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.advancedtools.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractTool extends ItemTool {

    public AbstractTool() {
        super(ToolMaterial.DIAMOND, null);
        setMaxDamage(100);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        StackUtils.updateFlags(stack);
        if (isBroken(stack)) {
            tooltip.add(new TextComponentTranslation("tooltip.broken").getFormattedText());
        }

        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            tooltip.add(new TextComponentTranslation("tooltip.material", mat.getLocalizedName()).getFormattedText());
            if (!flagIn.isAdvanced()) {
                tooltip.add(new TextComponentTranslation("tooltip.durability_left", getMaxDamage(stack) - stack.getItemDamage()).getFormattedText());
            }
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (!enchantments.isEmpty()) {
            tooltip.add(new TextComponentTranslation("tooltips.enchantments").getFormattedText());
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                tooltip.add("  - " + entry.getKey().getTranslatedName(entry.getValue()));
            }
        }

        Map<String, Integer> stats = StackUtils.getToolStats(stack);
        if (!stats.isEmpty()) {
            tooltip.add(new TextComponentTranslation("tooltips.stats").getFormattedText());
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                tooltip.add("  - " + new TextComponentTranslation("stat." + entry.getKey(), entry.getValue()).getFormattedText());
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

    public abstract String getPrimaryToolType();

    public abstract ImmutableList<Enchantment> getValidEnchantments(ItemStack stack);

    @Override
    public abstract int getMaxDamage(ItemStack stack);

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return ChatFormatting.WHITE + mat.getLocalizedName() + " " + new TextComponentTranslation("tool." + getPrimaryToolType() + ".name").getUnformattedText();
        }
        return ChatFormatting.WHITE + new TextComponentTranslation("tool." + getPrimaryToolType() + ".name").getFormattedText();
    }

    public abstract int getRepairCost(ItemStack stack);

    public ItemStack repair(ItemStack in, AdvancedToolMaterial material, int count) {
        AdvancedToolMaterial currMat = StackUtils.getMaterial(in);
        if (currMat == null) {
            return ItemStack.EMPTY;
        }

        int repairCost = getRepairCost(in);
        if (repairCost < 1) {
            repairCost = 1;
        }
        if (repairCost > 8) {
            repairCost = 8;
        }

        if (currMat.equals(material)) {
            ItemStack newStack = in.copy();
            int maxDamage = getMaxDamage(newStack);
            int damageRev = newStack.getItemDamage();

            int repairPerCount = (maxDamage / repairCost) + 1;

            newStack.setItemDamage(damageRev - (repairPerCount * count));

            return newStack;
        }

        if (count < repairCost) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = in.copy();
        newStack.setItemDamage(0);

        return StackUtils.setMaterial(newStack, material);
    }

    public ItemStack applyEnchantment(ItemStack tool, ItemStack enchantment) {
        ItemStack newTool = tool.copy();
        EnchantmentData data = ModItems.ENCHANTMENT.getEnchantment(enchantment);
        if (data != null) {
            if (getValidEnchantments(tool).contains(data.enchantment)) {
                List<EnchantmentData> enchantments = EnchantmentTools.getEnchantments(newTool);
                EnchantmentHelper.removeIncompatible(enchantments, data);
                enchantments.add(data);
                EnchantmentTools.setEnchantments(enchantments, newTool);
                return newTool;
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack removeEnchantment(ItemStack tool, ItemStack enchantmentRemover) {
        ItemStack newTool = tool.copy();
        Enchantment ench = ModItems.ENCHANTMENT_REMOVER.getEnchantment(enchantmentRemover);
        if (ench != null) {

            if(!getValidEnchantments(tool).contains(ench)){
                return ItemStack.EMPTY;
            }

            List<EnchantmentData> enchantments = EnchantmentTools.getEnchantments(tool);
            Iterator<EnchantmentData> iterator=enchantments.iterator();
            while(iterator.hasNext()){
                EnchantmentData data=iterator.next();
                if(data.enchantment.equals(ench)){
                    iterator.remove();
                }
            }
            EnchantmentTools.setEnchantments(enchantments, newTool);
            return newTool;

        }
        return ItemStack.EMPTY;
    }

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
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    public boolean countBreakStats(ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND) {
            if (!isBroken(stack)) {
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double) getAttackDamage(stack), 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) getAttackSpeed(stack), 0));
            }
        }

        return multimap;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (isBroken(stack)) {
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
        if (getToolClasses(stack).contains(toolClass)) {
            return getHarvestLevel(stack);
        } else {
            return -1;
        }
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

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        if (location instanceof EntityItem) {
            EntityItem item = (EntityItem) location;
            item.lifespan = Integer.MAX_VALUE;
            item.setEntityInvulnerable(true);
        }
        return null;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        boolean flag = super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        if (flag && countBreakStats(stack)) {
            StackUtils.incrementToolStat(stack, StackUtils.STAT_BLOCKS_MINED, 1);
        }
        return flag;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }
}
