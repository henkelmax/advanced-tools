package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.crafting.*;
import de.maxhenkel.advancedtools.render.AdvancedToolModel;
import de.maxhenkel.advancedtools.render.base.UniversalModel;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoaderRegistry;
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
        new Registry();

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
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new SwordEvents());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "items/pickaxe/empty_pickaxe"),
                new ResourceLocation(Main.MODID, "items/pickaxe/pickaxe_handle"),
                ModItems.PICKAXE)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "items/axe/empty_axe"),
                new ResourceLocation(Main.MODID, "items/axe/axe_handle"),
                ModItems.AXE)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "items/shovel/empty_shovel"),
                new ResourceLocation(Main.MODID, "items/shovel/shovel_handle"),
                ModItems.SHOVEL)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "items/sword/empty_sword"),
                new ResourceLocation(Main.MODID, "items/sword/sword_handle"),
                ModItems.SWORD)));

        ModelLoaderRegistry.registerLoader(new UniversalModel(new AdvancedToolModel(
                new ResourceLocation(Main.MODID, "items/hoe/empty_hoe"),
                new ResourceLocation(Main.MODID, "items/hoe/hoe_handle"),
                ModItems.HOE)));
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
    public static IRecipeSerializer CRAFTING_VOID;

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CRAFTING_COMBINE_ENCHANTMENTS = new SpecialRecipeSerializer<>(ReciepeCombineEnchantments::new);
        CRAFTING_COMBINE_ENCHANTMENTS.setRegistryName(new ResourceLocation(MODID, "crafting_special_combine_enchantments"));
        event.getRegistry().register(CRAFTING_COMBINE_ENCHANTMENTS);

        CRAFTING_CONVERT_BOOK = new SpecialRecipeSerializer<>(ReciepeConvertBook::new);
        CRAFTING_CONVERT_BOOK.setRegistryName(new ResourceLocation(MODID, "crafting_special_convert_book"));
        event.getRegistry().register(CRAFTING_CONVERT_BOOK);

        CRAFTING_CONVERT_ENCHANTMENT = new SpecialRecipeSerializer<>(ReciepeConvertEnchantment::new);
        CRAFTING_CONVERT_ENCHANTMENT.setRegistryName(new ResourceLocation(MODID, "crafting_special_convert_enchantment"));
        event.getRegistry().register(CRAFTING_CONVERT_ENCHANTMENT);

        CRAFTING_ENCHANT_TOOL = new SpecialRecipeSerializer<>(ReciepeEnchantTool::new);
        CRAFTING_ENCHANT_TOOL.setRegistryName(new ResourceLocation(MODID, "crafting_special_enchant_tool"));
        event.getRegistry().register(CRAFTING_ENCHANT_TOOL);

        CRAFTING_EREMOVE_ENCHANTMENT = new SpecialRecipeSerializer<>(ReciepeRemoveEnchantment::new);
        CRAFTING_EREMOVE_ENCHANTMENT.setRegistryName(new ResourceLocation(MODID, "crafting_special_remove_enchantment"));
        event.getRegistry().register(CRAFTING_EREMOVE_ENCHANTMENT);

        CRAFTING_REPAIR_TOOL = new SpecialRecipeSerializer<>(ReciepeRepairTool::new);
        CRAFTING_REPAIR_TOOL.setRegistryName(new ResourceLocation(MODID, "crafting_special_repair_tool"));
        event.getRegistry().register(CRAFTING_REPAIR_TOOL);

        CRAFTING_VOID = new SpecialRecipeSerializer<>(ReciepeVoid::new);
        CRAFTING_VOID.setRegistryName(new ResourceLocation(MODID, "crafting_special_void"));
        event.getRegistry().register(CRAFTING_VOID);
    }

}
