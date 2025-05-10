package models.enums.types;

public enum TileType {
    // TODO: complete this enum the right way...
    GREENHOUSE("Greenhouse"),
    CABIN("Cabin"),
    QUARRY("Quarry"),
    ANIMAL("Animal"),
    PLOWED_SOIL("Plowed Soil"),
    NOT_PLOWED_SOIL("Not Plowed Soil"),
    PLANTED_SEED("Planted Seed"),
    GROWING_CROP("Growing Crop"),
    STONE("Stone"),
    WATER("Water"),
    GRASS("Grass"),
    TREE("Tree"),
    WOOD_LOG("Wood Log"),
    UNDER_AN_ITEM("An Item");

    private final String displayName;

    TileType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
