package com.project.models.enums.types;

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


    public static TreeSourceType getTreeSourceTypeByName(String name) {
        if (name == null) {
            return null;
        }
        return switch (name.trim().toLowerCase()) {
            case "apricot sapling" -> APRICOT_SAPLING;
            case "cherry sapling" -> CHERRY_SAPLING;
            case "banana sapling" -> BANANA_SAPLING;
            case "mango sapling" -> MANGO_SAPLING;
            case "orange sapling" -> ORANGE_SAPLING;
            case "peach sapling" -> PEACH_SAPLING;
            case "apple sapling" -> APPLE_SAPLING;
            case "pomegranate sapling" -> POMEGRANATE_SAPLING;
            case "acorns" -> ACORNS;
            case "maple seeds" -> MAPLE_SEEDS;
            case "pine cones" -> PINE_CONES;
            case "mahogany seeds" -> MAHOGANY_SEEDS;
            case "mushroom tree seeds" -> MUSHROOM_TREE_SEEDS;
            case "mystic tree seeds" -> MYSTIC_TREE_SEEDS;

            default -> null;
        };
    }

    @Override
    public String getName() {
        return this.name;
    }
}
