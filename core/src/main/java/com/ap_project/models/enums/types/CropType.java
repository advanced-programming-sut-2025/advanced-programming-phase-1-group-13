package com.ap_project.models.enums.types;

import com.ap_project.models.enums.environment.Season;

import java.util.ArrayList;
import java.util.List;

public enum CropType implements ItemType {

    BLUE_JAZZ("Blue Jazz", SeedType.JAZZ_SEEDS, new ArrayList<>(List.of(1, 2, 2, 2)), 7, true, null, 50, true, 45, new ArrayList<>(List.of(Season.SPRING)), false),
    CARROT("Carrot", SeedType.CARROT_SEEDS, new ArrayList<>(List.of(1, 1, 1)), 3, true, null, 35, true, 75, new ArrayList<>(List.of(Season.SPRING)), false),
    CAULIFLOWER("Cauliflower", SeedType.CAULIFLOWER_SEEDS, new ArrayList<>(List.of(1, 2, 4, 4, 1)), 12, true, null, 175, true, 75, new ArrayList<>(List.of(Season.SPRING)), true),
    COFFEE_BEAN("Coffee Bean", SeedType.COFFEE_BEAN, new ArrayList<>(List.of(1, 2, 2, 3, 2)), 10, false, 2, 15, false, null, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER)), false),
    GARLIC("Garlic", SeedType.GARLIC_SEEDS, new ArrayList<>(List.of(1, 1, 1, 1)), 4, true, null, 60, true, 20, new ArrayList<>(List.of(Season.SPRING)), false),
    GREEN_BEAN("Green Bean", SeedType.BEAN_STARTER, new ArrayList<>(List.of(1, 1, 1, 3, 4)), 10, false, 3, 40, true, 25, new ArrayList<>(List.of(Season.SPRING)), false),
    KALE("Kale", SeedType.KALE_SEEDS, new ArrayList<>(List.of(1, 2, 2, 1)), 6, true, null, 110, true, 50, new ArrayList<>(List.of(Season.SPRING)), false),
    PARSNIP("Parsnip", SeedType.PARSNIP_SEEDS, new ArrayList<>(List.of(1, 1, 1, 1)), 4, true, null, 35, true, 25, new ArrayList<>(List.of(Season.SPRING)), false),
    POTATO("Potato", SeedType.POTATO_SEEDS, new ArrayList<>(List.of(1, 1, 1, 2, 1)), 6, true, null, 80, true, 25, new ArrayList<>(List.of(Season.SPRING)), false),
    RHUBARB("Rhubarb", SeedType.RHUBARB_SEEDS, new ArrayList<>(List.of(2, 2, 2, 3, 4)), 13, true, null, 220, false, null, new ArrayList<>(List.of(Season.SPRING)), false),
    STRAWBERRY("Strawberry", SeedType.STRAWBERRY_SEEDS, new ArrayList<>(List.of(1, 1, 2, 2, 2)), 8, false, 4, 120, true, 50, new ArrayList<>(List.of(Season.SPRING)), false),
    TULIP("Tulip", SeedType.TULIP_BULB, new ArrayList<>(List.of(1, 1, 2, 2)), 6, true, null, 30, true, 45, new ArrayList<>(List.of(Season.SPRING)), false),
    UNMILLED_RICE("Unmilled Rice", SeedType.RICE_SHOOT, new ArrayList<>(List.of(1, 2, 2, 3)), 8, true, null, 30, true, 3, new ArrayList<>(List.of(Season.SPRING)), false),
    BLUEBERRY("Blueberry", SeedType.BLUEBERRY_SEEDS, new ArrayList<>(List.of(1, 3, 3, 4, 2)), 13, false, 4, 50, true, 25, new ArrayList<>(List.of(Season.SUMMER)), false),
    CORN("Corn", SeedType.CORN_SEEDS, new ArrayList<>(List.of(2, 3, 3, 3, 3)), 14, false, 4, 50, true, 25, new ArrayList<>(List.of(Season.SUMMER, Season.FALL)), false),
    HOPS("Hops", SeedType.HOPS_STARTER, new ArrayList<>(List.of(1, 1, 2, 3, 4)), 11, false, 1, 25, true, 45, new ArrayList<>(List.of(Season.SUMMER)), false),
    HOT_PEPPER("Hot Pepper", SeedType.PEPPER_SEEDS, new ArrayList<>(List.of(1, 1, 1, 1, 1)), 5, false, 3, 40, true, 13, new ArrayList<>(List.of(Season.SUMMER)), false),
    MELON("Melon", SeedType.MELON_SEEDS, new ArrayList<>(List.of(1, 2, 3, 3, 3)), 12, true, null, 250, true, 113, new ArrayList<>(List.of(Season.SUMMER)), true),
    POPPY("Poppy", SeedType.POPPY_SEEDS, new ArrayList<>(List.of(1, 2, 2, 2)), 7, true, null, 140, true, 45, new ArrayList<>(List.of(Season.SUMMER)), false),
    RADISH("Radish", SeedType.RADISH_SEEDS, new ArrayList<>(List.of(2, 1, 2, 1)), 6, true, null, 90, true, 45, new ArrayList<>(List.of(Season.SUMMER)), false),
    RED_CABBAGE("Red Cabbage", SeedType.RED_CABBAGE_SEEDS, new ArrayList<>(List.of(2, 1, 2, 2, 2)), 9, true, null, 260, true, 75, new ArrayList<>(List.of(Season.SUMMER)), false),
    STARFRUIT("Starfruit", SeedType.STARFRUIT_SEEDS, new ArrayList<>(List.of(2, 3, 2, 3, 3)), 13, true, null, 750, true, 125, new ArrayList<>(List.of(Season.SUMMER)), false),
    SUMMER_SPANGLE("Summer Spangle", SeedType.SPANGLE_SEEDS, new ArrayList<>(List.of(1, 2, 3, 1)), 8, true, null, 90, true, 45, new ArrayList<>(List.of(Season.SUMMER)), false),
    SUMMER_SQUASH("Summer Squash", SeedType.SUMMER_SQUASH_SEEDS, new ArrayList<>(List.of(1, 1, 1, 2, 1)), 6, false, 3, 45, true, 63, new ArrayList<>(List.of(Season.SUMMER)), false),
    SUNFLOWER("Sunflower", SeedType.SUNFLOWER_SEEDS, new ArrayList<>(List.of(1, 2, 3, 2)), 8, true, null, 80, true, 45, new ArrayList<>(List.of(Season.SUMMER, Season.FALL)), false),
    TOMATO("Tomato", SeedType.TOMATO_SEEDS, new ArrayList<>(List.of(2, 2, 2, 2, 3)), 11, false, 4, 60, true, 20, new ArrayList<>(List.of(Season.SUMMER)), false),
    WHEAT("Wheat", SeedType.WHEAT_SEEDS, new ArrayList<>(List.of(1, 1, 1, 1)), 4, true, null, 25, false, null, new ArrayList<>(List.of(Season.SUMMER, Season.FALL)), false),
    AMARANTH("Amaranth", SeedType.AMARANTH_SEEDS, new ArrayList<>(List.of(1, 2, 2, 2)), 7, true, null, 150, true, 50, new ArrayList<>(List.of(Season.FALL)), false),
    ARTICHOKE("Artichoke", SeedType.ARTICHOKE_SEEDS, new ArrayList<>(List.of(2, 2, 1, 2, 1)), 8, true, null, 160, true, 30, new ArrayList<>(List.of(Season.FALL)), false),
    BEET("Beet", SeedType.BEET_SEEDS, new ArrayList<>(List.of(1, 1, 2, 2)), 6, true, null, 100, true, 30, new ArrayList<>(List.of(Season.FALL)), false),
    BOK_CHOY("Bok Choy", SeedType.BOK_CHOY_SEEDS, new ArrayList<>(List.of(1, 1, 1, 1)), 4, true, null, 80, true, 25, new ArrayList<>(List.of(Season.FALL)), false),
    BROCCOLI("Broccoli", SeedType.BROCCOLI_SEEDS, new ArrayList<>(List.of(2, 2, 2, 2)), 8, false, 4, 70, true, 63, new ArrayList<>(List.of(Season.FALL)), false),
    CRANBERRIES("Cranberries", SeedType.CRANBERRY_SEEDS, new ArrayList<>(List.of(1, 2, 1, 1, 2)), 7, false, 5, 75, true, 38, new ArrayList<>(List.of(Season.FALL)), false),
    EGGPLANT("Eggplant", SeedType.EGGPLANT_SEEDS, new ArrayList<>(List.of(1, 1, 1, 1)), 5, false, 5, 60, true, 20, new ArrayList<>(List.of(Season.FALL)), false),
    FAIRY_ROSE("Fairy Rose", SeedType.FAIRY_SEEDS, new ArrayList<>(List.of(1, 4, 4, 3)), 12, true, null, 290, true, 45, new ArrayList<>(List.of(Season.FALL)), false),
    GRAPE("Grape", SeedType.GRAPE_STARTER, new ArrayList<>(List.of(1, 1, 2, 3, 3)), 10, false, 3, 80, true, 38, new ArrayList<>(List.of(Season.FALL)), false),
    PUMPKIN("Pumpkin", SeedType.PUMPKIN_SEEDS, new ArrayList<>(List.of(1, 2, 3, 4, 3)), 13, true, null, 320, false, null, new ArrayList<>(List.of(Season.FALL)), true),
    YAM("Yam", SeedType.YAM_SEEDS, new ArrayList<>(List.of(1, 3, 3, 3)), 10, true, null, 160, true, 45, new ArrayList<>(List.of(Season.FALL)), false),
    SWEET_GEM_BERRY("Sweet Gem Berry", SeedType.RARE_SEED, new ArrayList<>(List.of(2, 4, 6, 6, 6)), 24, true, null, 3000, false, null, new ArrayList<>(List.of(Season.FALL)), false),
    POWDERMELON("Powdermelon", SeedType.POWDERMELON_SEEDS, new ArrayList<>(List.of(1, 2, 1, 2, 1)), 7, true, null, 60, true, 63, new ArrayList<>(List.of(Season.WINTER)), true),
    ANCIENT_FRUIT("Ancient Fruit", SeedType.ANCIENT_SEEDS, new ArrayList<>(List.of(2, 7, 7, 7, 5)), 28, false, 7, 550, false, null, new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL)), false);

    private final String name;
    private final SeedType source;
    private final ArrayList<Integer> stages;
    private final Integer totalHarvestTime;
    private final boolean oneTime;
    private final Integer regrowthTime;
    private final Integer sellPrice;
    private final boolean isEdible;
    private final Integer energy;
    private final ArrayList<Season> seasons;
    private final boolean canBecomeGiant;

    CropType(String name, SeedType source, ArrayList<Integer> stages, Integer totalHarvestTime, boolean oneTime,
             Integer regrowthTime, Integer sellPrice, boolean isEdible, Integer energy, ArrayList<Season> seasons, boolean canBecomeGiant) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.sellPrice = sellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.seasons = seasons;
        this.canBecomeGiant = canBecomeGiant;
    }

    public int getNumberOfStages() {
        return stages.size();
    }

    public SeedType getSource() {
        return source;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public Integer getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public Integer getRegrowthTime() {
        return regrowthTime;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public Integer getEnergy() {
        return energy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public boolean canBecomeGiant() {
        return canBecomeGiant;
    }

    public static CropType getCropTypeByName(String name) {
        for (CropType cropType : CropType.values()) {
            if (cropType.getName().equals(name)) {
                return cropType;
            }
        }
        return null;
    }

    public static CropType getCropTypeBySeedType(SeedType seedType) {
        for (CropType cropType : CropType.values()) {
            if (seedType == cropType.getSource()) {
                return cropType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
