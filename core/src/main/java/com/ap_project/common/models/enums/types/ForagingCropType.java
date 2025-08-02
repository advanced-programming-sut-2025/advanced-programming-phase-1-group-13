package com.ap_project.common.models.enums.types;

import com.ap_project.common.models.enums.environment.Season;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum ForagingCropType implements ItemType {
    COMMON_MUSHROOM("Common Mushroom", new ArrayList<>(List.of(Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER)), 40, 38),
    DAFFODIL("Daffodil", new ArrayList<>(List.of(Season.SPRING)), 30, 0),
    DANDELION("Dandelion", new ArrayList<>(List.of(Season.SPRING)), 40, 25),
    LEEK("Leek", new ArrayList<>(List.of(Season.SPRING)), 60, 40),
    MOREL("Morel", new ArrayList<>(List.of(Season.SPRING)), 150, 20),
    SALMONBERRY("Salmonberry", new ArrayList<>(List.of(Season.SPRING)), 5, 25),
    SPRING_ONION("Spring Onion", new ArrayList<>(List.of(Season.SPRING)), 8, 13),
    WILD_HORSERADISH("Wild Horseradish", new ArrayList<>(List.of(Season.SPRING)), 50, 13),
    FIDDLEHEAD_FERN("Fiddlehead Fern", new ArrayList<>(List.of(Season.SUMMER)), 90, 25),
    GRAPE("Grape", new ArrayList<>(List.of(Season.SUMMER)), 80, 38),
    RED_MUSHROOM("Red Mushroom", new ArrayList<>(List.of(Season.SUMMER)), 75, -50),
    SPICE_BERRY("Spice Berry", new ArrayList<>(List.of(Season.SUMMER)), 80, 25),
    SWEET_PEA("Sweet Pea", new ArrayList<>(List.of(Season.SUMMER)), 50, 0),
    BLACKBERRY("Blackberry", new ArrayList<>(List.of(Season.FALL)), 25, 25),
    CHANTERELLE("Chanterelle", new ArrayList<>(List.of(Season.FALL)), 160, 75),
    HAZELNUT("Hazelnut", new ArrayList<>(List.of(Season.FALL)), 40, 38),
    PURPLE_MUSHROOM("Purple Mushroom", new ArrayList<>(List.of(Season.FALL)), 90, 30),
    WILD_PLUM("Wild Plum", new ArrayList<>(List.of(Season.FALL)), 80, 25),
    CROCUS("Crocus", new ArrayList<>(List.of(Season.WINTER)), 60, 0),
    CRYSTAL_FRUIT("Crystal Fruit", new ArrayList<>(List.of(Season.WINTER)), 150, 63),
    HOLLY("Holly", new ArrayList<>(List.of(Season.WINTER)), 80, -37),
    SNOW_YAM("Snow Yam", new ArrayList<>(List.of(Season.WINTER)), 100, 30),
    WINTER_ROOT("Winter Root", new ArrayList<>(List.of(Season.WINTER)), 70, 25);

    private final String name;
    private final ArrayList<Season> seasons;
    private final int baseSellPrice;
    private final int energy;

    ForagingCropType(String name, ArrayList<Season> seasons, int baseSellPrice, int energy) {
        this.name = name;
        this.seasons = seasons;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public static ForagingCropType getRandomForagingCropType() {
        ForagingCropType[] values = ForagingCropType.values();
        return values[new Random().nextInt(values.length)];
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static ForagingCropType getForagingCropTypeByName(String name) {
        if (name == null) return null;
        for (ForagingCropType type : ForagingCropType.values()) {
            if (type.getName().equals(name)) return type;
        }
        return null;
    }
}
