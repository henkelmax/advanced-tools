package de.maxhenkel.advancedtools.crafting;

import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import de.maxhenkel.advancedtools.items.tools.StackUtils;
import de.maxhenkel.advancedtools.items.tools.AdvancedToolMaterial;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ReciepeRepairTool implements IRecipe {

    private ResourceLocation resourceLocation;

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return !getCraftingResult(inv).equals(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack tool = null;
        List<ItemStack> otherItems = new ArrayList<>();
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack.getItem() instanceof AbstractTool) {
                if (tool != null) {
                    return ItemStack.EMPTY;
                }
                tool = stack;
            } else {
                if (!StackUtils.isEmpty(stack)) {
                    otherItems.add(stack);
                }
            }
        }

        if (tool == null) {
            return ItemStack.EMPTY;
        }


        AdvancedToolMaterial fMaterial = null;
        int fCount = 0;

        for (AdvancedToolMaterial mat : AdvancedToolMaterial.getAll()) {
            AdvancedToolMaterial material = null;
            int count = 0;
            for (ItemStack stack : otherItems) {
                if (mat.getMatcher().isMaterial(stack)) {
                    if (material != null) {
                        if (!material.equals(mat)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    material = mat;
                    count++;
                } else {
                    break;
                }
            }
            if(material!=null){
                fMaterial=material;
                fCount=count;
                break;
            }
        }

        if (fMaterial == null) {
            return ItemStack.EMPTY;
        }

        return ((AbstractTool) tool.getItem()).repair(tool, fMaterial, fCount);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY; //TODO maybe fix
    }

    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        this.resourceLocation = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return resourceLocation;
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }
}
