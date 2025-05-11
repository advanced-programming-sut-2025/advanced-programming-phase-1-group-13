package models.enums.types;

import java.util.HashMap;

public enum Craft implements ItemType {
    CHERRY_BOMB(
            "Cherry Bomb",
            "Destroys everything within a 3-tile radius.", ),
    BOMB(
            "Bomb",
            "Destroys everything within a 5-tile radius.", ),
    MEGA_BOMB(
            "Mega Bomb",
            "Destroys everything within a 7-tile radius.", ),
    SPRINKLER(
            "Sprinkler",
            "Waters 4 adjacent tiles.", ),
    QUALITY_SPRINKLER(
            "Quality Sprinkler",
            "Waters 8 adjacent tiles.", ),
    IRIDIUM_SPRINKLER(
            "Iridium Sprinkler",
            "Waters 24 adjacent tiles.", ),
    CHARCOAL_KILN(
            "Charcoal Kiln",
            "Converts 10 wood into 1 charcoal.", ),
    FURNACE(
            "Furnace",
            "Converts ores and coal into bars.", ),
    SCARECROW(
            "Scarecrow",
            "Prevents crow attacks within an 8-tile radius.", ),
    DELUXE_SCARECROW(
            "Deluxe Scarecrow",
            "Prevents crow attacks within a 12-tile radius.", ),
    BEE_HOUSE(
            "Bee House",
            "Produces honey when placed on the farm.", ),
    CHEESE_PRESS(
            "Cheese Press",
            "Converts milk into cheese.", ),
    KEG(
            "Keg",
            "Converts fruits and vegetables into beverages.", ),
    LOOM(
            "Loom",
            "Converts wool into cloth.", ),
    MAYONNAISE_MACHINE(
            "Mayonnaise Machine",
            "Converts eggs into mayonnaise.", ),
    OIL_MAKER(
            "Oil Maker",
            "Converts truffle into oil.", ),
    PRESERVES_JAR(
            "Preserves Jar",
            "Converts vegetables into pickles and fruits into jam.", ),
    DEHYDRATOR(
            "Dehydrator",
            "Dries fruits or mushrooms.", ),
    GRASS_STARTER(
            "Grass Starter",
            "When placed on the ground, grass grows in that spot.", ),
    FISH_SMOKER(
            "Fish Smoker",
            "Converts any fish into smoked fish using one charcoal while maintaining its quality.", ),
    MYSTIC_TREE_SEED(
            "Mystic Tree Seed",
            "Can be planted to grow a mystic tree.", );

    private final String name;
    private final String description;

    private final HashMap<IngredientType, Integer> ingredients;

    Craft(String name, String description, HashMap<IngredientType, Integer> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}