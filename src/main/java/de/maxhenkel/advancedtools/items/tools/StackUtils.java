package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StackUtils {

    private static final String TAG_MATERIAL = "Material";
    private static final String TAG_STATS = "Stats";
    private static final String TAG_TOOL_DATA = "ToolData";

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
        return AdvancedToolMaterial.byName(getStackToolData(stack).getString(TAG_MATERIAL));
    }

    public static ItemStack setMaterial(ItemStack stack, AdvancedToolMaterial material) {

        CompoundNBT toolData = getStackToolData(stack);

        toolData.putString(TAG_MATERIAL, material.getName());

        return stack;
    }

    public static void incrementToolStat(ItemStack stack, Stat stat, int amount) {
        CompoundNBT statTag = getStatTag(stack);
        if (!statTag.contains(stat.getNbtName())) {
            statTag.putInt(stat.getNbtName(), amount);
        } else {
            int count = statTag.getInt(stat.getNbtName());
            count += amount;
            statTag.putInt(stat.getNbtName(), count);
        }
    }

    public static int getToolStat(ItemStack stack, Stat stat) {
        CompoundNBT statTag = getStatTag(stack);
        if (statTag.contains(stat.getNbtName())) {
            return statTag.getInt(stat.getNbtName());
        } else {
            return 0;
        }
    }

    public static Map<Stat, Integer> getToolStats(ItemStack stack) {
        Map<Stat, Integer> stats = new HashMap<>();
        CompoundNBT statTag = getStatTag(stack);

        Set<String> keys = statTag.keySet();
        for (String key : keys) {
            Stat stat = Stat.getStatFromNBTName(key);
            if (stat != null) {
                stats.put(stat, statTag.getInt(key));
            }
        }

        return stats;
    }


    private static CompoundNBT getStatTag(ItemStack stack) {
        CompoundNBT td = getStackToolData(stack);
        if (!td.contains(TAG_STATS)) {
            td.put(TAG_STATS, new CompoundNBT());
        }

        return td.getCompound(TAG_STATS);
    }

    public static CompoundNBT getStackCompound(ItemStack stack) {
        return stack.getOrCreateTag();
    }

    public static CompoundNBT getStackToolData(ItemStack stack) {
        CompoundNBT compound = getStackCompound(stack);
        if (!compound.contains(TAG_TOOL_DATA)) {
            compound.put(TAG_TOOL_DATA, new CompoundNBT());
        }

        return compound.getCompound(TAG_TOOL_DATA);
    }

    public static class Stat {

        public static final Stat STAT_BLOCKS_MINED = new Stat("blocks_mined", "BlocksMined");
        public static final Stat STAT_MOBS_HIT = new Stat("mobs_hit", "MobsHit");
        public static final Stat STAT_HOED = new Stat("farmland_hoed", "FarmlandHoed");
        public static final Stat STAT_PATHS_CREATED = new Stat("paths_created", "PathsCreated");
        public static final Stat STAT_LOGS_STRIPPED = new Stat("logs_stripped", "LogsStripped");
        public static final List<Stat> STATS = ImmutableList.of(STAT_BLOCKS_MINED, STAT_MOBS_HIT, STAT_HOED, STAT_PATHS_CREATED, STAT_LOGS_STRIPPED);

        private String nbtName;
        private String translationName;

        public Stat(String translationName, String nbtName) {
            this.nbtName = nbtName;
            this.translationName = translationName;
        }

        public String getNbtName() {
            return nbtName;
        }

        public String getTranslationName() {
            return "stat." + translationName;
        }

        public static Stat getStatFromNBTName(String name) {
            return STATS.stream().filter(stat -> stat.getNbtName().equals(name)).findAny().orElse(null);
        }

        public IFormattableTextComponent getTranslation(Object... args) {
            return new TranslationTextComponent(getTranslationName(), args);
        }
    }

}
