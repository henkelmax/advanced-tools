package de.maxhenkel.advancedtools.items.tools;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StackUtils {

    // /give @p advancedtools:pickaxe 1 255 {"tool_data": {"material": "iron"}}
    // /give @p advancedtools:enchantment 1 0 {"enchantment": {"name": "minecraft:unbreaking", "level": 5}}

    public static final String STAT_BLOCKS_MINED = "blocks_mined";
    public static final String STAT_MOBS_HIT = "mobs_hit";
    public static final String STAT_HOED = "farmland_hoed";
    public static final String STAT_PATHS_CREATED = "paths_created";
    public static final String STAT_LOGS_STRIPPED = "logs_stripped";

    public static void addEnchantment(ItemStack stack, Enchantment enchantment, int level) {
        stack.addEnchantment(enchantment, level);
    }

    public static void updateFlags(ItemStack stack) {
        CompoundNBT compound = getStackCompound(stack);
        if (!compound.contains("HideFlags")) {
            compound.putInt("HideFlags", 0);
        }

        compound.putInt("HideFlags", compound.getInt("HideFlags") | 1);
    }

    public static AdvancedToolMaterial getMaterial(ItemStack stack) {
        return AdvancedToolMaterial.byName(getStackToolData(stack).getString("material"));
    }

    public static ItemStack setMaterial(ItemStack stack, AdvancedToolMaterial material) {

        CompoundNBT toolData = getStackToolData(stack);

        toolData.putString("material", material.getName());

        return stack;
    }

    public static void incrementToolStat(ItemStack stack, String name, int amount) {
        CompoundNBT statTag = getStatTag(stack);
        if (!statTag.contains(name)) {
            statTag.putInt(name, amount);
        } else {
            int stat = statTag.getInt(name);
            stat += amount;
            statTag.putInt(name, stat);
        }
    }

    public static int getToolStat(ItemStack stack, String name) {
        CompoundNBT statTag = getStatTag(stack);
        if (statTag.contains(name)) {
            return statTag.getInt(name);
        } else {
            return 0;
        }
    }

    public static Map<String, Integer> getToolStats(ItemStack stack) {
        Map<String, Integer> stats = new HashMap<>();
        CompoundNBT statTag = getStatTag(stack);

        Set<String> keys = statTag.keySet();
        for (String key : keys) {
            stats.put(key, statTag.getInt(key));
        }

        return stats;
    }

    public static CompoundNBT getStatTag(ItemStack stack) {
        CompoundNBT td = getStackToolData(stack);
        if (!td.contains("stats")) {
            td.put("stats", new CompoundNBT());
        }

        return td.getCompound("stats");
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack.isEmpty();
    }

    public static CompoundNBT getStackCompound(ItemStack stack) {
        return stack.getOrCreateTag();
    }

    public static CompoundNBT getStackToolData(ItemStack stack) {
        CompoundNBT compound = getStackCompound(stack);
        if (!compound.contains("tool_data")) {
            compound.put("tool_data", new CompoundNBT());
        }

        return compound.getCompound("tool_data");
    }

}
