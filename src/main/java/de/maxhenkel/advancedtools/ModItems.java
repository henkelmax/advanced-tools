package de.maxhenkel.advancedtools;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.items.enchantments.ItemBrokenEnchantment;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantment;
import de.maxhenkel.advancedtools.items.enchantments.ItemEnchantmentRemover;
import de.maxhenkel.advancedtools.items.enchantments.ItemPliers;
import de.maxhenkel.advancedtools.items.tools.*;

import java.util.List;

public class ModItems {

    public static final AdvancedPickaxe PICKAXE = new AdvancedPickaxe();
    public static final AdvancedAxe AXE = new AdvancedAxe();
    public static final AdvancedShovel SHOVEL = new AdvancedShovel();
    public static final AdvancedSword SWORD = new AdvancedSword();
    public static final AdvancedHoe HOE = new AdvancedHoe();
    public static final ItemEnchantment ENCHANTMENT = new ItemEnchantment();
    public static final ItemEnchantmentRemover ENCHANTMENT_REMOVER = new ItemEnchantmentRemover();
    public static final ItemBrokenEnchantment BROKEN_ENCHANTMENT = new ItemBrokenEnchantment();
    public static final ItemPliers PLIER = new ItemPliers();


    public static List<AbstractTool> getAllTools() {
        return ImmutableList.of(ModItems.PICKAXE, ModItems.AXE, ModItems.SWORD, ModItems.SHOVEL, ModItems.HOE);
    }

}
