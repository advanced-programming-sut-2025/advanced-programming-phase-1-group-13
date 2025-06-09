package com.ap_project.models.enums.types;

import com.ap_project.models.enums.environment.Season;

import java.util.Random;

public enum FishType implements ItemType {
    SALMON("Salmon", 75, Season.FALL, false),
    SARDINE("Sardine", 40, Season.FALL, false),
    SHAD("Shad", 60, Season.FALL, false),
    BLUE_DISCUS("Blue Discus", 120, Season.FALL, false),
    MIDNIGHT_CARP("Midnight Carp", 150, Season.WINTER, false),
    SQUID("Squid", 80, Season.WINTER, false),
    TUNA("Tuna", 100, Season.WINTER, false),
    PERCH("Perch", 55, Season.WINTER, false),
    FLOUNDER("Flounder", 100, Season.SPRING, false),
    LIONFISH("Lionfish", 100, Season.SPRING, false),
    HERRING("Herring", 30, Season.SPRING, false),
    GHOSTFISH("Ghostfish", 45, Season.SPRING, false),
    TILAPIA("Tilapia", 75, Season.SUMMER, false),
    DORADO("Dorado", 100, Season.SUMMER, false),
    SUNFISH("Sunfish", 30, Season.SUMMER, false),
    RAINBOW_TROUT("Rainbow Trout", 65, Season.SUMMER, false),
    LEGEND("Legend", 5000, Season.SPRING, true),
    GLACIERFISH("Glacierfish", 1000, Season.WINTER, true),
    ANGLER("Angler", 900, Season.FALL, true),
    CRIMSONFISH("Crimsonfish", 1500, Season.SUMMER, true);

    private final String name;
    private final int basePrice;
    private final Season season;
    private final boolean isLegendary;

    FishType(String name, int basePrice, Season season, boolean isLegendary) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    public static FishType getFishTypeByName(String name) {
        for (FishType fishType : FishType.values()) {
            if (fishType.getName().equals(name)) {
                return fishType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }


    public int getBasePrice() {
        return basePrice;
    }

    public Season getSeason() {
        return season;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public static FishType getRandomFishType(Season season, boolean canBeLegendary, FishingRodType rodType) {
        FishType[] fishTypes = FishType.values();

        if (rodType.equals(FishingRodType.TRAINING)) {
            if (season.equals(Season.SPRING)) {
                return FishType.HERRING;
            }
            if (season.equals(Season.SUMMER)) {
                return FishType.SUNFISH;
            }
            if (season.equals(Season.FALL)) {
                return FishType.SARDINE;
            }
            if (season.equals(Season.WINTER)) {
                return FishType.PERCH;
            }
        }

        while (true) {
            int index = (new Random()).nextInt(20);
            if (fishTypes[index].season.equals(season)) {
                if (!fishTypes[index].isLegendary || canBeLegendary) {
                    return fishTypes[index];
                }
            }
        }
    }
}
