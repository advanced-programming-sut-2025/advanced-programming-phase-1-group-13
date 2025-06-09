package com.project.models.enums.types;

public enum TileType {
    GREENHOUSE("Greenhouse"),
    CABIN("Cabin"),
    QUARRY_GROUND("Quarry's Ground"),
    ANIMAL("Animal"),
    PLOWED_SOIL("Plowed Soil"),
    NOT_PLOWED_SOIL("Not Plowed Soil"),
    PLANTED_SEED("Planted Seed"),
    GROWING_CROP("Growing Crop"),
    STONE("Stone"),
    WATER("Water"),
    WATERED_NOT_PLOWED_SOIL("Watered Not Plowed Soil"),
    WATERED_PLOWED_SOIL("Watered Plowed Soil"),
    GRASS("Grass"),
    TREE("Tree"),
    WOOD_LOG("Wood Log"),
    UNDER_AN_ITEM("An Item"),
    SHOP("Shop");


    private final String displayName;

    TileType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
