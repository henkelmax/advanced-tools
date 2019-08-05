package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.ApplyEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.EnchantmentRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment.CombineEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.convert_enchantment.ConvertEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.EnchantmentRemoveRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.RemoveEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipeCategory;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.registration.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@mezz.jei.api.JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static final ResourceLocation CATEGORY_ENCHANT = new ResourceLocation(Main.MODID, "advancedtools.enchant");
    public static final ResourceLocation CATEGORY_UPGRADE = new ResourceLocation(Main.MODID, "advancedtools.upgrade");
    public static final ResourceLocation CATEGORY_REMOVE_ENCHANTING = new ResourceLocation(Main.MODID, "advancedtools.remove_enchanting");
    public static final ResourceLocation CATEGORY_BOOK_CONVERTING = new ResourceLocation(Main.MODID, "advancedtools.book_converting");
    public static final ResourceLocation CATEGORY_ENCHANTMENT_CONVERTING = new ResourceLocation(Main.MODID, "advancedtools.enchantment_converting");
    public static final ResourceLocation CATEGORY_ENCHANTMENT_COMBINING = new ResourceLocation(Main.MODID, "advancedtools.enchantment_combining");

    private static final ISubtypeInterpreter MATERIAL_SUBTYPE_INTERPRETER = itemStack -> StackUtils.getMaterial(itemStack).getName();

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModItems.PICKAXE, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.AXE, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.SHOVEL, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.SWORD, MATERIAL_SUBTYPE_INTERPRETER);
        registration.registerSubtypeInterpreter(ModItems.HOE, MATERIAL_SUBTYPE_INTERPRETER);
        registration.useNbtForSubtypes(ModItems.PICKAXE);
        registration.useNbtForSubtypes(ModItems.AXE);
        registration.useNbtForSubtypes(ModItems.SHOVEL);
        registration.useNbtForSubtypes(ModItems.SWORD);
        registration.useNbtForSubtypes(ModItems.HOE);
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
        List<EnchantmentRecipe> enchants = new ArrayList<>();
        for (AbstractTool tool : ModItems.getAllTools()) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                Iterator<Enchantment> i = ForgeRegistries.ENCHANTMENTS.iterator();
                while (i.hasNext()) {
                    Enchantment enchantment = i.next();
                    ItemStack stack = new ItemStack(tool);
                    ItemStack ench = new ItemStack(ModItems.ENCHANTMENT);
                    ModItems.ENCHANTMENT.setEnchantment(ench, enchantment, enchantment.getMaxLevel());
                    if (!StackUtils.isEmpty(tool.applyEnchantment(stack, ench))) {
                        enchants.add(new EnchantmentRecipe(enchantment, tool, material));
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
                    ItemStack stack = new ItemStack(tool);
                    StackUtils.addEnchantment(stack, enchantment, enchantment.getMaxLevel());
                    ItemStack ench = new ItemStack(ModItems.ENCHANTMENT_REMOVER);
                    ModItems.ENCHANTMENT_REMOVER.setEnchantment(ench, enchantment);
                    if (!StackUtils.isEmpty(tool.removeEnchantment(stack, ench))) {
                        remove.add(new EnchantmentRemoveRecipe(new EnchantmentData(enchantment, enchantment.getMaxLevel()), tool, material));
                    }
                }
            }
        }

        registry.addRecipes(remove, JEIPlugin.CATEGORY_REMOVE_ENCHANTING);


        //Convert book
        List<ConvertBookRecipe> conv = new ArrayList<>();
        Iterator<Enchantment> iterator = ForgeRegistries.ENCHANTMENTS.iterator();
        List<Enchantment> allEnchantments = new ArrayList<>();
        Random r = new Random();
        while (iterator.hasNext()) {
            allEnchantments.add(iterator.next());
        }

        for (Enchantment enchantment : allEnchantments) {
            EnchantmentData[] enchs = new EnchantmentData[r.nextInt(8) + 1];
            enchs[0] = new EnchantmentData(enchantment, enchantment.getMaxLevel());
            for (int i = 1; i < enchs.length; i++) {
                Enchantment re = allEnchantments.get(r.nextInt(allEnchantments.size()));
                enchs[i] = new EnchantmentData(re, re.getMaxLevel());
            }
            conv.add(new ConvertBookRecipe(enchs));
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
        List<Enchantment> enchantments = new ArrayList<>();
        Iterator<Enchantment> it = ForgeRegistries.ENCHANTMENTS.iterator();
        while (it.hasNext()) {
            Enchantment enchantment = it.next();
            if (enchantment.getMaxLevel() > 1) {
                enchantments.add(enchantment);
            }
        }

        registry.addRecipes(enchantments, JEIPlugin.CATEGORY_ENCHANTMENT_COMBINING);


        //Convert enchantment
        List<EnchantmentData> ed = new ArrayList<>();
        Iterator<Enchantment> enchIt = ForgeRegistries.ENCHANTMENTS.iterator();
        while (enchIt.hasNext()) {
            Enchantment e = enchIt.next();
            ed.add(new EnchantmentData(e, e.getMaxLevel()));
        }

        registry.addRecipes(ed, JEIPlugin.CATEGORY_ENCHANTMENT_CONVERTING);

        //Tool crafting
        List<ICraftingRecipe> recipes = new ArrayList<>();

        for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
            recipes.add(new ShapedToolRecipe(
                    createList(
                            material.getIngredient(), material.getIngredient(), material.getIngredient(),
                            null, Ingredient.fromItems(Items.STICK), null,
                            null, Ingredient.fromItems(Items.STICK), null
                    ),
                    ModItems.PICKAXE,
                    material
            ));
            recipes.add(new ShapedToolRecipe(
                    createList(
                            material.getIngredient(), material.getIngredient(), null,
                            material.getIngredient(), Ingredient.fromItems(Items.STICK), null,
                            null, Ingredient.fromItems(Items.STICK), null
                    ),
                    ModItems.AXE,
                    material
            ));
            recipes.add(new ShapedToolRecipe(
                    createList(
                            null, material.getIngredient(), null,
                            null, Ingredient.fromItems(Items.STICK), null,
                            null, Ingredient.fromItems(Items.STICK), null
                    ),
                    ModItems.SHOVEL,
                    material
            ));
            recipes.add(new ShapedToolRecipe(
                    createList(
                            null, material.getIngredient(), null,
                            null, material.getIngredient(), null,
                            null, Ingredient.fromItems(Items.STICK), null
                    ),
                    ModItems.SWORD,
                    material
            ));
            recipes.add(new ShapedToolRecipe(
                    createList(
                            material.getIngredient(), material.getIngredient(), null,
                            null, Ingredient.fromItems(Items.STICK), null,
                            null, Ingredient.fromItems(Items.STICK), null
                    ),
                    ModItems.HOE,
                    material
            ));
        }

        registry.addRecipes(recipes, VanillaRecipeCategoryUid.CRAFTING);
    }

    private class ShapedToolRecipe extends ShapedRecipe {
        public ShapedToolRecipe(NonNullList<Ingredient> recipeItemsIn, Item item, AdvancedToolMaterial material) {
            super(null, "", 3, 3, recipeItemsIn, StackUtils.setMaterial(new ItemStack(item), material));
        }
    }

    private static NonNullList createList(Ingredient... ingredients) {
        NonNullList list = NonNullList.create();
        for (Ingredient ingredient : ingredients) {
            if (ingredient == null) {
                list.add(Ingredient.EMPTY);
            } else {
                list.add(ingredient);
            }
        }
        return list;
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {
        /*
        IIngredientBlacklist blacklist = registration.getJeiHelpers().getIngredientBlacklist();
        blacklist.addIngredientToBlacklist(new ItemStack(Items.DIAMOND_PICKAXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.IRON_PICKAXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.GOLDEN_PICKAXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.STONE_PICKAXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.WOODEN_PICKAXE));

        blacklist.addIngredientToBlacklist(new ItemStack(Items.DIAMOND_AXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.IRON_AXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.GOLDEN_AXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.STONE_AXE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.WOODEN_AXE));

        blacklist.addIngredientToBlacklist(new ItemStack(Items.DIAMOND_SHOVEL));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.IRON_SHOVEL));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.GOLDEN_SHOVEL));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.STONE_SHOVEL));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.WOODEN_SHOVEL));

        blacklist.addIngredientToBlacklist(new ItemStack(Items.DIAMOND_SWORD));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.IRON_SWORD));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.GOLDEN_SWORD));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.STONE_SWORD));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.WOODEN_SWORD));

        blacklist.addIngredientToBlacklist(new ItemStack(Items.DIAMOND_HOE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.IRON_HOE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.GOLDEN_HOE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.STONE_HOE));
        blacklist.addIngredientToBlacklist(new ItemStack(Items.WOODEN_HOE));
        */
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ApplyEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new RemoveEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new UpgradeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ConvertBookRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CombineEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ConvertEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Main.MODID, "advancedtools");
    }
}
