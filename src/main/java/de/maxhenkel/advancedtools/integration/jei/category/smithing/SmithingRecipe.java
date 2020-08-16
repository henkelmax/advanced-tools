package de.maxhenkel.advancedtools.integration.jei.category.smithing;

import net.minecraft.item.ItemStack;

public class SmithingRecipe {

    private ItemStack input;
    private ItemStack material;
    private ItemStack output;

    public SmithingRecipe(ItemStack input, ItemStack material, ItemStack output) {
        this.input = input;
        this.material = material;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getMaterial() {
        return material;
    }

    public ItemStack getOutput() {
        return output;
    }

}