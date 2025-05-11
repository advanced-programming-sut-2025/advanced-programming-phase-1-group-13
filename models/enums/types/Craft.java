package models.enums.types;

import java.util.HashMap;

public enum Craft implements ItemType {
    CHERRY_BOMB(
            "Cherry Bomb",
            "Destroys everything within a 3-tile radius.", null),
    BOMB(
            "Bomb",
            "Destroys everything within a 5-tile radius.", null),
    MEGA_BOMB(
            "Mega Bomb",
            "Destroys everything within a 7-tile radius.", null),
    SPRINKLER(
            "Sprinkler",
            "Waters 4 adjacent tiles.", null),
    QUALITY_SPRINKLER(
            "Quality Sprinkler",
            "Waters 8 adjacent tiles.", null),
    IRIDIUM_SPRINKLER(
            "Iridium Sprinkler",
            "Waters 24 adjacent tiles.", null),
    CHARCOAL_KILN(
            "Charcoal Kiln",
            "Converts 10 wood into 1 charcoal.", null),
    FURNACE(
            "Furnace",
            "Converts ores and coal into bars.", null),
    SCARECROW(
            "Scarecrow",
            "Prevents crow attacks within an 8-tile radius.", null),
    DELUXE_SCARECROW(
            "Deluxe Scarecrow",
            "Prevents crow attacks within a 12-tile radius.", null),
    BEE_HOUSE(
            "Bee House",
            "Produces honey when placed on the farm.", null),
    CHEESE_PRESS(
            "Cheese Press",
            "Converts milk into cheese.", null),
    KEG(
            "Keg",
            "Converts fruits and vegetables into beverages.", null),
    LOOM(
            "Loom",
            "Converts wool into cloth.", null),
    MAYONNAISE_MACHINE(
            "Mayonnaise Machine",
            "Converts eggs into mayonnaise.", null),
    OIL_MAKER(
            "Oil Maker",
            "Converts truffle into oil.", null),
    PRESERVES_JAR(
            "Preserves Jar",
            "Converts vegetables into pickles and fruits into jam.", null),
    DEHYDRATOR(
            "Dehydrator",
            "Dries fruits or mushrooms.", null),
    GRASS_STARTER(
            "Grass Starter",
            "When placed on the ground, grass grows in that spot.", null),
    FISH_SMOKER(
            "Fish Smoker",
            "Converts any fish into smoked fish using one charcoal while maintaining its quality.", null),
    MYSTIC_TREE_SEED(
            "Mystic Tree Seed",
            "Can be planted to grow a mystic tree.", null);

    private final String name;
    private final String description;

    private final HashMap<IngredientType, Integer> ingredients;

    Craft(String name, String description, HashMap<IngredientType, Integer> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    public static Craft getCraftByName(String name) {
        for (Craft craft : Craft.values()) {
            if (craft.getName().equals(name)) {
                return craft;
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
}