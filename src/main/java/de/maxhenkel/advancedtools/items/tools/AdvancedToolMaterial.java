package de.maxhenkel.advancedtools.items.tools;

import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;

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

    public static final AdvancedToolMaterial NETHERITE;
    public static final AdvancedToolMaterial DIAMOND;
    public static final AdvancedToolMaterial IRON;
    public static final AdvancedToolMaterial GOLD;
    public static final AdvancedToolMaterial WOOD;
    public static final AdvancedToolMaterial STONE;

    static {
        NETHERITE = new AdvancedToolMaterial(
                "netherite",
                9F,
                4F,
                4,
                2048,
                true,
                () -> Ingredient.fromItems(Items.field_234759_km_) //TODO netherite ingot tag
        );
        materials.put("netherite", NETHERITE);

        DIAMOND = new AdvancedToolMaterial(
                "diamond",
                8F,
                3F,
                3,
                1024,
                false,
                () -> Ingredient.fromTag(Tags.Items.GEMS_DIAMOND)
        );
        materials.put("diamond", DIAMOND);

        IRON = new AdvancedToolMaterial(
                "iron",
                6F,
                2F,
                2,
                256,
                false,
                () -> Ingredient.fromTag(Tags.Items.INGOTS_IRON)
        );
        materials.put("iron", IRON);

        GOLD = new AdvancedToolMaterial(
                "gold",
                12F,
                0F,
                0,
                32,
                false,
                () -> Ingredient.fromTag(Tags.Items.INGOTS_GOLD)
        );
        materials.put("gold", GOLD);

        STONE = new AdvancedToolMaterial(
                "stone",
                4F,
                1F,
                1,
                128,
                false,
                () -> Ingredient.fromTag(ItemTags.field_232909_aa_)
        );
        materials.put("stone", STONE);

        WOOD = new AdvancedToolMaterial(
                "wood",
                2F,
                0F,
                0,
                64,
                false,
                () -> Ingredient.fromTag(ItemTags.PLANKS)
        );
        materials.put("wood", WOOD);
    }

    private String name;
    private float efficiency;
    private float attackModifier;
    private int harvestLevel;
    private int maxDamage;
    private boolean fireResistant;
    private Supplier<Ingredient> ingredient;

    public AdvancedToolMaterial(String name, float efficiency, float attackModifier, int harvestLevel, int maxDamage, boolean fireResistant, Supplier<Ingredient> ingredient) {
        this.name = name;
        this.efficiency = efficiency;
        this.attackModifier = attackModifier;
        this.harvestLevel = harvestLevel;
        this.maxDamage = maxDamage;
        this.fireResistant = fireResistant;
        this.ingredient = ingredient;
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

    public boolean isFireResistant() {
        return fireResistant;
    }

    public Ingredient getIngredient() {
        return ingredient.get();
    }

    public IFormattableTextComponent getDisplayName() {
        return new TranslationTextComponent("material." + name);
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
