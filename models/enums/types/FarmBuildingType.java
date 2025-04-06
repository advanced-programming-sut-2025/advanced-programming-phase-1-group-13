package models.enums.types;

public enum FarmBuildingType {
    // TODO: modify all of the values
    BARN(1, 1, "", 1, 1, false),
    BIG_BARN(1, 1, "", 1, 1, false),
    DELUXE_BARN(1, 1, "", 1, 1, false),
    COOP(1, 1, "", 1, 1, false),
    BIG_COOP(1, 1, "", 1, 1, false),
    DELUXE_COOP(1, 1, "", 1, 1, false),
    WELL(1, 1, "", 1, 1, null),
    SHIPPING_BIN(1, 1, "", 1, 1, false);

    private final int width;
    private final int length;
    private final String description;
    private final int woodCost;
    private final int stoneCost;
    private final Boolean isCage;

    FarmBuildingType(int width, int length, String description, int woodCost, int stoneCost, Boolean isCage) {
        this.width = width;
        this.length = length;
        this.description = description;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
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

    public int getWoodCost() {
        return woodCost;
    }

    public int getStoneCost() {
        return stoneCost;
    }
}
