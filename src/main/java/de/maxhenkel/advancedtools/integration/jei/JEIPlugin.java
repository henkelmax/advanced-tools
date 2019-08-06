package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.crafting.RecipeConvertTool;
import de.maxhenkel.advancedtools.crafting.RecipeToolMaterial;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.ApplyEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.EnchantmentRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment.CombineEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.convert_enchantment.ConvertEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.EnchantmentRemoveRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.RemoveEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.split_enchantment.SplitEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipeCategory;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.recipe.category.extensions.IExtendableRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

@mezz.jei.api.JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static final ResourceLocation CATEGORY_ENCHANT = new ResourceLocation(Main.MODID, "advancedtools.enchant");
    public static final ResourceLocation CATEGORY_UPGRADE = new ResourceLocation(Main.MODID, "advancedtools.upgrade");
    public static final ResourceLocation CATEGORY_REMOVE_ENCHANTING = new ResourceLocation(Main.MODID, "advancedtools.remove_enchanting");
    public static final ResourceLocation CATEGORY_BOOK_CONVERTING = new ResourceLocation(Main.MODID, "advancedtools.book_converting");
    public static final ResourceLocation CATEGORY_ENCHANTMENT_CONVERTING = new ResourceLocation(Main.MODID, "advancedtools.enchantment_converting");
    public static final ResourceLocation CATEGORY_ENCHANTMENT_COMBINING = new ResourceLocation(Main.MODID, "advancedtools.enchantment_combining");
    public static final ResourceLocation CATEGORY_ENCHANTMENT_SPLITTING = new ResourceLocation(Main.MODID, "advancedtools.enchantment_splitting");

    private static final ISubtypeInterpreter MATERIAL_SUBTYPE_INTERPRETER = itemStack -> StackUtils.getMaterial(itemStack).getName();

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModItems.PICKAXE, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.AXE, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.SHOVEL, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.SWORD, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.HOE, MATERIAL_SUBTYPE_INTERPRETER);

        registration.registerSubtypeInterpreter(ModItems.ENCHANTMENT, itemStack -> {
            EnchantmentData data = ModItems.ENCHANTMENT.getEnchantment(itemStack);
            if (data == null) {
                return "";
            }
            return data.enchantment.getRegistryName().toString() + ":" + data.enchantmentLevel;
        });
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT), JEIPlugin.CATEGORY_ENCHANT);
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT_REMOVER), JEIPlugin.CATEGORY_REMOVE_ENCHANTING);
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT), JEIPlugin.CATEGORY_BOOK_CONVERTING);
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT), JEIPlugin.CATEGORY_ENCHANTMENT_COMBINING);
        registry.addRecipeCatalyst(new ItemStack(Items.ENCHANTED_BOOK), JEIPlugin.CATEGORY_ENCHANTMENT_CONVERTING);

        for (AbstractTool tool : ModItems.getAllTools()) {
            ItemStack stack = new ItemStack(tool);
            StackUtils.setMaterial(stack, AdvancedToolMaterial.DIAMOND);
            registry.addRecipeCatalyst(stack, JEIPlugin.CATEGORY_UPGRADE);
        }

    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        Iterator<Enchantment> iterator = ForgeRegistries.ENCHANTMENTS.iterator();
        List<Enchantment> allEnchantments = new ArrayList<>();
        Random r = new Random();
        while (iterator.hasNext()) {
            allEnchantments.add(iterator.next());
        }

        // Enchant
        List<EnchantmentRecipe> enchants = new ArrayList<>();
        for (AbstractTool tool : ModItems.getAllTools()) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                Iterator<Enchantment> i = ForgeRegistries.ENCHANTMENTS.iterator();
                while (i.hasNext()) {
                    Enchantment enchantment = i.next();
                    for (int j = 1; j <= enchantment.getMaxLevel(); j++) {
                        ItemStack stack = new ItemStack(tool);
                        ItemStack ench = new ItemStack(ModItems.ENCHANTMENT);
                        ModItems.ENCHANTMENT.setEnchantment(ench, enchantment, j);
                        if (!tool.applyEnchantment(stack, ench).isEmpty()) {
                            enchants.add(new EnchantmentRecipe(new EnchantmentData(enchantment, j), tool, material));
                        }
                    }

                }
            }
        }

        registry.addRecipes(enchants, JEIPlugin.CATEGORY_ENCHANT);


        //Remove ench
        List<EnchantmentRemoveRecipe> remove = new ArrayList<>();
        for (AbstractTool tool : ModItems.getAllTools()) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                Iterator<Enchantment> i = ForgeRegistries.ENCHANTMENTS.iterator();
                while (i.hasNext()) {
                    Enchantment enchantment = i.next();
                    for (int j = 1; j <= enchantment.getMaxLevel(); j++) {
                        ItemStack stack = new ItemStack(tool);
                        StackUtils.addEnchantment(stack, enchantment, j);
                        ItemStack ench = new ItemStack(ModItems.ENCHANTMENT_REMOVER);
                        ModItems.ENCHANTMENT_REMOVER.setEnchantment(ench, enchantment);
                        if (!tool.removeEnchantment(stack, ench).isEmpty()) {
                            remove.add(new EnchantmentRemoveRecipe(new EnchantmentData(enchantment, enchantment.getMaxLevel()), tool, material));
                        }
                    }
                }
            }
        }

        registry.addRecipes(remove, JEIPlugin.CATEGORY_REMOVE_ENCHANTING);


        //Convert book
        List<ConvertBookRecipe> conv = new ArrayList<>();

        for (Enchantment enchantment : allEnchantments) {
            for (int j = 1; j <= enchantment.getMaxLevel(); j++) {
                EnchantmentData[] enchs = new EnchantmentData[r.nextInt(8) + 1];
                enchs[0] = new EnchantmentData(enchantment, j);
                for (int i = 1; i < enchs.length; i++) {
                    Enchantment re = allEnchantments.get(r.nextInt(allEnchantments.size()));
                    enchs[i] = new EnchantmentData(re, r.nextInt(re.getMaxLevel()) + 1);
                }
                conv.add(new ConvertBookRecipe(enchs));
            }
        }

        registry.addRecipes(conv, JEIPlugin.CATEGORY_BOOK_CONVERTING);


        //Upgrade
        List<UpgradeRecipe> upgrades = new ArrayList<>();
        for (AbstractTool tool : ModItems.getAllTools()) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                for (AdvancedToolMaterial material1 : AdvancedToolMaterial.getAll()) {
                    if (!material.equals(material1)) {
                        upgrades.add(new UpgradeRecipe(tool, material, material1));
                    }
                }
            }
        }

        registry.addRecipes(upgrades, JEIPlugin.CATEGORY_UPGRADE);


        //Combine
        List<EnchantmentData> enchantments = new ArrayList<>();
        Iterator<Enchantment> it = ForgeRegistries.ENCHANTMENTS.iterator();
        while (it.hasNext()) {
            Enchantment enchantment = it.next();
            if (enchantment.getMaxLevel() >= 2) {
                for (int j = 2; j <= enchantment.getMaxLevel(); j++) {
                    enchantments.add(new EnchantmentData(enchantment, j));
                }
            }
        }

        registry.addRecipes(enchantments, JEIPlugin.CATEGORY_ENCHANTMENT_COMBINING);


        //Split
        List<EnchantmentData> splitEnchantments = new ArrayList<>();
        Iterator<Enchantment> it1 = ForgeRegistries.ENCHANTMENTS.iterator();
        while (it1.hasNext()) {
            Enchantment enchantment = it1.next();
            for (int i = 2; i <= enchantment.getMaxLevel(); i++) {
                splitEnchantments.add(new EnchantmentData(enchantment, i));
            }
        }

        registry.addRecipes(splitEnchantments, JEIPlugin.CATEGORY_ENCHANTMENT_SPLITTING);


        //Convert enchantment
        List<EnchantmentData> ed = new ArrayList<>();
        Iterator<Enchantment> enchIt = ForgeRegistries.ENCHANTMENTS.iterator();
        while (enchIt.hasNext()) {
            Enchantment enchantment = enchIt.next();
            for (int j = 1; j <= enchantment.getMaxLevel(); j++) {
                ed.add(new EnchantmentData(enchantment, j));
            }
        }

        registry.addRecipes(ed, JEIPlugin.CATEGORY_ENCHANTMENT_CONVERTING);
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        IExtendableRecipeCategory<ICraftingRecipe, ICraftingCategoryExtension> craftingCategory = registration.getCraftingCategory();
        craftingCategory.addCategoryExtension(RecipeConvertTool.class, ConvertToolExtension::new);
        craftingCategory.addCategoryExtension(RecipeToolMaterial.class, CraftToolExtension::new);
    }

    private <T extends IRecipe> List<T> getAllRecipes(Class<T> clazz) {
        List<T> convertRecipes = new ArrayList<>();
        ClientWorld world = Minecraft.getInstance().world;
        RecipeManager recipeManager = world.getRecipeManager();
        Collection<IRecipe<?>> recipes = recipeManager.getRecipes();
        for (IRecipe recipe : recipes) {
            if (recipe.getClass().isAssignableFrom(clazz)) {
                convertRecipes.add((T) recipe);
            }
        }
        return convertRecipes;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ApplyEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new RemoveEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new UpgradeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ConvertBookRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CombineEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SplitEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ConvertEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Main.MODID, "advancedtools");
    }
}
