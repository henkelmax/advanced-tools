package de.maxhenkel.advancedtools.items.tools.matcher;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class OredictMatcher implements MaterialMatcher{

    private String oredict;

    public OredictMatcher(String oredict){
        this.oredict=oredict;
    }

    @Override
    public boolean isMaterial(ItemStack stack) {
        for(ItemStack s:OreDictionary.getOres(oredict)){
            if(OreDictionary.itemMatches(s, stack, false)){
                return true;
            }
        }
        return false;
        //return OreDictionary.containsMatch(false, OreDictionary.getOres(oredict), stack);
    }

    @Override
    public List<ItemStack> getAll() {
        return OreDictionary.getOres(oredict);
    }

}
