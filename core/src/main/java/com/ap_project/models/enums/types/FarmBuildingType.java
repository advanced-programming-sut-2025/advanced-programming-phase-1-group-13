package com.ap_project.models.enums.types;

public enum FarmBuildingType {
    BARN("Barn", 7, 4, "Houses 4 barn-dwelling animals.", 350, 150, 6000, false, 4),
    BIG_BARN("Big Barn", 7, 4, "Houses 8 barn-dwelling animals. Unlocks goats.", 450, 200, 12000, false, 8),
    DELUXE_BARN("Deluxe Barn", 7, 4, "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.", 550, 300, 25000, false, 12),
    COOP("Coop", 6, 3, "Houses 4 coop-dwelling animals.", 300, 100, 4000, false, 4),
    BIG_COOP("Big Coop", 6, 3, "Houses 8 coop-dwelling animals. Unlocks ducks.", 400, 150, 10000, false, 8),
    DELUXE_COOP("Deluxe Coop", 6, 3, "Houses 12 coop-dwelling animals. Unlocks rabbits.", 500, 200, 20000, false, 12),
    WELL("Well", 3, 3, "Provides a place for you to refill your watering can.", 0, 75, 1000, null, null),
    SHIPPING_BIN("Shipping Bin", 1, 1, "Items placed in it will be included in the nightly shipment.", 150, 0, 250, null, null);

    private final String name;
    private final int width;
    private final int length;
    private final String description;
    private final int woodCount;
    private final int stoneCount;
    private final int cost;
    private final Boolean isCage;
    /* isCage:
    true: cage
    false: stable
    null: neither of the two
    */
    private final Integer capacity; // null if not an animal living space

    FarmBuildingType(String name, int width, int length, String description, int woodCount, int stoneCount, int cost, Boolean isCage, Integer capacity) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.description = description;
        this.woodCount = woodCount;
        this.stoneCount = stoneCount;
        this.cost = cost;
        this.isCage = isCage;
        this.capacity = capacity;
    }

    public static FarmBuildingType getFarmBuildingTypeByName(String name) {
        for (FarmBuildingType farmBuildingType : FarmBuildingType.values()) {
            if (farmBuildingType.getName().equals(name)) {
                return farmBuildingType;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public String getDescription() {
        return description;
    }

    public int getLength() {
        return length;
    }

    public int getWoodCount() {
        return woodCount;
    }

    public int getCost() {
        return cost;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public Boolean getIsCage() {
        return isCage;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
