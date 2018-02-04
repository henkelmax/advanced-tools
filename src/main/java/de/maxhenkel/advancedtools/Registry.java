package de.maxhenkel.advancedtools;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Registry {

    public static void addRenderItem(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void addRenderBlock(Block b) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(b), 0, new ModelResourceLocation(b.getRegistryName(), "inventory"));
    }

    public static void registerItem(IForgeRegistry<Item> registry, Item i) {
        registry.register(i);
    }

    public static void registerBlock(IForgeRegistry<Block> registry, Block b) {
        registry.register(b);
    }

    public static void registerItemBlock(IForgeRegistry<Item> registry, Block b) {
        registerItem(registry, new ItemBlock(b).setRegistryName(b.getRegistryName()));
    }

    public static void regiserRecipe(IForgeRegistry<IRecipe> registry, IRecipe recipe) {
        registry.register(recipe);
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        registerItem(event.getRegistry(), ModItems.PICKAXE);
        registerItem(event.getRegistry(), ModItems.ENCHANTMENT);

        GameRegistry.findRegistry(IRecipe.class).register(new ReciepePickaxe().setRegistryName(new ResourceLocation(Main.MODID, "modify_pickaxe")));
        GameRegistry.findRegistry(IRecipe.class).register(new ReciepeEnchantTool().setRegistryName(new ResourceLocation(Main.MODID, "enchant_tool")));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        addRenderItem(ModItems.PICKAXE);
        addRenderItem(ModItems.ENCHANTMENT);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {

    }

    public static void registerSound(IForgeRegistry<SoundEvent> registry, SoundEvent sound) {
        registry.register(sound);
    }

}
