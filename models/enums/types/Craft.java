package models.enums.types;

public enum Craft implements ItemType {
    CHERRY_BOMB("Cherry Bomb"),
    BOMB("Bomb"),
    MEGA_BOMB("Mega Bomb"),
    SPRINKLER("Sprinkler"),
    QUALITY_SPRINKLER("Quality Sprinkler"),
    IRIDIUM_SPRINKLER("Iridium Sprinkler"),
    CHARCOAL_KILN("Charcoal Kiln"),
    FURNACE("Furnace"),
    SCARECROW("Scarecrow"),
    DELUXE_SCARECROW("Deluxe Scarecrow"),
    BEE_HOUSE("Bee House"),
    CHEESE_PRESS("Cheese Press"),
    KEG("Keg"),
    LOOM("Loom"),
    MAYONNAISE_MACHINE("Mayonnaise Machine"),
    OIL_MAKER("Oil Maker"),
    PRESERVES_JAR("Preserves Jar"),
    DEHYDRATOR("Dehydrator"),
    GRASS_STARTER("Grass Starter"),
    FISH_SMOKER("Fish Smoker"),
    MYSTIC_TREE_SEED("Mystic Tree Seed");

    private final String name;

    Craft(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}