package de.maxhenkel.advancedtools.integration.jei;

import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.ToolMaterial;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

	public static final String CATEGORY_ENCHANT = "advancedtools.enchant";
    public static final String CATEGORY_UPGRADE = "advancedtools.upgrade";

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


		registry.handleRecipes(EnchantmentRecipe.class, new ApplyEnchantmentRecipeWrapperFactory(), JEIPlugin.CATEGORY_ENCHANT);

        AbstractTool[] tools=new AbstractTool[]{ModItems.PICKAXE};

		List<ApplyEnchantmentRecipeWrapper> enchants = new ArrayList<ApplyEnchantmentRecipeWrapper>();
		for(AbstractTool tool:tools){
            for (ToolMaterial material:ToolMaterial.getAll()) {
                Iterator<Enchantment> i=Enchantment.REGISTRY.iterator();
                while(i.hasNext()){
                    Enchantment enchantment=i.next();
                    ItemStack stack=new ItemStack(tool);
                    ItemStack ench=new ItemStack(ModItems.ENCHANTMENT);
                    ModItems.ENCHANTMENT.setEnchantment(ench, enchantment, enchantment.getMaxLevel());
                    if(!StackUtils.isEmpty(tool.applyEnchantment(stack, ench))){
                        enchants.add(new ApplyEnchantmentRecipeWrapper(new EnchantmentRecipe(enchantment, tool, material)));
                    }
                }
            }
        }

		registry.addRecipes(enchants, JEIPlugin.CATEGORY_ENCHANT);
		registry.addRecipeCatalyst(new ItemStack(ModItems.ENCHANTMENT), JEIPlugin.CATEGORY_ENCHANT);

		//Upgrade
        List<UpgradeRecipeWrapper> upgrades = new ArrayList<UpgradeRecipeWrapper>();
        for(AbstractTool tool:tools) {
            for (ToolMaterial material : ToolMaterial.getAll()) {
                for (ToolMaterial material1 : ToolMaterial.getAll()) {
                    if(!material.equals(material1)){
                        upgrades.add(new UpgradeRecipeWrapper(new UpgradeRecipe(tool, material, material1)));
                    }
                }
            }
        }

        registry.addRecipes(upgrades, JEIPlugin.CATEGORY_UPGRADE);
        for(AbstractTool tool:tools){
            registry.addRecipeCatalyst(new ItemStack(tool), JEIPlugin.CATEGORY_UPGRADE);
        }

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
        registry.addRecipeCategories(new UpgradeRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

}
