package de.maxhenkel.advancedtools.render;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModelWrapper {

    public ImmutableList<ResourceLocation> getTextures(ItemStack stack){
        ImmutableList.Builder<ResourceLocation> texBuilder = ImmutableList.builder();
        ToolMaterial mat= StackUtils.getMaterial(stack);
        if(mat!=null){
            texBuilder.add(mat.getTextureFor("pickaxe"));
        }

        texBuilder.add(new ResourceLocation(Main.MODID, "items/handle_wood"));
        return texBuilder.build();
    }

    public ImmutableList<ResourceLocation> getAllTextures(){
        ImmutableList.Builder<ResourceLocation> texBuilder = ImmutableList.builder();
        ToolMaterial mat= ToolMaterial.DIAMOND;
        if(mat!=null){
            texBuilder.add(mat.getTextureFor("pickaxe"));
        }

        texBuilder.add(new ResourceLocation(Main.MODID, "items/handle_wood"));
        return texBuilder.build();
    }

    public boolean isValid(ResourceLocation modelLocation){
        return modelLocation.getResourcePath().equals("pickaxe");
    }

}
