package de.maxhenkel.advancedtools.items.tools;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.matcher.MaterialMatcher;
import de.maxhenkel.advancedtools.items.tools.matcher.OredictMatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

public class ToolMaterial {

    private static Map<String, ToolMaterial> materials = new HashMap<>();

    public static final MaterialMatcher MATCHER_DIAMOND = new OredictMatcher("gemDiamond");
    public static final MaterialMatcher MATCHER_IRON = new OredictMatcher("ingotIron");
    public static final MaterialMatcher MATCHER_GOLD = new OredictMatcher("ingotGold");
    public static final MaterialMatcher MATCHER_WOOD = new OredictMatcher("plankWood");
    public static final MaterialMatcher MATCHER_STONE = new OredictMatcher("cobblestone");

    public static final ToolMaterial DIAMOND;
    public static final ToolMaterial IRON;
    public static final ToolMaterial GOLD;
    public static final ToolMaterial WOOD;
    public static final ToolMaterial STONE;

    static {
        Map<String, ResourceLocation> diaTextures = new HashMap<>();
        diaTextures.put("pickaxe", new ResourceLocation(Main.MODID, "items/pickaxe/diamond_pickaxe_head"));
        DIAMOND=new ToolMaterial("diamond", 8F, 3F, -2.8F, 3, 2048, MATCHER_DIAMOND, diaTextures);
        materials.put("diamond", DIAMOND);

        Map<String, ResourceLocation> ironTextures = new HashMap<>();
        ironTextures.put("pickaxe", new ResourceLocation(Main.MODID, "items/pickaxe/iron_pickaxe_head"));
        IRON=new ToolMaterial("iron", 12F, 2F, -2.8F, 2, 256, MATCHER_IRON, ironTextures);
        materials.put("iron", IRON);

        Map<String, ResourceLocation> goldTextures = new HashMap<>();
        goldTextures.put("pickaxe", new ResourceLocation(Main.MODID, "items/pickaxe/gold_pickaxe_head"));
        GOLD=new ToolMaterial("gold", 6F, 0F, -2.8F, 0, 32, MATCHER_GOLD, goldTextures);
        materials.put("gold", GOLD);

        Map<String, ResourceLocation> stoneTextures = new HashMap<>();
        stoneTextures.put("pickaxe", new ResourceLocation(Main.MODID, "items/pickaxe/stone_pickaxe_head"));
        STONE=new ToolMaterial("stone", 4F, 1F, -2.8F, 1, 128, MATCHER_STONE, stoneTextures);
        materials.put("stone", STONE);

        Map<String, ResourceLocation> woodTextures = new HashMap<>();
        woodTextures.put("pickaxe", new ResourceLocation(Main.MODID, "items/pickaxe/wood_pickaxe_head"));
        WOOD=new ToolMaterial("wood", 2F, 0F, -2.8F, 0, 64, MATCHER_WOOD, woodTextures);
        materials.put("wood", WOOD);
    }

    private String name;
    private float efficiency;
    private float attackModifier;
    private float attackSpeedModifier;
    private int harvestLevel;
    private int maxDamage;
    private MaterialMatcher matcher;
    private Map<String, ResourceLocation> partTextures;

    public ToolMaterial(String name, float efficiency, float attackModifier, float attackSpeedModifier, int harvestLevel, int maxDamage, MaterialMatcher matcher, Map<String, ResourceLocation> partTextures) {
        this.name = name;
        this.efficiency = efficiency;
        this.attackModifier = attackModifier;
        this.attackSpeedModifier = attackSpeedModifier;
        this.harvestLevel = harvestLevel;
        this.maxDamage = maxDamage;
        this.matcher = matcher;
        this.partTextures = partTextures;
    }

    public MaterialMatcher getMatcher() {
        return matcher;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public float getAttackModifier() {
        return attackModifier;
    }

    public float getAttackSpeedModifier() {
        return attackSpeedModifier;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public String getName() {
        return name;
    }

    public String getLocalizedName(){
        return new TextComponentTranslation("material." +name +".name").getUnformattedText();
    }

    public Map<String, ResourceLocation> getPartTextures() {
        return Collections.unmodifiableMap(partTextures);
    }

    public ResourceLocation getTextureFor(String tool) {
        if (tool == null) {
            return null;
        }

        return partTextures.get(tool);
    }

    public static ToolMaterial byName(String name) {
        if (name == null) {
            return null;
        }
        return materials.get(name);
    }

    public static Collection<ToolMaterial> getAll() {
        return Collections.unmodifiableCollection(materials.values());
    }

}
