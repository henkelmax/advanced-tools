package de.maxhenkel.advancedtools.items.tools.matcher;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class SimpleMatcher implements MaterialMatcher {

    private ItemStack stack;

    public SimpleMatcher(Item item) {
        this.stack = new ItemStack(item);
    }

    public SimpleMatcher(Block block) {
        this.stack = new ItemStack(block);
    }

    public SimpleMatcher(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean isMaterial(ItemStack stack) {
        return this.stack.getItem().equals(stack.getItem());
    }

    @Override
    public List<ItemStack> getAll() {
        return Arrays.asList(stack);
    }

}
