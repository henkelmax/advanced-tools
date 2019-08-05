package de.maxhenkel.advancedtools.items.tools;

import de.maxhenkel.advancedtools.Main;
import de.maxhenkel.advancedtools.items.tools.matcher.MaterialMatcher;
import de.maxhenkel.advancedtools.items.tools.matcher.SimpleMatcher;
import de.maxhenkel.advancedtools.items.tools.matcher.TagMatcher;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AdvancedToolMaterial {

    public static final String PICKAXE = "pickaxe";
    public static final String AXE = "axe";
    public static final String SWORD = "sword";
    public static final String SHOVEL = "shovel";
    public static final String HOE = "hoe";

    private static Map<String, AdvancedToolMaterial> materials = new HashMap<>();

    public static final MaterialMatcher MATCHER_DIAMOND = new SimpleMatcher(Items.DIAMOND);
    public static final MaterialMatcher MATCHER_IRON = new SimpleMatcher(Items.IRON_INGOT);
    public static final MaterialMatcher MATCHER_GOLD = new SimpleMatcher(Items.GOLD_INGOT);
    public static final MaterialMatcher MATCHER_WOOD = new TagMatcher(new ResourceLocation("minecraft", "planks"));
    public static final MaterialMatcher MATCHER_STONE = new SimpleMatcher(Blocks.COBBLESTONE);

    public static final AdvancedToolMaterial DIAMOND;
    public static final AdvancedToolMaterial IRON;
    public static final AdvancedToolMaterial GOLD;
    public static final AdvancedToolMaterial WOOD;
    public static final AdvancedToolMaterial STONE;

    static {
        Map<String, ResourceLocation> diaTextures = new HashMap<>();
        diaTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "item/pickaxe/diamond_pickaxe_head"));
        diaTextures.put(AXE, new ResourceLocation(Main.MODID, "item/axe/diamond_axe_head"));
        diaTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "item/shovel/diamond_shovel_head"));
        diaTextures.put(SWORD, new ResourceLocation(Main.MODID, "item/sword/diamond_sword_head"));
        diaTextures.put(HOE, new ResourceLocation(Main.MODID, "item/hoe/diamond_hoe_head"));
        DIAMOND = new AdvancedToolMaterial(
                "diamond",
                8F,
                3F,
                3,
                2048,
                MATCHER_DIAMOND,
                diaTextures,
                () -> Ingredient.fromItems(Items.DIAMOND)
        );
        materials.put("diamond", DIAMOND);

        Map<String, ResourceLocation> ironTextures = new HashMap<>();
        ironTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "item/pickaxe/iron_pickaxe_head"));
        ironTextures.put(AXE, new ResourceLocation(Main.MODID, "item/axe/iron_axe_head"));
        ironTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "item/shovel/iron_shovel_head"));
        ironTextures.put(SWORD, new ResourceLocation(Main.MODID, "item/sword/iron_sword_head"));
        ironTextures.put(HOE, new ResourceLocation(Main.MODID, "item/hoe/iron_hoe_head"));
        IRON = new AdvancedToolMaterial(
                "iron",
                6F,
                2F,
                2,
                256,
                MATCHER_IRON,
                ironTextures,
                () -> Ingredient.fromItems(Items.IRON_INGOT)
        );
        materials.put("iron", IRON);

        Map<String, ResourceLocation> goldTextures = new HashMap<>();
        goldTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "item/pickaxe/gold_pickaxe_head"));
        goldTextures.put(AXE, new ResourceLocation(Main.MODID, "item/axe/gold_axe_head"));
        goldTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "item/shovel/gold_shovel_head"));
        goldTextures.put(SWORD, new ResourceLocation(Main.MODID, "item/sword/gold_sword_head"));
        goldTextures.put(HOE, new ResourceLocation(Main.MODID, "item/hoe/gold_hoe_head"));
        GOLD = new AdvancedToolMaterial(
                "gold",
                12F,
                0F,
                0,
                32,
                MATCHER_GOLD,
                goldTextures,
                () -> Ingredient.fromItems(Items.GOLD_INGOT)
        );
        materials.put("gold", GOLD);

        Map<String, ResourceLocation> stoneTextures = new HashMap<>();
        stoneTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "item/pickaxe/stone_pickaxe_head"));
        stoneTextures.put(AXE, new ResourceLocation(Main.MODID, "item/axe/stone_axe_head"));
        stoneTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "item/shovel/stone_shovel_head"));
        stoneTextures.put(SWORD, new ResourceLocation(Main.MODID, "item/sword/stone_sword_head"));
        stoneTextures.put(HOE, new ResourceLocation(Main.MODID, "item/hoe/stone_hoe_head"));
        STONE = new AdvancedToolMaterial(
                "stone",
                4F,
                1F,
                1,
                128,
                MATCHER_STONE,
                stoneTextures,
                () -> Ingredient.fromItems(Blocks.COBBLESTONE)
        );
        materials.put("stone", STONE);

        Map<String, ResourceLocation> woodTextures = new HashMap<>();
        woodTextures.put(PICKAXE, new ResourceLocation(Main.MODID, "item/pickaxe/wood_pickaxe_head"));
        woodTextures.put(AXE, new ResourceLocation(Main.MODID, "item/axe/wood_axe_head"));
        woodTextures.put(SHOVEL, new ResourceLocation(Main.MODID, "item/shovel/wood_shovel_head"));
        woodTextures.put(SWORD, new ResourceLocation(Main.MODID, "item/sword/wood_sword_head"));
        woodTextures.put(HOE, new ResourceLocation(Main.MODID, "item/hoe/wood_hoe_head"));
        WOOD = new AdvancedToolMaterial(
                "wood",
                2F,
                0F,
                0,
                64,
                MATCHER_WOOD,
                woodTextures,
                () -> Ingredient.fromTag(ItemTags.PLANKS)
        );
        materials.put("wood", WOOD);
    }

    private String name;
    private float efficiency;
    private float attackModifier;
    private int harvestLevel;
    private int maxDamage;
    private MaterialMatcher matcher;
    private Map<String, ResourceLocation> partTextures;
    private Supplier<Ingredient> ingredient;

    public AdvancedToolMaterial(String name, float efficiency, float attackModifier, int harvestLevel, int maxDamage, MaterialMatcher matcher, Map<String, ResourceLocation> partTextures, Supplier<Ingredient> ingredient) {
        this.name = name;
        this.efficiency = efficiency;
        this.attackModifier = attackModifier;
        this.harvestLevel = harvestLevel;
        this.maxDamage = maxDamage;
        this.matcher = matcher;
        this.partTextures = partTextures;
        this.ingredient = ingredient;
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

    public Ingredient getIngredient() {
        return ingredient.get();
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("material." + name);
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
