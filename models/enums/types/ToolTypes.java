package models.enums.types;

public enum ToolTypes implements ItemType {
    HOE("Hoe"),
    PICKAXE("Pickaxe"),
    AXE("Axe"),
    WATERING_CAN("Watering can"),
    FISHING_ROD("Fishing rod"),
    SCYTHE("Scythe"),
    MILK_PAIL("Milk pail"),
    SHEARS("Shears"),
    BACKPACK("Backpack"),
    TRASH_CAN("Trash can");

    private final String name;

    ToolTypes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

