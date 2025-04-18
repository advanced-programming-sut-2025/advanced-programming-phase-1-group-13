package models.farming;

import models.enums.types.CropType;

public class Crop extends Harvestable {
    private CropType type;
    private boolean oneTime;
    int regrowthTime;

    public Crop(CropType type) {
        this.type = type;
    }
}
