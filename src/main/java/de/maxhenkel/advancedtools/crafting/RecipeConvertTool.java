package de.maxhenkel.advancedtools.crafting;

import com.google.gson.JsonObject;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeConvertTool implements ICraftingRecipe, net.minecraftforge.common.crafting.IShapedRecipe<CraftingInventory> {

    private ResourceLocation id;
    private Ingredient inputTool;
    private ItemStack outputTool;
    private AdvancedToolMaterial outputMaterial;

    public RecipeConvertTool(ResourceLocation id, Ingredient inputTool, ItemStack outputTool, AdvancedToolMaterial outputMaterial) {
        this.id = id;
        this.inputTool = inputTool;
        this.outputTool = outputTool;
        this.outputMaterial = outputMaterial;
    }

    @Override
    public int getRecipeWidth() {
        return 1;
    }

    @Override
    public int getRecipeHeight() {
        return 1;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, getInputTool());
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean found = false;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (!inv.getItem(i).isEmpty()) {
                if (found) {
                    return false;
                } else if (inputTool.test(inv.getItem(i))) {
                    found = true;
                }
            }
        }
        return found;
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        ItemStack input = getInputItem(inv);
        return getCraftingResult(input);
    }

    public ItemStack getCraftingResult(ItemStack input) {
        float percentage = (float) input.getDamageValue() / (float) input.getMaxDamage();

        ItemStack output = getResultItem().copy();

        output.setDamageValue((int) ((float) output.getMaxDamage() * percentage));

        EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(input), output);

        return output;
    }

    private ItemStack getInputItem(CraftingInventory inv) {
        for (int i = 0; i < inv.getContainerSize(); i++) {
            if (!inv.getItem(i).isEmpty()) {
                if (inputTool.test(inv.getItem(i))) {
                    return inv.getItem(i);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 1 && height >= 1;
    }

    @Override
    public ItemStack getResultItem() {
        return StackUtils.setMaterial(outputTool, outputMaterial);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public Ingredient getInputTool() {
        return inputTool;
    }

    public ItemStack getOutputTool() {
        return outputTool;
    }

    public AdvancedToolMaterial getOutputMaterial() {
        return outputMaterial;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_CONVERT_TOOL;
    }


    public static class RecipeConvertToolSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeConvertTool> {

        public RecipeConvertToolSerializer() {

        }

        @Override
        public RecipeConvertTool fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return new RecipeConvertTool(resourceLocation, Ingredient.fromJson(jsonObject.get("input")), ShapedRecipe.itemFromJson(jsonObject.getAsJsonObject("result")), AdvancedToolMaterial.byName(jsonObject.getAsJsonObject("result").get("material").getAsString()));
        }

        @Override
        public RecipeConvertTool fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            return new RecipeConvertTool(packetBuffer.readResourceLocation(), Ingredient.fromNetwork(packetBuffer), packetBuffer.readItem(), AdvancedToolMaterial.byName(packetBuffer.readUtf()));
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, RecipeConvertTool recipe) {
            packetBuffer.writeResourceLocation(recipe.getId());
            recipe.getInputTool().toNetwork(packetBuffer);
            packetBuffer.writeItem(recipe.getOutputTool());
            packetBuffer.writeUtf(recipe.getOutputMaterial().getName());
        }
    }
}
