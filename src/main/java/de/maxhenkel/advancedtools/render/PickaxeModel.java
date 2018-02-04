package de.maxhenkel.advancedtools.render;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Constants;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.ToolMaterial;
import de.maxhenkel.advancedtools.render.base.IAdvancedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class PickaxeModel implements IAdvancedModel {

    @Override
    public ImmutableList<ResourceLocation> getTextures(ItemStack stack) {
        ImmutableList.Builder<ResourceLocation> texBuilder = ImmutableList.builder();
        ToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            texBuilder.add(mat.getTextureFor(Constants.PICKAXE));
        }

        texBuilder.add(new ResourceLocation(Main.MODID, "items/wood_handle"));
        return texBuilder.build();
    }

    @Override
    public ImmutableList<ResourceLocation> getAllTextures() {
        ImmutableList.Builder<ResourceLocation> texBuilder = ImmutableList.builder();
        for(ToolMaterial mat:ToolMaterial.getAll()){
            texBuilder.add(mat.getTextureFor(Constants.PICKAXE));
        }

        texBuilder.add(new ResourceLocation(Main.MODID, "items/wood_handle"));
        return texBuilder.build();
    }

    @Override
    public boolean isValid(ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().equals("pickaxe") && modelLocation.getResourceDomain().equals(Main.MODID);
    }
}
