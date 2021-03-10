package de.maxhenkel.advancedtools.crafting;

import com.google.gson.JsonObject;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
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

public class RecipeToolMaterial implements ICraftingRecipe, net.minecraftforge.common.crafting.IShapedRecipe<CraftingInventory> {

    private ShapedRecipe recipe;
    private AdvancedToolMaterial material;

    public RecipeToolMaterial(ShapedRecipe recipe, AdvancedToolMaterial material) {
        this.recipe = recipe;
        this.material = material;
    }

    @Override
    public int getRecipeWidth() {
        return recipe.getRecipeWidth();
    }

    @Override
    public int getRecipeHeight() {
        return recipe.getRecipeHeight();
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        return recipe.matches(inv, worldIn);
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        return StackUtils.setMaterial(recipe.assemble(inv), material);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return recipe.canCraftInDimensions(width, height);
    }

    @Override
    public ItemStack getResultItem() {
        return StackUtils.setMaterial(recipe.getResultItem(), material);
    }

    @Override
    public ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Main.CRAFTING_TOOL_MATERIAL;
    }

    public AdvancedToolMaterial getMaterial() {
        return material;
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }

    public static class RecipeToolMaterialSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<RecipeToolMaterial> {

        private ShapedRecipe.Serializer serializer;

        public RecipeToolMaterialSerializer() {
            serializer = new ShapedRecipe.Serializer();
        }

        @Override
        public RecipeToolMaterial fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return new RecipeToolMaterial(serializer.fromJson(resourceLocation, jsonObject), AdvancedToolMaterial.byName(jsonObject.get("result").getAsJsonObject().get("material").getAsString()));
        }

        @Override
        public RecipeToolMaterial fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
            String material = packetBuffer.readUtf();
            return new RecipeToolMaterial(serializer.fromNetwork(resourceLocation, packetBuffer), AdvancedToolMaterial.byName(material));
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, RecipeToolMaterial recipe) {
            packetBuffer.writeUtf(recipe.getMaterial().getName());
            serializer.toNetwork(packetBuffer, recipe.getRecipe());
        }
    }
}
