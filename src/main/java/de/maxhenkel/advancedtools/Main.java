package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.crafting.*;
import de.maxhenkel.corelib.CommonRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "advancedtools";

    public static ServerConfig SERVER_CONFIG;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        SERVER_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.SERVER, ServerConfig.class);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new SwordEvents());
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

    public static IRecipeSerializer<RecipeCombineEnchantments> CRAFTING_COMBINE_ENCHANTMENTS;
    public static IRecipeSerializer<RecipeSplitEnchantments> CRAFTING_SPLIT_ENCHANTMENTS;
    public static IRecipeSerializer<RecipeConvertBook> CRAFTING_CONVERT_BOOK;
    public static IRecipeSerializer<RecipeConvertEnchantment> CRAFTING_CONVERT_ENCHANTMENT;
    public static IRecipeSerializer<RecipeEnchantTool> CRAFTING_ENCHANT_TOOL;
    public static IRecipeSerializer<RecipeRemoveEnchantment> CRAFTING_EREMOVE_ENCHANTMENT;
    public static IRecipeSerializer<RecipeRepairTool> CRAFTING_REPAIR_TOOL;
    public static RecipeToolMaterial.RecipeToolMaterialSerializer CRAFTING_TOOL_MATERIAL;
    public static RecipeConvertTool.RecipeConvertToolSerializer CRAFTING_CONVERT_TOOL;

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CRAFTING_COMBINE_ENCHANTMENTS = new SpecialRecipeSerializer<>(RecipeCombineEnchantments::new);
        CRAFTING_COMBINE_ENCHANTMENTS.setRegistryName(new ResourceLocation(MODID, "crafting_special_combine_enchantments"));
        event.getRegistry().register(CRAFTING_COMBINE_ENCHANTMENTS);

        CRAFTING_SPLIT_ENCHANTMENTS = new SpecialRecipeSerializer<>(RecipeSplitEnchantments::new);
        CRAFTING_SPLIT_ENCHANTMENTS.setRegistryName(new ResourceLocation(MODID, "crafting_special_split_enchantments"));
        event.getRegistry().register(CRAFTING_SPLIT_ENCHANTMENTS);

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

        CRAFTING_CONVERT_TOOL = new RecipeConvertTool.RecipeConvertToolSerializer();
        CRAFTING_CONVERT_TOOL.setRegistryName(new ResourceLocation(MODID, "convert_tool"));
        event.getRegistry().register(CRAFTING_CONVERT_TOOL);
    }

}
