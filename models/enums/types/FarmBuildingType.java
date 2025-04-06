package models.enums.types;

public enum FarmBuildingType {
    // TODO: modify all of the values
    BARN(7, 4, "Houses 4 barn-dwelling animals.", 350,150, 6000, false),
    BIG_BARN(7, 4, "Houses 8 barn-dwelling animals. Unlocks goats.", 450, 200, 12000, false),
    DELUXE_BARN(7, 4, "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.", 550, 300, 25000, false),
    COOP(6, 3, "Houses 4 coop-dwelling animals.", 300, 100, 4000, false),
    BIG_COOP(6, 3, "Houses 8 coop-dwelling animals. Unlocks ducks.", 400, 150, 10000, false),
    DELUXE_COOP(6, 3, "Houses 12 coop-dwelling animals. Unlocks rabbits.", 500, 200, 20000,  false),
    WELL(3, 3, "Provides a place for you to refill your watering can.", 0, 75, 1000, false),
    SHIPPING_BIN(1, 1, "Items placed in it will be included in the nightly shipment.", 150, 0, 250, false);

    private final int width;
    private final int length;
    private final String description;
    private final int woodCount;
    private final int stoneCount;
    private final int cost;
    private final Boolean isCage;

    FarmBuildingType(int width, int length, String description, int woodCost, int stoneCost, int cost, Boolean isCage) {
        this.width = width;
        this.length = length;
        this.description = description;
        this.woodCount = woodCost;
        this.stoneCount = stoneCost;
        this.cost = cost;
        this.isCage = isCage;
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

    public int getStoneCount() {
        return stoneCount;
    }
}
