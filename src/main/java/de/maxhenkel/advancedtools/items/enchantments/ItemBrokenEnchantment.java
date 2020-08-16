package de.maxhenkel.advancedtools.items.enchantments;

import de.maxhenkel.advancedtools.Main;
import net.minecraft.util.ResourceLocation;

public class ItemBrokenEnchantment extends ItemEnchantmentBase {

    public ItemBrokenEnchantment() {
        setRegistryName(new ResourceLocation(Main.MODID, "broken_enchantment"));
    }

}
