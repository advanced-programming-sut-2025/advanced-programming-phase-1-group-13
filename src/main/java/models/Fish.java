package models;

import models.enums.Quality;
import models.enums.environment.Season;
import models.enums.types.FishType;

public class Fish extends Item {
    FishType type;
    Quality quality;

    public Fish(FishType type, Quality quality) {
        this.type = type;
        this.quality = quality;
    }

    public FishType getType() {
        return type;
    }

    public Quality getQuality() {
        return quality;
    }
}
