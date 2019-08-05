package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.crafting.*;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "advancedtools";

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            clientStart();
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void clientStart() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::clientSetup);
        // ModelLoaderRegistry.registerLoader(new AdvancedModelLoader());
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new SwordEvents());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ModItems.ENCHANTMENT,
                ModItems.ENCHANTMENT_REMOVER,
                ModItems.AXE,
                ModItems.HOE,
                ModItems.PICKAXE,
                ModItems.SHOVEL,
                ModItems.SWORD
        );
    }

    public static IRecipeSerializer CRAFTING_COMBINE_ENCHANTMENTS;
    public static IRecipeSerializer CRAFTING_CONVERT_BOOK;
    public static IRecipeSerializer CRAFTING_CONVERT_ENCHANTMENT;
    public static IRecipeSerializer CRAFTING_ENCHANT_TOOL;
    public static IRecipeSerializer CRAFTING_EREMOVE_ENCHANTMENT;
    public static IRecipeSerializer CRAFTING_REPAIR_TOOL;
    public static RecipeToolMaterial.RecipeToolMaterialSerializer CRAFTING_TOOL_MATERIAL;

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CRAFTING_COMBINE_ENCHANTMENTS = new SpecialRecipeSerializer<>(RecipeCombineEnchantments::new);
        CRAFTING_COMBINE_ENCHANTMENTS.setRegistryName(new ResourceLocation(MODID, "crafting_special_combine_enchantments"));
        event.getRegistry().register(CRAFTING_COMBINE_ENCHANTMENTS);

        CRAFTING_CONVERT_BOOK = new SpecialRecipeSerializer<>(RecipeConvertBook::new);
        CRAFTING_CONVERT_BOOK.setRegistryName(new ResourceLocation(MODID, "crafting_special_convert_book"));
        event.getRegistry().register(CRAFTING_CONVERT_BOOK);

        CRAFTING_CONVERT_ENCHANTMENT = new SpecialRecipeSerializer<>(RecipeConvertEnchantment::new);
        CRAFTING_CONVERT_ENCHANTMENT.setRegistryName(new ResourceLocation(MODID, "crafting_special_convert_enchantment"));
        event.getRegistry().register(CRAFTING_CONVERT_ENCHANTMENT);

        CRAFTING_ENCHANT_TOOL = new SpecialRecipeSerializer<>(RecipeEnchantTool::new);
        CRAFTING_ENCHANT_TOOL.setRegistryName(new ResourceLocation(MODID, "crafting_special_enchant_tool"));
        event.getRegistry().register(CRAFTING_ENCHANT_TOOL);

        CRAFTING_EREMOVE_ENCHANTMENT = new SpecialRecipeSerializer<>(RecipeRemoveEnchantment::new);
        CRAFTING_EREMOVE_ENCHANTMENT.setRegistryName(new ResourceLocation(MODID, "crafting_special_remove_enchantment"));
        event.getRegistry().register(CRAFTING_EREMOVE_ENCHANTMENT);

        CRAFTING_REPAIR_TOOL = new SpecialRecipeSerializer<>(RecipeRepairTool::new);
        CRAFTING_REPAIR_TOOL.setRegistryName(new ResourceLocation(MODID, "crafting_special_repair_tool"));
        event.getRegistry().register(CRAFTING_REPAIR_TOOL);

        CRAFTING_TOOL_MATERIAL = new RecipeToolMaterial.RecipeToolMaterialSerializer();
        CRAFTING_TOOL_MATERIAL.setRegistryName(new ResourceLocation(MODID, "tool_recipe"));
        event.getRegistry().register(CRAFTING_TOOL_MATERIAL);
    }

}
