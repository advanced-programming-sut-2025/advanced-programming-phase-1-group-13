package models;

import models.enums.Quality;
import models.enums.types.FishType;

public class Fish extends Item {
    FishType type;
    Quality quality;
    String name;

    public Fish(FishType type, Quality quality) {
        this.type = type;
        this.quality = quality;
        this.name = type.getName();
    }

    @Override
    public String getName() {
        return name;
    }

    public FishType getType() {
        return type;
    }

    public Quality getQuality() {
        return quality;
    }


}
