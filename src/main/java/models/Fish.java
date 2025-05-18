package models;

import models.enums.Quality;
import models.enums.types.FishType;

public class Fish extends Item {
    private FishType type;
    private Quality quality;
    private String name;

    public Fish(FishType type, Quality quality) {
        this.type = type;
        this.quality = quality;
        this.name = type.getName();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public FishType getType() {
        return type;
    }

    public Quality getQuality() {
        return quality;
    }


}
