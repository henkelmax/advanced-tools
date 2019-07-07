package de.maxhenkel.advancedtools.render;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.render.base.IAdvancedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AdvancedToolModel implements IAdvancedModel {

    private ResourceLocation handle;
    private ResourceLocation def;
    private AbstractTool tool;

    public AdvancedToolModel(ResourceLocation def, ResourceLocation handle, AbstractTool tool){
        this.handle=handle;
        this.tool=tool;
        this.def=def;
    }

    @Override
    public ImmutableList<ResourceLocation> getTextures(ItemStack stack) {
        ImmutableList.Builder<ResourceLocation> texBuilder = ImmutableList.builder();
        AdvancedToolMaterial mat = StackUtils.getMaterial(stack);
        if (mat != null) {
            texBuilder.add(mat.getTextureFor(tool.getPrimaryToolType()));
            texBuilder.add(handle);
        }else{
            texBuilder.add(def);
        }

        return texBuilder.build();
    }

    @Override
    public ImmutableList<ResourceLocation> getAllTextures() {
        ImmutableList.Builder<ResourceLocation> texBuilder = ImmutableList.builder();
        for(AdvancedToolMaterial mat: AdvancedToolMaterial.getAll()){
            texBuilder.add(mat.getTextureFor(tool.getPrimaryToolType()));
        }

        texBuilder.add(handle);
        texBuilder.add(def);
        return texBuilder.build();
    }

    @Override
    public boolean isValid(ResourceLocation modelLocation) {
        return modelLocation.getPath().equals(tool.getPrimaryToolType()) && modelLocation.getNamespace().equals(Main.MODID);
    }
}