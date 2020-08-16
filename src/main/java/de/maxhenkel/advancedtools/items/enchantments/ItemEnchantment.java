package de.maxhenkel.advancedtools.items.enchantments;

import de.maxhenkel.advancedtools.Main;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemEnchantment extends ItemEnchantmentBase {

    public ItemEnchantment() {
        setRegistryName(new ResourceLocation(Main.MODID, "enchantment"));
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        EnchantmentData data = getEnchantment(stack);
        if (data == null) {
            return new TranslationTextComponent("enchantment.empty");
        }
        return super.getDisplayName(stack);
    }

}
