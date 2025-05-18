package models.enums.types;

import models.enums.environment.Season;
import models.farming.Tree;

import java.util.ArrayList;
import java.util.List;

public enum TreeType implements ItemType {
    APRICOT_TREE("Apricot Tree", TreeSourceType.APRICOT_SAPLING, FruitType.APRICOT, 1, 59, true, 38, new ArrayList<>(List.of(Season.SPRING))),
    CHERRY_TREE("Cherry Tree", TreeSourceType.CHERRY_SAPLING, FruitType.CHERRY, 1, 80, true, 38, new ArrayList<>(List.of(Season.SPRING))),
    BANANA_TREE("Banana Tree", TreeSourceType.BANANA_SAPLING, FruitType.BANANA, 1, 150, true, 75, new ArrayList<>(List.of(Season.SUMMER))),
    MANGO_TREE("Mango Tree", TreeSourceType.MANGO_SAPLING, FruitType.MANGO, 1, 130, true, 100, new ArrayList<>(List.of(Season.SUMMER))),
    ORANGE_TREE("Orange Tree", TreeSourceType.ORANGE_SAPLING, FruitType.ORANGE, 1, 100, true, 38, new ArrayList<>(List.of(Season.SUMMER))),
    PEACH_TREE("Peach Tree", TreeSourceType.PEACH_SAPLING, FruitType.PEACH, 1, 140, true, 38, new ArrayList<>(List.of(Season.SUMMER))),
    APPLE_TREE("Apple Tree", TreeSourceType.APPLE_SAPLING, FruitType.APPLE, 1, 100, true, 38, new ArrayList<>(List.of(Season.FALL))),
    POMEGRANATE_TREE("Pomegranate Tree", TreeSourceType.POMEGRANATE_SAPLING, FruitType.POMEGRANATE, 1, 140, true, 38, new ArrayList<>(List.of(Season.FALL))),
    OAK_TREE("Oak Tree", TreeSourceType.ACORNS, FruitType.OAK_RESIN, 7, 150, false, null, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER))),
    MAPLE_TREE("Maple Tree", TreeSourceType.MAPLE_SEEDS, FruitType.MAPLE_SYRUP, 9, 200, false, null, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER))),
    PINE_TREE("Pine Tree", TreeSourceType.PINE_CONES, FruitType.PINE_TAR, 5, 100, false, null, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER))),
    MAHOGANY_TREE("Mahogany Tree", TreeSourceType.MAHOGANY_SEEDS, FruitType.SAP, 1, 2, true, -2, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER))),
    MUSHROOM_TREE("Mushroom Tree", TreeSourceType.MUSHROOM_TREE_SEEDS, FruitType.COMMON_MUSHROOM, 1, 40, true, 38, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER))),
    MYSTIC_TREE("Mystic Tree", TreeSourceType.MYSTIC_TREE_SEEDS, FruitType.MYSTIC_SYRUP, 7, 1000, true, 500, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER)));

    private final String name;
    private final TreeSourceType source;
    private final FruitType fruit;
    private final int fruitHarvestCycle;
    private final int fruitBaseSellPrice;
    private final boolean isFruitEdible;
    private final Integer fruitEnergy;
    private final ArrayList<Season> seasons;

    TreeType(String name, TreeSourceType source, FruitType fruit, int fruitHarvestCycle, int fruitBaseSellPrice,
             boolean isFruitEdible, Integer fruitEnergy, ArrayList<Season> seasons) {
        this.name = name;
        this.source = source;
        this.fruit = fruit;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.fruitBaseSellPrice = fruitBaseSellPrice;
        this.isFruitEdible = isFruitEdible;
        this.fruitEnergy = fruitEnergy;
        this.seasons = seasons;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public TreeSourceType getSource() {
        return source;
    }

    public int getStagesTime() {
        return 7;
    }

    public int getTotalHarvestTime() {
        return 28;
    }

    public FruitType getFruit() {
        return fruit;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public int getFruitBaseSellPrice() {
        return fruitBaseSellPrice;
    }

    public boolean isFruitEdible() {
        return isFruitEdible;
    }

    public Integer getFruitEnergy() {
        return fruitEnergy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public static TreeType getRandomTreeType() {
        return TreeType.values()[(int) (Math.random() * TreeType.values().length)];
    }

    public static TreeType getTreeTypeByName(String name) {
        if (name == null) {
            return null;
        }
        return switch (name.toLowerCase()) {
            case "apricot tree" -> APRICOT_TREE;
            case "cherry tree" -> CHERRY_TREE;
            case "banana tree" -> BANANA_TREE;
            case "mango tree" -> MANGO_TREE;
            case "orange tree" -> ORANGE_TREE;
            case "peach tree" -> PEACH_TREE;
            case "apple tree" -> APPLE_TREE;
            case "pomegranate tree" -> POMEGRANATE_TREE;
            case "oak tree" -> OAK_TREE;
            case "maple tree" -> MAPLE_TREE;
            case "pine tree" -> PINE_TREE;
            case "mahogany tree" -> MAHOGANY_TREE;
            case "mushroom tree" -> MUSHROOM_TREE;
            case "mystic tree" -> MYSTIC_TREE;
            default -> null;
        };
    }

    public static TreeType getTreeTypeBySourceType(TreeSourceType sourceType) {
        return switch (sourceType) {
            case APRICOT_SAPLING -> APRICOT_TREE;
            case CHERRY_SAPLING -> CHERRY_TREE;
            case BANANA_SAPLING -> BANANA_TREE;
            case MANGO_SAPLING -> MANGO_TREE;
            case ORANGE_SAPLING -> ORANGE_TREE;
            case PEACH_SAPLING -> PEACH_TREE;
            case APPLE_SAPLING -> APPLE_TREE;
            case POMEGRANATE_SAPLING -> POMEGRANATE_TREE;
            case ACORNS -> OAK_TREE;
            case MAPLE_SEEDS -> MAPLE_TREE;
            case PINE_CONES -> PINE_TREE;
            case MAHOGANY_SEEDS -> MAHOGANY_TREE;
            case MUSHROOM_TREE_SEEDS -> MUSHROOM_TREE;
            case MYSTIC_TREE_SEEDS -> MYSTIC_TREE;
        };
    }

}
