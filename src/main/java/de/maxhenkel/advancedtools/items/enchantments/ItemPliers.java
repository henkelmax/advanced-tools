package de.maxhenkel.advancedtools.items.enchantments;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModCreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPliers extends Item {

    public ItemPliers() {
        super(new Properties().maxDamage(10).group(ModCreativeTabs.TAB_ADVANCED_TOOLS));
        setRegistryName(new ResourceLocation(Main.MODID, "pliers"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.pliers").func_240699_a_(TextFormatting.GRAY));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

}
