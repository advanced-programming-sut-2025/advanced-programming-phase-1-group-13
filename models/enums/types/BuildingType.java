package models.enums.types;

public enum BuildingType {
    // TODO: modify the values
    BARN(1, 1, "", 1, 1),
    BIG_BARN(1, 1, "", 1, 1),
    DELUXE_BARN(1, 1, "", 1, 1),
    COOP(1, 1, "", 1, 1),
    BIG_COOP(1, 1, "", 1, 1),
    DELUXE_COOP(1, 1, "", 1, 1),
    WELL(1, 1, "", 1, 1),
    SHIPPING_BIN(1, 1, "", 1, 1);

    private final int width;
    private final int length;
    private final String description;
    private final int woodCost;
    private final int stoneCost;


    BuildingType(int width, int length, String description, int woodCost, int stoneCost) {
        this.width = width;
        this.length = length;
        this.description = description;
        this.woodCost = woodCost;
        this.stoneCost = stoneCost;
    }
}
