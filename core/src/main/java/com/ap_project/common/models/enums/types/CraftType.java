package com.ap_project.common.models.enums.types;

import com.ap_project.common.models.GameAssetManager;

import java.util.HashMap;

public enum CraftType implements ItemType {
    CHERRY_BOMB(
        "Cherry Bomb",
        "Destroys everything within a 3-tile radius.",
        createIngredientsMap(
            IngredientType.COPPER_ORE, 4,
            IngredientType.COAL, 1
        )
    ),
    BOMB(
        "Bomb",
        "Destroys everything within a 5-tile radius.",
        createIngredientsMap(
            IngredientType.IRON_ORE, 4,
            IngredientType.COAL, 1
        )
    ),
    MEGA_BOMB(
        "Mega Bomb",
        "Destroys everything within a 7-tile radius.",
        createIngredientsMap(
            IngredientType.GOLD_ORE, 4,
            IngredientType.COAL, 1
        )
    ),
    SPRINKLER(
        "Sprinkler",
        "Waters 4 adjacent tiles.",
        createIngredientsMap(
            IngredientType.COPPER_BAR, 1,
            IngredientType.IRON_BAR, 1
        )
    ),
    QUALITY_SPRINKLER(
        "Quality Sprinkler",
        "Waters 8 adjacent tiles.",
        createIngredientsMap(
            IngredientType.IRON_BAR, 1,
            IngredientType.GOLD_BAR, 1
        )
    ),
    IRIDIUM_SPRINKLER(
        "Iridium Sprinkler",
        "Waters 24 adjacent tiles.",
        createIngredientsMap(
            IngredientType.GOLD_BAR, 1,
            IngredientType.IRIDIUM_BAR, 1
        )
    ),
    CHARCOAL_KILN(
        "Charcoal Kiln",
        "Converts 10 wood into 1 charcoal.",
        createIngredientsMap(
            IngredientType.WOOD, 20,
            IngredientType.COPPER_BAR, 2
        )
    ),
    FURNACE(
        "Furnace",
        "Converts ores and coal into bars.",
        createIngredientsMap(
            IngredientType.COPPER_ORE, 20,
            IngredientType.STONE, 25
        )
    ),
    SCARECROW(
        "Scarecrow",
        "Prevents crow attacks within an 8-tile radius.",
        createIngredientsMap(
            IngredientType.WOOD, 50,
            IngredientType.COAL, 1,
            IngredientType.FIBER, 20
        )
    ),
    DELUXE_SCARECROW(
        "Deluxe Scarecrow",
        "Prevents crow attacks within a 12-tile radius.",
        createIngredientsMap(
            IngredientType.WOOD, 50,
            IngredientType.COAL, 1,
            IngredientType.FIBER, 20,
            IngredientType.IRIDIUM_ORE, 1
        )
    ),
    BEE_HOUSE(
        "Bee House",
        "Produces honey when placed on the farm.",
        createIngredientsMap(
            IngredientType.WOOD, 40,
            IngredientType.COAL, 8,
            IngredientType.IRON_BAR, 1
        )
    ),
    CHEESE_PRESS(
        "Cheese Press",
        "Converts milk into cheese.",
        createIngredientsMap(
            IngredientType.WOOD, 45,
            IngredientType.STONE, 45,
            IngredientType.COPPER_BAR, 1
        )
    ),
    KEG(
        "Keg",
        "Converts fruits and vegetables into beverages.",
        createIngredientsMap(
            IngredientType.WOOD, 30,
            IngredientType.COPPER_BAR, 1,
            IngredientType.IRON_BAR, 1
        )
    ),
    LOOM(
        "Loom",
        "Converts wool into cloth.",
        createIngredientsMap(
            IngredientType.WOOD, 60,
            IngredientType.FIBER, 30
        )
    ),
    MAYONNAISE_MACHINE(
        "Mayonnaise Machine",
        "Converts eggs into mayonnaise.",
        createIngredientsMap(
            IngredientType.WOOD, 15,
            IngredientType.STONE, 15,
            IngredientType.COPPER_BAR, 1
        )
    ),
    OIL_MAKER(
        "Oil Maker",
        "Converts truffle into oil.",
        createIngredientsMap(
            IngredientType.WOOD, 100,
            IngredientType.GOLD_BAR, 1,
            IngredientType.IRON_BAR, 1
        )
    ),
    PRESERVES_JAR(
        "Preserves Jar",
        "Converts vegetables into pickles and fruits into jam.",
        createIngredientsMap(
            IngredientType.WOOD, 50,
            IngredientType.STONE, 40,
            IngredientType.COAL, 8
        )
    ),
    DEHYDRATOR(
        "Dehydrator",
        "Dries fruits or mushrooms.",
        createIngredientsMap(
            IngredientType.WOOD, 30,
            IngredientType.STONE, 20,
            IngredientType.FIBER, 30
        )
    ),
    GRASS_STARTER(
        "Grass Starter",
        "When placed on the ground, grass grows in that spot.",
        createIngredientsMap(
            IngredientType.WOOD, 1,
            IngredientType.FIBER, 1
        )
    ),
    FISH_SMOKER(
        "Fish Smoker",
        "Converts any fish into smoked fish using one charcoal while maintaining its quality.",
        createIngredientsMap(
            IngredientType.WOOD, 50,
            IngredientType.IRON_BAR, 3,
            IngredientType.COAL, 10
        )
    ),
    MYSTIC_TREE_SEED(
        "Mystic Tree Seed",
        "Can be planted to grow a mystic tree.",
        createIngredientsMap(
            IngredientType.ACORN, 5,
            IngredientType.MAPLE_SEED, 5,
            IngredientType.PINE_CONE, 5,
            IngredientType.MAHOGANY_SEED, 5
        )
    );
    private final String name;
    private final String description;

    private final HashMap<IngredientType, Integer> ingredients;

    CraftType(String name, String description, HashMap<IngredientType, Integer> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    public static CraftType getCraftByName(String name) {
        for (CraftType craftType : CraftType.values()) {
            if (craftType.getName().equals(name) ||
                GameAssetManager.toPascalCase(craftType.getName()).equals(name)) {
                return craftType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public HashMap<IngredientType, Integer> getIngredients() {
        return this.ingredients;
    }

    private static HashMap<IngredientType, Integer> createIngredientsMap(Object... items) {
        return FoodType.createIngredientsMap(items); // same functionality
    }

    public ArtisanType getArtisanType() {
        if (this == BEE_HOUSE) return ArtisanType.BEE_HOUSE;
        if (this == KEG) return ArtisanType.KEG;
        if (this == CHEESE_PRESS) return ArtisanType.CHEESE_PRESS;
        if (this == LOOM) return ArtisanType.LOOM;
        if (this == MAYONNAISE_MACHINE) return ArtisanType.MAYONNAISE_MACHINE;
        if (this == OIL_MAKER) return ArtisanType.OIL_MAKER;
        if (this == PRESERVES_JAR) return ArtisanType.PRESERVES_JAR;
        if (this == DEHYDRATOR) return ArtisanType.DEHYDRATOR;
        if (this == FISH_SMOKER) return ArtisanType.FISH_SMOKER;
        return null;
    }

    public boolean isArtisan() {
        return this.getArtisanType() != null;
    }
}
