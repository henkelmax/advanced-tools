package de.maxhenkel.advancedtools.items.tools.matcher;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface MaterialMatcher {

    boolean isMaterial(ItemStack stack);

    List<ItemStack> getAll();

}
