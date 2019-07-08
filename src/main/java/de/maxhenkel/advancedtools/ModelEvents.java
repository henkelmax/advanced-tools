package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.render.AdvancedToolModel;
import de.maxhenkel.advancedtools.render.base.UniversalModel;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModelEvents {

    @SubscribeEvent
    public void onModel(ModelBakeEvent event){
       // event.getModelRegistry();
    }

    @SubscribeEvent
    public void onModel(ModelRegistryEvent event){
        //Minecraft.getInstance().getModelManager().
/*
        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "item/pickaxe/empty_pickaxe"),
                new ResourceLocation(Main.MODID, "item/pickaxe/pickaxe_handle"),
                ModItems.PICKAXE)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "item/axe/empty_axe"),
                new ResourceLocation(Main.MODID, "item/axe/axe_handle"),
                ModItems.AXE)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "item/shovel/empty_shovel"),
                new ResourceLocation(Main.MODID, "item/shovel/shovel_handle"),
                ModItems.SHOVEL)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "item/sword/empty_sword"),
                new ResourceLocation(Main.MODID, "item/sword/sword_handle"),
                ModItems.SWORD)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "item/hoe/empty_hoe"),
                new ResourceLocation(Main.MODID, "item/hoe/hoe_handle"),
                ModItems.HOE)));*/

    }

}
