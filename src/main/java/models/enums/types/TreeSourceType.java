package models.enums.types;

public enum TreeSourceType implements ItemType {
    APRICOT_SAPLING("Apricot Sapling"),
    CHERRY_SAPLING("Cherry Sapling"),
    BANANA_SAPLING("Banana Sapling"),
    MANGO_SAPLING("Mango Sapling"),
    ORANGE_SAPLING("Orange Sapling"),
    PEACH_SAPLING("Peach Sapling"),
    APPLE_SAPLING("Apple Sapling"),
    POMEGRANATE_SAPLING("Pomegranate Sapling"),
    ACORNS("Acorns"),
    MAPLE_SEEDS("Maple Seeds"),
    PINE_CONES("Pine Cones"),
    MAHOGANY_SEEDS("Mahogany Seeds"),
    MUSHROOM_TREE_SEEDS("Mushroom Tree Seeds"),
    MYSTIC_TREE_SEEDS("Mystic Tree Seeds");

    private final String name;

    TreeSourceType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
