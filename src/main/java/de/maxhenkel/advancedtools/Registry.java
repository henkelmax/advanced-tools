package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import de.maxhenkel.advancedtools.items.tools.matcher.OredictMatcher;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

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
        registerItem(event.getRegistry(), ModItems.AXE);
        registerItem(event.getRegistry(), ModItems.SHOVEL);
        registerItem(event.getRegistry(), ModItems.SWORD);
        registerItem(event.getRegistry(), ModItems.HOE);
        registerItem(event.getRegistry(), ModItems.ENCHANTMENT);

        IForgeRegistryModifiable<IRecipe> registry=(IForgeRegistryModifiable<IRecipe>)GameRegistry.findRegistry(IRecipe.class);
        registry.register(new ReciepeRepairTool().setRegistryName(new ResourceLocation(Main.MODID, "modify_tool")));
        registry.register(new ReciepeEnchantTool().setRegistryName(new ResourceLocation(Main.MODID, "enchant_tool")));
        registry.register(new ReciepeConvertBook().setRegistryName(new ResourceLocation(Main.MODID, "convert_book")));

        registry.remove(new ResourceLocation("wooden_pickaxe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("wooden_pickaxe")));
        registry.remove(new ResourceLocation("stone_pickaxe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("stone_pickaxe")));
        registry.remove(new ResourceLocation("golden_pickaxe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("golden_pickaxe")));
        registry.remove(new ResourceLocation("iron_pickaxe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("iron_pickaxe")));
        registry.remove(new ResourceLocation("diamond_pickaxe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("diamond_pickaxe")));

        registry.remove(new ResourceLocation("wooden_axe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("wooden_axe")));
        registry.remove(new ResourceLocation("stone_axe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("stone_axe")));
        registry.remove(new ResourceLocation("golden_axe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("golden_axe")));
        registry.remove(new ResourceLocation("iron_axe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("iron_axe")));
        registry.remove(new ResourceLocation("diamond_axe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("diamond_axe")));

        registry.remove(new ResourceLocation("wooden_shovel"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("wooden_shovel")));
        registry.remove(new ResourceLocation("stone_shovel"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("stone_shovel")));
        registry.remove(new ResourceLocation("golden_shovel"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("golden_shovel")));
        registry.remove(new ResourceLocation("iron_shovel"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("iron_shovel")));
        registry.remove(new ResourceLocation("diamond_shovel"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("diamond_shovel")));

        registry.remove(new ResourceLocation("wooden_sword"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("wooden_sword")));
        registry.remove(new ResourceLocation("stone_sword"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("stone_sword")));
        registry.remove(new ResourceLocation("golden_sword"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("golden_sword")));
        registry.remove(new ResourceLocation("iron_sword"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("iron_sword")));
        registry.remove(new ResourceLocation("diamond_sword"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("diamond_sword")));

        registry.remove(new ResourceLocation("wooden_hoe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("wooden_hoe")));
        registry.remove(new ResourceLocation("stone_hoe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("stone_hoe")));
        registry.remove(new ResourceLocation("golden_hoe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("golden_hoe")));
        registry.remove(new ResourceLocation("iron_hoe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("iron_hoe")));
        registry.remove(new ResourceLocation("diamond_hoe"));
        registry.register(new ReciepeVoid().setRegistryName(new ResourceLocation("diamond_hoe")));


        //Pickaxe
        for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
            ItemStack pickaxe = new ItemStack(ModItems.PICKAXE);
            StackUtils.setMaterial(pickaxe, material);
            OreDictionary.registerOre(material.getOredictName("pickaxe"), pickaxe);
            if (material.getMatcher() instanceof OredictMatcher) {
                OredictMatcher matcher = (OredictMatcher) material.getMatcher();
                GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, material.getName() +"_pickaxe"), null, pickaxe,
                        "MMM", " S ", " S ",
                        Character.valueOf('S'), "stickWood",
                        Character.valueOf('M'), matcher.getOredict());
            }
        }

        //Axe
        for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
            ItemStack axe = new ItemStack(ModItems.AXE);
            StackUtils.setMaterial(axe, material);
            OreDictionary.registerOre(material.getOredictName("axe"), axe);
            if (material.getMatcher() instanceof OredictMatcher) {
                OredictMatcher matcher = (OredictMatcher) material.getMatcher();
                GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, material.getName() +"_axe"), null, axe,
                        "MM", "SM", "S ",
                        Character.valueOf('S'), "stickWood",
                        Character.valueOf('M'), matcher.getOredict());
            }
        }

        //Shovel
        for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
            ItemStack shovel = new ItemStack(ModItems.SHOVEL);
            StackUtils.setMaterial(shovel, material);
            OreDictionary.registerOre(material.getOredictName("shovel"), shovel);
            if (material.getMatcher() instanceof OredictMatcher) {
                OredictMatcher matcher = (OredictMatcher) material.getMatcher();
                GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, material.getName() +"_shovel"), null, shovel,
                        "M", "S", "S",
                        Character.valueOf('S'), "stickWood",
                        Character.valueOf('M'), matcher.getOredict());
            }
        }

        //Sword
        for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
            ItemStack sword = new ItemStack(ModItems.SWORD);
            StackUtils.setMaterial(sword, material);
            OreDictionary.registerOre(material.getOredictName("sword"), sword);
            if (material.getMatcher() instanceof OredictMatcher) {
                OredictMatcher matcher = (OredictMatcher) material.getMatcher();
                GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, material.getName() +"_sword"), null, sword,
                        "M", "M", "S",
                        Character.valueOf('S'), "stickWood",
                        Character.valueOf('M'), matcher.getOredict());
            }
        }

        //Hoe
        for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
            ItemStack sword = new ItemStack(ModItems.HOE);
            StackUtils.setMaterial(sword, material);
            OreDictionary.registerOre(material.getOredictName("hoe"), sword);
            if (material.getMatcher() instanceof OredictMatcher) {
                OredictMatcher matcher = (OredictMatcher) material.getMatcher();
                GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, material.getName() +"_hoe"), null, sword,
                        "MM", " S", " S",
                        Character.valueOf('S'), "stickWood",
                        Character.valueOf('M'), matcher.getOredict());
            }
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        addRenderItem(ModItems.PICKAXE);
        addRenderItem(ModItems.AXE);
        addRenderItem(ModItems.SHOVEL);
        addRenderItem(ModItems.SWORD);
        addRenderItem(ModItems.HOE);
        addRenderItem(ModItems.ENCHANTMENT);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {

    }

    public static void registerSound(IForgeRegistry<SoundEvent> registry, SoundEvent sound) {
        registry.register(sound);
    }

}
