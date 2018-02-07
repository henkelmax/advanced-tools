package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.ApplyEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.ApplyEnchantmentRecipeWrapper;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.ApplyEnchantmentRecipeWrapperFactory;
import de.maxhenkel.advancedtools.integration.jei.category.apply_enchantment.EnchantmentRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment.CombineEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment.CombineEnchantmentRecipeWrapper;
import de.maxhenkel.advancedtools.integration.jei.category.combine_enchantment.CombineEnchantmentRecipeWrapperFactory;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipeWrapper;
import de.maxhenkel.advancedtools.integration.jei.category.convert_book.ConvertBookRecipeWrapperFactory;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.EnchantmentRemoveRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.RemoveEnchantmentRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.RemoveEnchantmentRecipeWrapper;
import de.maxhenkel.advancedtools.integration.jei.category.remove_enchantment.RemoveEnchantmentRecipeWrapperFactory;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipe;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipeCategory;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipeWrapper;
import de.maxhenkel.advancedtools.integration.jei.category.upgrade.UpgradeRecipeWrapperFactory;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.EnchantmentTools;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static final String CATEGORY_ENCHANT = "advancedtools.enchant";
    public static final String CATEGORY_UPGRADE = "advancedtools.upgrade";
    public static final String CATEGORY_REMOVE_ENCHANTING = "advancedtools.remove_enchanting";
    public static final String CATEGORY_BOOK_CONVERTING = "advancedtools.book_converting";
    public static final String CATEGORY_ENCHANTMENT_COMBINING = "advancedtools.enchantment_combining";

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {

    }

    @Override
    public void register(IModRegistry registry) {

        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
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

        registry.handleRecipes(EnchantmentRecipe.class, new ApplyEnchantmentRecipeWrapperFactory(), JEIPlugin.CATEGORY_ENCHANT);
        registry.handleRecipes(EnchantmentRemoveRecipe.class, new RemoveEnchantmentRecipeWrapperFactory(), JEIPlugin.CATEGORY_REMOVE_ENCHANTING);
        registry.handleRecipes(UpgradeRecipe.class, new UpgradeRecipeWrapperFactory(), JEIPlugin.CATEGORY_UPGRADE);
        registry.handleRecipes(ConvertBookRecipe.class, new ConvertBookRecipeWrapperFactory(), JEIPlugin.CATEGORY_BOOK_CONVERTING);
        registry.handleRecipes(Enchantment.class, new CombineEnchantmentRecipeWrapperFactory(), JEIPlugin.CATEGORY_ENCHANTMENT_COMBINING);

        //Enchant
        List<ApplyEnchantmentRecipeWrapper> enchants = new ArrayList<ApplyEnchantmentRecipeWrapper>();
        for (AbstractTool tool : AdvancedToolMaterial.TOOLS) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                Iterator<Enchantment> i = Enchantment.REGISTRY.iterator();
                while (i.hasNext()) {
                    Enchantment enchantment = i.next();
                    ItemStack stack = new ItemStack(tool);
                    ItemStack ench = new ItemStack(ModItems.ENCHANTMENT);
                    ModItems.ENCHANTMENT.setEnchantment(ench, enchantment, enchantment.getMaxLevel());
                    if (!StackUtils.isEmpty(tool.applyEnchantment(stack, ench))) {
                        enchants.add(new ApplyEnchantmentRecipeWrapper(new EnchantmentRecipe(enchantment, tool, material)));
                    }
                }
            }
        }

        registry.addRecipes(enchants, JEIPlugin.CATEGORY_ENCHANT);
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT), JEIPlugin.CATEGORY_ENCHANT);

        //Remove ench
        List<RemoveEnchantmentRecipeWrapper> remove = new ArrayList<RemoveEnchantmentRecipeWrapper>();
        for (AbstractTool tool : AdvancedToolMaterial.TOOLS) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                Iterator<Enchantment> i = Enchantment.REGISTRY.iterator();
                while (i.hasNext()) {
                    Enchantment enchantment = i.next();
                    ItemStack stack = new ItemStack(tool);
                    StackUtils.addEnchantment(stack, enchantment, enchantment.getMaxLevel());
                    ItemStack ench = new ItemStack(ModItems.ENCHANTMENT_REMOVER);
                    ModItems.ENCHANTMENT_REMOVER.setEnchantment(ench, enchantment);
                    if (!StackUtils.isEmpty(tool.removeEnchantment(stack, ench))) {
                        remove.add(new RemoveEnchantmentRecipeWrapper(new EnchantmentRemoveRecipe(enchantment, tool, material)));
                    }
                }
            }
        }

        registry.addRecipes(remove, JEIPlugin.CATEGORY_REMOVE_ENCHANTING);
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT_REMOVER), JEIPlugin.CATEGORY_REMOVE_ENCHANTING);

        //Convert book
        List<ConvertBookRecipeWrapper> conv = new ArrayList<ConvertBookRecipeWrapper>();
        Iterator<Enchantment> iterator = Enchantment.REGISTRY.iterator();
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
            conv.add(new ConvertBookRecipeWrapper(new ConvertBookRecipe(enchs)));
        }

        registry.addRecipes(conv, JEIPlugin.CATEGORY_BOOK_CONVERTING);
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT), JEIPlugin.CATEGORY_BOOK_CONVERTING);

        //Upgrade
        List<UpgradeRecipeWrapper> upgrades = new ArrayList<UpgradeRecipeWrapper>();
        for (AbstractTool tool : AdvancedToolMaterial.TOOLS) {
            for (AdvancedToolMaterial material : AdvancedToolMaterial.getAll()) {
                for (AdvancedToolMaterial material1 : AdvancedToolMaterial.getAll()) {
                    if (!material.equals(material1)) {
                        upgrades.add(new UpgradeRecipeWrapper(new UpgradeRecipe(tool, material, material1)));
                    }
                }
            }
        }

        registry.addRecipes(upgrades, JEIPlugin.CATEGORY_UPGRADE);
        for (AbstractTool tool : AdvancedToolMaterial.TOOLS) {
            ItemStack stack = new ItemStack(tool);
            StackUtils.setMaterial(stack, AdvancedToolMaterial.DIAMOND);
            registry.addRecipeCatalyst(stack, JEIPlugin.CATEGORY_UPGRADE);
        }

        //Combine
        List<CombineEnchantmentRecipeWrapper> enchantments = new ArrayList<CombineEnchantmentRecipeWrapper>();
        Iterator<Enchantment> it = Enchantment.REGISTRY.iterator();
        while (it.hasNext()) {
            Enchantment enchantment=it.next();
            if (enchantment.getMaxLevel()>1) {
                enchantments.add(new CombineEnchantmentRecipeWrapper(enchantment));
            }
        }

        registry.addRecipes(enchantments, JEIPlugin.CATEGORY_ENCHANTMENT_COMBINING);
        registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT), JEIPlugin.CATEGORY_ENCHANTMENT_COMBINING);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration reg) {

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry reg) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ApplyEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new RemoveEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new UpgradeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ConvertBookRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CombineEnchantmentRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

}
