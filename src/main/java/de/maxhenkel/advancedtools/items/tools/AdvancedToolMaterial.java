package de.maxhenkel.advancedtools.items.tools;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.ModItems;
import de.maxhenkel.advancedtools.items.tools.matcher.MaterialMatcher;
import de.maxhenkel.advancedtools.items.tools.matcher.OredictMatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import java.util.*;

public class AdvancedToolMaterial {

    public static final String PICKAXE = "pickaxe";
    public static final String AXE = "axe";
    public static final String SWORD = "sword";
    public static final String SHOVEL = "shovel";
    public static final String HOE = "hoe";

    public static final ImmutableList<AbstractTool> TOOLS = ImmutableList.of(ModItems.PICKAXE, ModItems.AXE, ModItems.SWORD, ModItems.SHOVEL, ModItems.HOE);

    private static Map<String, AdvancedToolMaterial> materials = new HashMap<>();

    public static final MaterialMatcher MATCHER_DIAMOND = new OredictMatcher("gemDiamond");
    public static final MaterialMatcher MATCHER_IRON = new OredictMatcher("ingotIron");
    public static final MaterialMatcher MATCHER_GOLD = new OredictMatcher("ingotGold");
    public static final MaterialMatcher MATCHER_WOOD = new OredictMatcher("plankWood");
    public static final MaterialMatcher MATCHER_STONE = new OredictMatcher("cobblestone");

    public static final AdvancedToolMaterial DIAMOND;
    public static final AdvancedToolMaterial IRON;
    public static final AdvancedToolMaterial GOLD;
    public static final AdvancedToolMaterial WOOD;
    public static final AdvancedToolMaterial STONE;

    static {
        Map<String, ResourceLocation> diaTextures = new HashMap<>();
        diaTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "items/pickaxe/diamond_pickaxe_head"));
        diaTextures.put(AXE, new ResourceLocation(Main.MODID, "items/axe/diamond_axe_head"));
        diaTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "items/shovel/diamond_shovel_head"));
        diaTextures.put(SWORD, new ResourceLocation(Main.MODID, "items/sword/diamond_sword_head"));
        diaTextures.put(HOE, new ResourceLocation(Main.MODID, "items/hoe/diamond_hoe_head"));
        DIAMOND = new AdvancedToolMaterial("diamond",
                8F, 3F, 3, 2048, MATCHER_DIAMOND, diaTextures);
        materials.put("diamond", DIAMOND);

        Map<String, ResourceLocation> ironTextures = new HashMap<>();
        ironTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "items/pickaxe/iron_pickaxe_head"));
        ironTextures.put(AXE, new ResourceLocation(Main.MODID, "items/axe/iron_axe_head"));
        ironTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "items/shovel/iron_shovel_head"));
        ironTextures.put(SWORD, new ResourceLocation(Main.MODID, "items/sword/iron_sword_head"));
        ironTextures.put(HOE, new ResourceLocation(Main.MODID, "items/hoe/iron_hoe_head"));
        IRON = new AdvancedToolMaterial("iron",
                12F, 2F, 2, 256, MATCHER_IRON, ironTextures);
        materials.put("iron", IRON);

        Map<String, ResourceLocation> goldTextures = new HashMap<>();
        goldTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "items/pickaxe/gold_pickaxe_head"));
        goldTextures.put(AXE, new ResourceLocation(Main.MODID, "items/axe/gold_axe_head"));
        goldTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "items/shovel/gold_shovel_head"));
        goldTextures.put(SWORD, new ResourceLocation(Main.MODID, "items/sword/gold_sword_head"));
        goldTextures.put(HOE, new ResourceLocation(Main.MODID, "items/hoe/gold_hoe_head"));
        GOLD = new AdvancedToolMaterial("gold",
                6F, 0F, 0, 32, MATCHER_GOLD, goldTextures);
        materials.put("gold", GOLD);

        Map<String, ResourceLocation> stoneTextures = new HashMap<>();
        stoneTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "items/pickaxe/stone_pickaxe_head"));
        stoneTextures.put(AXE, new ResourceLocation(Main.MODID, "items/axe/stone_axe_head"));
        stoneTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "items/shovel/stone_shovel_head"));
        stoneTextures.put(SWORD, new ResourceLocation(Main.MODID, "items/sword/stone_sword_head"));
        stoneTextures.put(HOE, new ResourceLocation(Main.MODID, "items/hoe/stone_hoe_head"));
        STONE = new AdvancedToolMaterial("stone",
                4F, 1F, 1, 128, MATCHER_STONE, stoneTextures);
        materials.put("stone", STONE);

        Map<String, ResourceLocation> woodTextures = new HashMap<>();
        woodTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "items/pickaxe/wood_pickaxe_head"));
        woodTextures.put(AXE, new ResourceLocation(Main.MODID, "items/axe/wood_axe_head"));
        woodTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "items/shovel/wood_shovel_head"));
        woodTextures.put(SWORD, new ResourceLocation(Main.MODID, "items/sword/wood_sword_head"));
        woodTextures.put(HOE, new ResourceLocation(Main.MODID, "items/hoe/wood_hoe_head"));
        WOOD = new AdvancedToolMaterial("wood",
                2F, 0F, 0, 64, MATCHER_WOOD, woodTextures);
        materials.put("wood", WOOD);
    }

    private String name;
    private float efficiency;
    private float attackModifier;
    private int harvestLevel;
    private int maxDamage;
    private MaterialMatcher matcher;
    private Map<String, ResourceLocation> partTextures;

    public AdvancedToolMaterial(String name, float efficiency, float attackModifier, int harvestLevel, int maxDamage, MaterialMatcher matcher, Map<String, ResourceLocation> partTextures) {
        this.name = name;
        this.efficiency = efficiency;
        this.attackModifier = attackModifier;
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

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public String getName() {
        return name;
    }

    public String getOredictName(String tool) {
        return tool + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getLocalizedName() {
        return new TextComponentTranslation("material." + name + ".name").getUnformattedText();
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

    public static AdvancedToolMaterial byName(String name) {
        if (name == null) {
            return null;
        }
        return materials.get(name);
    }

    public static Collection<AdvancedToolMaterial> getAll() {
        return Collections.unmodifiableCollection(materials.values());
    }

}
