package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModCreativeTabs;
import de.maxhenkel.advancedtools.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractTool extends ToolItem {

    public AbstractTool() {
        super(0F, 0F, ItemTier.DIAMOND, null, new Item.Properties().group(ModCreativeTabs.TAB_ADVANCED_TOOLS).maxDamage(100));
        for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
            addPropertyOverride(new ResourceLocation(Main.MODID, material.getName()), (itemStack, world, livingEntity) -> material.equals(StackUtils.getMaterial(itemStack)) ? 1F : 0F);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return Math.min(Math.max(getDurability(stack) - 1, 0), amount);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (isBroken(stack)) {
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        StackUtils.updateFlags(stack);
        if (isBroken(stack)) {
            tooltip.add(new TranslationTextComponent("tooltip.broken").applyTextStyle(TextFormatting.DARK_RED));
        }

        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            tooltip.add(new TranslationTextComponent("tooltip.material", mat.getDisplayName().applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY));
            if (!flagIn.isAdvanced()) {
                tooltip.add(new TranslationTextComponent("tooltip.durability_left", new StringTextComponent(String.valueOf(getMaxDamage(stack) - stack.getDamage())).applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY));
            }
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (!enchantments.isEmpty()) {
            tooltip.add(new TranslationTextComponent("tooltips.enchantments").applyTextStyle(TextFormatting.GRAY));
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                tooltip.add(new StringTextComponent("  - ").appendSibling(entry.getKey().getDisplayName(entry.getValue()).applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY));
            }
        }

        Map<String, Integer> stats = StackUtils.getToolStats(stack);
        if (!stats.isEmpty()) {
            tooltip.add(new TranslationTextComponent("tooltips.stats").applyTextStyle(TextFormatting.GRAY));
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                tooltip.add(new StringTextComponent("  - ").appendSibling(new TranslationTextComponent("stat." + entry.getKey(), new StringTextComponent(String.valueOf(entry.getValue())).applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY)).applyTextStyle(TextFormatting.GRAY));
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public abstract Set<ToolType> getToolTypes(ItemStack stack);

    public abstract float getAttackDamage(ItemStack stack);

    public abstract float getAttackSpeed(ItemStack stack);

    public abstract float getEfficiency(ItemStack stack);

    public abstract Set<Material> getEffectiveMaterials(ItemStack stack);

    public abstract int getHarvestLevel(ItemStack stack);

    public abstract String getPrimaryToolType();

    public abstract ImmutableList<Enchantment> getValidEnchantments(ItemStack stack);

    @Override
    public abstract int getMaxDamage(ItemStack stack);

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            return new StringTextComponent(mat.getDisplayName().getFormattedText() + " " + new TranslationTextComponent("tool." + getPrimaryToolType()).getFormattedText()).applyTextStyle(TextFormatting.WHITE);
        }
        return new TranslationTextComponent("tool." + getPrimaryToolType()).applyTextStyle(TextFormatting.WHITE);
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

        if (count > repairCost) {
            return ItemStack.EMPTY;
        }

        if (currMat.equals(material)) {
            ItemStack newStack = in.copy();
            int maxDamage = getMaxDamage(newStack);
            int damageRev = newStack.getDamage();

            int repairPerCount = (maxDamage / repairCost) + 1;

            newStack.setDamage(damageRev - (repairPerCount * count));

            return newStack;
        }

        if (count < repairCost) {
            return ItemStack.EMPTY;
        }

        ItemStack newStack = in.copy();
        newStack.setDamage(0);

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

            if (!getValidEnchantments(tool).contains(ench)) {
                return ItemStack.EMPTY;
            }

            List<EnchantmentData> enchantments = EnchantmentTools.getEnchantments(tool);
            Iterator<EnchantmentData> iterator = enchantments.iterator();
            while (iterator.hasNext()) {
                EnchantmentData data = iterator.next();
                if (data.enchantment.equals(ench)) {
                    iterator.remove();
                }
            }
            EnchantmentTools.setEnchantments(enchantments, newTool);
            return newTool;

        }
        return ItemStack.EMPTY;
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

    public boolean countHitStats(ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EquipmentSlotType.MAINHAND) {
            if (!isBroken(stack)) {
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double) getAttackDamage(stack), AttributeModifier.Operation.ADDITION));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
            }
        }

        return multimap;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (isBroken(stack)) {
            return 0F;//TODO fix normal speed?
        }
        for (ToolType type : getToolTypes(stack)) {
            if (state.getBlock().isToolEffective(state, type))
                return getEfficiency(stack);
        }
        return getEffectiveMaterials(stack).contains(state.getMaterial()) ? getEfficiency(stack) : 1.0F;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType toolType, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        if (getToolTypes(stack).contains(toolType)) {
            return getHarvestLevel(stack);
        } else {
            return -1;
        }
    }

    public boolean isBroken(ItemStack stack) {
        return getDurability(stack) <= 1;
    }

    public int getDurability(ItemStack stack) {
        return getMaxDamage(stack) - getDamage(stack);
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

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.lifespan = Integer.MAX_VALUE;
        entity.setInvulnerable(true);
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        boolean flag = super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        if (flag && countBreakStats(stack)) {
            StackUtils.incrementToolStat(stack, StackUtils.STAT_BLOCKS_MINED, 1);
        }
        return flag;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean flag = super.hitEntity(stack, target, attacker);
        if (flag && countHitStats(stack)) {
            StackUtils.incrementToolStat(stack, StackUtils.STAT_MOBS_HIT, 1);
        }
        return flag;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        for (ToolType type : getToolTypes(stack)) {
            if (state.getBlock().isToolEffective(state, type))
                return true;
        }
        return super.canHarvestBlock(stack, state);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }
}
