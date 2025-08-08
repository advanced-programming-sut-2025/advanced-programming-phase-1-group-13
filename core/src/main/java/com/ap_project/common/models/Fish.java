package com.ap_project.common.models;

import com.ap_project.common.models.enums.Quality;
import com.ap_project.common.models.enums.types.FishType;

public class Fish extends Item {
    private final FishType type;
    private Quality quality;

    public Fish(FishType type, Quality quality) {
        this.type = type;
        this.quality = quality;
        this.name = type.getName();
    }

    public FishType getType() {
        return type;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }
}
