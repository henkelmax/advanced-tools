package de.maxhenkel.advancedtools.render.base;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IAdvancedModel {

    ImmutableList<ResourceLocation> getTextures(ItemStack stack);

    ImmutableList<ResourceLocation> getAllTextures();

    boolean isValid(ResourceLocation modelLocation);

}
