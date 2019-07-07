package de.maxhenkel.advancedtools.items.tools.matcher;

import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

public class TagMatcher implements MaterialMatcher {

    private ResourceLocation tag;

    public TagMatcher(ResourceLocation tag) {
        this.tag = tag;
    }

    @Override
    public boolean isMaterial(ItemStack stack) {
        return stack.getItem().getTags().contains(tag);
    }

    @Override
    public List<ItemStack> getAll() {
        return ItemTags.getCollection().get(tag).getAllElements().stream().map(item -> new ItemStack(item)).collect(Collectors.toList());
    }

}
